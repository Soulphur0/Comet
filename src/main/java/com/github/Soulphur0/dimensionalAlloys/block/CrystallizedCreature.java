package com.github.Soulphur0.dimensionalAlloys.block;

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
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

public class CrystallizedCreature extends BlockWithEntity implements BlockEntityProvider, Waterloggable {

    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    public CrystallizedCreature(Settings settings) {
        super(settings);
        setDefaultState(this.getStateManager().getDefaultState().with(WATERLOGGED,false));
    }

    // ? Ticking
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, CometBlocks.CRYSTALLIZED_CREATURE_BLOCK_ENTITY, CrystallizedCreatureBlockEntity::tick);
    }

    // Fix issues when inheriting from BlockWithEntity.
    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    // ? For waterlogging

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED);
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

    // ? NBT jugglery

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        if (world.isClient){
            world.getBlockEntity(pos, CometBlocks.CRYSTALLIZED_CREATURE_BLOCK_ENTITY).ifPresent((blockEntity) -> {
                blockEntity.readNbtFromItemStack(itemStack);
            });
        }
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!world.isClient && world.getGameRules().getBoolean(GameRules.DO_TILE_DROPS)){
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof CrystallizedCreatureBlockEntity crystallizedCreatureBlockEntity) {
                ItemStack itemStack = new ItemStack(this);

                // ? Store nbt data to the item if the block entity has nbt data
                // Blocks without nbt data are not dropped to creative players.
                if(crystallizedCreatureBlockEntity.mobData != null){
                    NbtCompound nbtCompound = new NbtCompound();
                    nbtCompound.put("mobData", crystallizedCreatureBlockEntity.mobData);
                    BlockItem.setBlockEntityNbt(itemStack, CometBlocks.CRYSTALLIZED_CREATURE_BLOCK_ENTITY,nbtCompound);
                } else if (player.isCreative()){
                    return;
                }

                ItemEntity itemEntity = new ItemEntity(world, (double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), itemStack);
                itemEntity.setToDefaultPickupDelay();
                world.spawnEntity(itemEntity);
            }
        }
        super.onBreak(world, pos, state, player);
    }

    // Todo revise later to add functionality
    @Override
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        System.out.println("getPickStack()");
        BlockEntity blockEntity = world.getBlockEntity(pos);
        return blockEntity instanceof CrystallizedCreatureBlockEntity ? ((CrystallizedCreatureBlockEntity)blockEntity).getPickStack() : super.getPickStack(world, pos, state);
    }

    // ? Entity spawn

    public void releaseMob(World world, BlockPos pos){
        if (!world.isClient){
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof CrystallizedCreatureBlockEntity crystallizedCreatureBlockEntity) {
                if(crystallizedCreatureBlockEntity.mobData != null) {
                    NbtCompound nbtCompound = new NbtCompound();
                    NbtCompound nbtCompound1 = crystallizedCreatureBlockEntity.mobData;
                    nbtCompound.put("mobData", nbtCompound1);

                    Entity entity = EntityType.loadEntityWithPassengers(nbtCompound.getCompound("mobData"), world, (loadedEntity) -> loadedEntity);

                    if (entity!=null){
                        entity.refreshPositionAndAngles(pos.getX(), pos.getY(),pos.getZ(),entity.getYaw(),entity.getPitch());
                        world.spawnEntity(entity);

                        world.setBlockState(pos, Blocks.AIR.getDefaultState());
                    }
                }
            }
        }
    }
}
