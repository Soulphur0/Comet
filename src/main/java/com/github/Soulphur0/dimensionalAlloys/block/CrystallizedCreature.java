package com.github.Soulphur0.dimensionalAlloys.block;

import com.github.Soulphur0.Comet;
import com.github.Soulphur0.dimensionalAlloys.block.entity.CrystallizedCreatureBlockEntity;
import com.github.Soulphur0.registries.CometBlocks;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.*;
import org.jetbrains.annotations.Nullable;

public class CrystallizedCreature extends BlockWithEntity implements BlockEntityProvider, Waterloggable {

    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    public static final BooleanProperty CLEAR = BooleanProperty.of("clear");
    public static final BooleanProperty FIXED =BooleanProperty.of("fixed");

    public CrystallizedCreature(Settings settings) {
        super(settings);
        setDefaultState(this.getStateManager().getDefaultState().with(WATERLOGGED,false).with(CLEAR, false));
    }

    // ? Ticking
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, CometBlocks.CRYSTALLIZED_CREATURE_BLOCK_ENTITY, CrystallizedCreatureBlockEntity::tick);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    // ? For waterlogging

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED);
        builder.add(CLEAR);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return (BlockState)this.getDefaultState().with(WATERLOGGED, ctx.getWorld().getFluidState(ctx.getBlockPos()).getFluid() == Fluids.WATER);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (!canPlaceAt(state, world, pos)){
            dropBlockItem((World)world,pos);
            return Blocks.AIR.getDefaultState();
        }

        if (state.get(WATERLOGGED)){
            world.createAndScheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }

        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    // ? Hitbox and collision setup.

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.empty();
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new CrystallizedCreatureBlockEntity(pos, state);
    }

    // ? Block placement behaviour
    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockPos blockPos = pos.down();
        BlockState blockState = world.getBlockState(blockPos);
        return blockState.isSideSolid(world, blockPos, Direction.UP, SideShapeType.CENTER);
    }

    // ? NBT jugglery

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        if (!world.isClient){
            world.getBlockEntity(pos, CometBlocks.CRYSTALLIZED_CREATURE_BLOCK_ENTITY).ifPresent((blockEntity) -> {
                blockEntity.readNbtFromItemStack(itemStack);
                world.setBlockState(pos,state.with(CLEAR, blockEntity.getBlockStateData()));
            });
        }
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!world.isClient && world.getGameRules().getBoolean(GameRules.DO_TILE_DROPS)){
            dropBlockItem(world, pos);
        }
        super.onBreak(world, pos, state, player);
    }

    private void dropBlockItem(World world, BlockPos pos){
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof CrystallizedCreatureBlockEntity crystallizedCreatureBlockEntity) {
            ItemStack itemStack = new ItemStack(this);

            // ? Store nbt data to the item if the block entity has nbt data
            // Blocks without nbt data are not dropped.
            if(crystallizedCreatureBlockEntity.getMobData() != null){
                NbtCompound nbtCompound = new NbtCompound();
                nbtCompound.put("mobData", crystallizedCreatureBlockEntity.getMobData());

                // Put block state data to nbt
                nbtCompound.putBoolean("blockStateData", crystallizedCreatureBlockEntity.getBlockStateData());

                BlockItem.setBlockEntityNbt(itemStack, CometBlocks.CRYSTALLIZED_CREATURE_BLOCK_ENTITY, nbtCompound);
            } else
                return;

            ItemEntity itemEntity = new ItemEntity(world, (double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), itemStack);
            itemEntity.setToDefaultPickupDelay();
            world.spawnEntity(itemEntity);
        }
    }

    @Override
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        return blockEntity instanceof CrystallizedCreatureBlockEntity ? ((CrystallizedCreatureBlockEntity)blockEntity).getPickStack() : super.getPickStack(world, pos, state);
    }

    // ? Entity spawn

    public void releaseMob(World world, BlockPos pos){
        if (!world.isClient){
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof CrystallizedCreatureBlockEntity crystallizedCreatureBlockEntity) {
                if(crystallizedCreatureBlockEntity.getMobData() != null) {
                    NbtCompound nbtCompound = new NbtCompound();
                    NbtCompound nbtCompound1 = crystallizedCreatureBlockEntity.getMobData();
                    nbtCompound.put("mobData", nbtCompound1);

                    Entity entity = EntityType.loadEntityWithPassengers(nbtCompound.getCompound("mobData"), world, (loadedEntity) -> loadedEntity);

                    if (entity!=null){
                        entity.refreshPositionAndAngles(pos.getX()+0.5D, pos.getY(),pos.getZ()+0.5D,entity.getYaw(),entity.getPitch());
                        world.spawnEntity(entity);
                    }
                }
            }
            world.setBlockState(pos, Blocks.AIR.getDefaultState());
        }
    }

    // ? Player interaction

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        Item mainHandItem = player.getMainHandStack().getItem();
        if (mainHandItem instanceof PickaxeItem){
            world.setBlockState(pos,state.with(CLEAR, true));

            world.getBlockEntity(pos, CometBlocks.CRYSTALLIZED_CREATURE_BLOCK_ENTITY).ifPresent(blockEntity -> {
                blockEntity.writeBlockStateData(true);
            });
        }
        return super.onUse(state, world, pos, player, hand, hit);
    }
}
