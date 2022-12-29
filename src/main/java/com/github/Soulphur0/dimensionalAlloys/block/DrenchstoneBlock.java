package com.github.Soulphur0.dimensionalAlloys.block;

import com.github.Soulphur0.dimensionalAlloys.utility.DrenchstoneSourceData;
import com.github.Soulphur0.registries.CometBlocks;
import net.minecraft.block.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.dimension.DimensionTypes;
import org.jetbrains.annotations.Nullable;

public class DrenchstoneBlock extends Block {

    protected static final DirectionProperty SOURCE_DIRECTION = DirectionProperty.of("source_direction", Direction.UP, Direction.NORTH, Direction.EAST,Direction.SOUTH, Direction.WEST, Direction.DOWN);
    protected static final IntProperty LEVEL = IntProperty.of("level",0,24);

    public DrenchstoneBlock(Settings settings) {
        super(settings);

        // ? Set default state.
        setDefaultState(getStateManager().getDefaultState().with(SOURCE_DIRECTION, Direction.UP).with(LEVEL, 0));
    }

    // $ Append properties
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(LEVEL);
        builder.add(SOURCE_DIRECTION);
    }

    // $ Placement state
    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        DrenchstoneSourceData sourceData = checkForSource(ctx.getWorld(), ctx.getWorld().getBlockState(ctx.getBlockPos()), ctx.getBlockPos());
        return this.getDefaultState().with(LEVEL, sourceData.getLevel()).with(SOURCE_DIRECTION, sourceData.getDirection());
    }

    // $ Behaviour methods
    // _ On neighbor update.
    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        // ? Get source data
        DrenchstoneSourceData sourceData = checkForSource(world, state, pos);

        // ? Get source's block.
        Block sourceBlock = world.getBlockState(pos.offset(state.get(SOURCE_DIRECTION))).getBlock();

        // ? If source doesn't exist, convert to level 0 of the same block and schedule block update.
        // todo: fluid-filled blocks empty instantly because the level state is changed to zero instantly, then all updated at once.
        //  Find a way to make them update in a chained manner.
        //  % A possible fix is to make them ticking block, emptying them at a very fast pace.
        if (sourceData.getLevel() < state.get(LEVEL)){
            // + If the current state is water, schedule update of 5 ticks.
            if (state.getBlock() instanceof WaterDrenchstoneBlock || state.getBlock().equals(Blocks.WATER)){
                world.setBlockState(pos, CometBlocks.WATER_DRENCHSTONE.getDefaultState().with(LEVEL, 0/*Math.max(state.get(LEVEL)-1,0)*/).with(SOURCE_DIRECTION, state.get(SOURCE_DIRECTION)), 2);
                world.createAndScheduleBlockTick(pos, state.getBlock(), 5);
            // + If the current state is lava, schedule update of 30/10 ticks depending on dimension.
            } else if (state.getBlock() instanceof LavaDrenchstoneBlock || state.getBlock().equals(Blocks.LAVA)){
                world.setBlockState(pos, CometBlocks.LAVA_DRENCHSTONE.getDefaultState().with(LEVEL, 0/*Math.max(state.get(LEVEL)-1,0)*/).with(SOURCE_DIRECTION, state.get(SOURCE_DIRECTION)), 2);
                world.createAndScheduleBlockTick(pos, state.getBlock(), 30);
            // + If the current state is none, schedule update for good measure.
            } else {
                world.setBlockState(pos, CometBlocks.DRENCHSTONE.getDefaultState().with(LEVEL, 0/*Math.max(state.get(LEVEL)-1,0)*/).with(SOURCE_DIRECTION, state.get(SOURCE_DIRECTION)), 2);
                world.createAndScheduleBlockTick(pos, state.getBlock(), 5);
            }
        }

        // ? If source level is greater than the current state's level, schedule placement of updated state.
        if (sourceData.getLevel() > state.get(LEVEL)){
            // + Set state with updated level.
            world.setBlockState(pos, state.with(SOURCE_DIRECTION, sourceData.getDirection()).with(LEVEL, sourceData.getLevel()), 2);

            // + Schedule block update.
            // - If the block is lava, check for dimension and schedule update.
            if (sourceBlock == CometBlocks.LAVA_DRENCHSTONE || sourceBlock.equals(Blocks.LAVA)){
                if (world.getDimension().equals(DimensionTypes.THE_NETHER))
                    world.createAndScheduleBlockTick(pos, state.getBlock(),10);
                else
                    world.createAndScheduleBlockTick(pos, state.getBlock(),30);
            // - Otherwise, schedule update of 5 ticks.
            } else
                world.createAndScheduleBlockTick(pos, state.getBlock(),5);
        }

        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    // _ Schedule block placement to make block appear like 'flowing' when filling up.
    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        // ? Get source's block.
        Block sourceBlock = world.getBlockState(pos.offset(state.get(SOURCE_DIRECTION))).getBlock();

        // ? Place a drenchstone variant if the source level is greater than zero, place the default state otherwise.
        if (state.get(LEVEL) > 0){
            // + Place drenchstone block depending on source's block.
            // - Water
            if (sourceBlock == Blocks.WATER || sourceBlock == CometBlocks.WATER_DRENCHSTONE)
                world.setBlockState(pos, CometBlocks.WATER_DRENCHSTONE.getDefaultState().with(LEVEL, state.get(LEVEL)).with(SOURCE_DIRECTION, state.get(SOURCE_DIRECTION)), 2);
            // - Lava
            else if (sourceBlock == Blocks.LAVA || sourceBlock == CometBlocks.LAVA_DRENCHSTONE)
                world.setBlockState(pos, CometBlocks.LAVA_DRENCHSTONE.getDefaultState().with(LEVEL, state.get(LEVEL)).with(SOURCE_DIRECTION, state.get(SOURCE_DIRECTION)), 2);
        } else
            world.setBlockState(pos, CometBlocks.DRENCHSTONE.getDefaultState().with(LEVEL, state.get(LEVEL)).with(SOURCE_DIRECTION, state.get(SOURCE_DIRECTION)), 2);

        // ? Update block in the renderer.
        MinecraftClient.getInstance().worldRenderer.updateBlock(null, pos, null, null, 0);
        super.scheduledTick(state, world, pos, random);
    }

    // _ Get adjacent block with the greatest level, not counting the block below.
    private DrenchstoneSourceData checkForSource(WorldAccess world, BlockState state, BlockPos pos){
        Direction[] directions = {Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST};

        // # Source attributes
        Direction sourceDirection = Direction.UP;
        int greatestLevelValue = 0;

        // %NOTE: This only uses source levels if the block type is the same as this one (or any if this block type is regular drenchstone).

        // ? Prioritize level value of the source above.
        BlockState aboveBlockState = world.getBlockState(pos.offset(Direction.UP));

        // + Check for DRENCHSTONE-COMPATIBLE sources.
        if (state.getBlock() == CometBlocks.DRENCHSTONE){
            // - Check for water sources.
            if (aboveBlockState.getBlock() == Blocks.WATER){
                greatestLevelValue = convertFluidLevelToDrenchstoneLevel(aboveBlockState.get(FluidBlock.LEVEL)) + 15;
            } else if (aboveBlockState.getBlock() == CometBlocks.WATER_DRENCHSTONE){
                greatestLevelValue = Math.max(Math.max(aboveBlockState.get(LEVEL) - 1, 0), greatestLevelValue);
            }

            // - Check for lava sources.
            if (aboveBlockState.getBlock() == Blocks.LAVA){
                greatestLevelValue = convertFluidLevelToDrenchstoneLevel(aboveBlockState.get(FluidBlock.LEVEL)) + 7;
            } else if (aboveBlockState.getBlock() == CometBlocks.LAVA_DRENCHSTONE){
                greatestLevelValue = Math.max(Math.max(aboveBlockState.get(LEVEL) - 1, 0), greatestLevelValue);
            }
        // + Check for WATER-COMPATIBLE sources.
        } else if (state.getBlock() == CometBlocks.WATER_DRENCHSTONE){
            // - Check for water sources.
            if (aboveBlockState.getBlock() == Blocks.WATER){
                greatestLevelValue = convertFluidLevelToDrenchstoneLevel(aboveBlockState.get(FluidBlock.LEVEL)) + 15;
            } else if (aboveBlockState.getBlock() == CometBlocks.WATER_DRENCHSTONE){
                greatestLevelValue = Math.max(Math.max(aboveBlockState.get(LEVEL) - 1, 0), greatestLevelValue);
            }
        // + Check for LAVA-COMPATIBLE sources.
        } else if (state.getBlock() == CometBlocks.LAVA_DRENCHSTONE){
            // - Check for lava sources.
            if (aboveBlockState.getBlock() == Blocks.LAVA){
                greatestLevelValue = convertFluidLevelToDrenchstoneLevel(aboveBlockState.get(FluidBlock.LEVEL)) + 7;
            } else if (aboveBlockState.getBlock() == CometBlocks.LAVA_DRENCHSTONE){
                greatestLevelValue = Math.max(Math.max(aboveBlockState.get(LEVEL) - 1, 0), greatestLevelValue);
            }
        }

        // ? Check for above and surrounding blocks, and override level value if it's greater than the source above.
        for (int i = 0; i<4; i++) {
            BlockState sourceBlockState = world.getBlockState(pos.offset(directions[i]));

            // + Check for DRENCHSTONE-COMPATIBLE sources.
            if (state.getBlock() == CometBlocks.DRENCHSTONE){
                // - Check for liquid sources.
                // Water fluid block.
                if  (sourceBlockState.getBlock() == Blocks.WATER) {
                    int waterLevel = convertFluidLevelToDrenchstoneLevel(sourceBlockState.get(FluidBlock.LEVEL)) + 8;
                    if (waterLevel > greatestLevelValue) {
                        greatestLevelValue = waterLevel;
                        sourceDirection = directions[i];
                    }
                // Lava fluid block.
                } else if (sourceBlockState.getBlock() == Blocks.LAVA) {
                    int lavaLevel = convertFluidLevelToDrenchstoneLevel(sourceBlockState.get(FluidBlock.LEVEL));
                    if (lavaLevel > greatestLevelValue) {
                        greatestLevelValue = lavaLevel;
                        sourceDirection = directions[i];
                    }
                // - Check for drenchstone sources.
                // Water drenchstone block.
                } else if (sourceBlockState.getBlock() == CometBlocks.WATER_DRENCHSTONE) {
                    if (sourceBlockState.get(LEVEL) > greatestLevelValue) {
                        greatestLevelValue = Math.max(sourceBlockState.get(LEVEL) - 1, 0);
                        sourceDirection = directions[i];
                    }
                // Lava drenchstone block.
                } else if (sourceBlockState.getBlock() == CometBlocks.LAVA_DRENCHSTONE) {
                    if (sourceBlockState.get(LEVEL) > greatestLevelValue) {
                        greatestLevelValue = Math.max(sourceBlockState.get(LEVEL) - 1, 0);
                        sourceDirection = directions[i];
                    }
                }
            // + Check for WATER-COMPATIBLE sources.
            } else if (state.getBlock() == CometBlocks.WATER_DRENCHSTONE) {
                // - Check for liquid sources.
                // Water fluid block.
                if  (sourceBlockState.getBlock() == Blocks.WATER) {
                    int waterLevel = convertFluidLevelToDrenchstoneLevel(sourceBlockState.get(FluidBlock.LEVEL)) + 8;
                    if (waterLevel > greatestLevelValue) {
                        greatestLevelValue = waterLevel;
                        sourceDirection = directions[i];
                    }
                // - Check for drenchstone sources.
                // Water drenchstone block.
                } else if (sourceBlockState.getBlock() == CometBlocks.WATER_DRENCHSTONE) {
                    if (sourceBlockState.get(LEVEL) > greatestLevelValue) {
                        greatestLevelValue = Math.max(sourceBlockState.get(LEVEL) - 1, 0);
                        sourceDirection = directions[i];
                    }
                }
            // + Check for LAVA-COMPATIBLE sources.
            } else if (state.getBlock() == CometBlocks.LAVA_DRENCHSTONE) {
                // - Check for liquid sources.
                // Lava fluid block.
                if (sourceBlockState.getBlock() == Blocks.LAVA) {
                    int lavaLevel = convertFluidLevelToDrenchstoneLevel(sourceBlockState.get(FluidBlock.LEVEL));
                    if (lavaLevel > greatestLevelValue) {
                        greatestLevelValue = lavaLevel;
                        sourceDirection = directions[i];
                    }
                // - Check for drenchstone sources.
                // Lava drenchstone block.
                } else if (sourceBlockState.getBlock() == CometBlocks.LAVA_DRENCHSTONE) {
                    if (sourceBlockState.get(LEVEL) > greatestLevelValue) {
                        greatestLevelValue = Math.max(sourceBlockState.get(LEVEL) - 1, 0);
                        sourceDirection = directions[i];
                    }
                }
            }

        }

        return new DrenchstoneSourceData(sourceDirection, greatestLevelValue);
    }

    private int convertFluidLevelToDrenchstoneLevel(int waterLevel){
        return switch (waterLevel) {
            case 0 -> 8;
            case 1 -> 7;
            case 2 -> 6;
            case 3 -> 5;
            case 4 -> 4;
            case 5 -> 3;
            case 6 -> 2;
            case 7 -> 1;
            default -> 0;
        };
    }
}
