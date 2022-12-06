package com.github.Soulphur0.dimensionalAlloys.block;

import com.github.Soulphur0.Comet;
import com.github.Soulphur0.dimensionalAlloys.EntityCometBehaviour;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.mob.ElderGuardianEntity;
import net.minecraft.entity.mob.RavagerEntity;
import net.minecraft.entity.mob.WardenEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public class FreshEndMedium extends TransparentBlock implements EntityCometBehaviour {

    public FreshEndMedium(Settings settings){
        super(settings);
    }

    // _ Glass-like properties.
    @Override
    public boolean isSideInvisible(BlockState state, BlockState stateFrom, Direction direction) {
        if (stateFrom.isOf(this)) {
            return true;
        }
        return super.isSideInvisible(state, stateFrom, direction);
    }

    @Override
    public VoxelShape getCameraCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.empty();
    }

    @Override
    public float getAmbientOcclusionLightLevel(BlockState state, BlockView world, BlockPos pos) {
        return 1.0f;
    }

    @Override
    public boolean isTranslucent(BlockState state, BlockView world, BlockPos pos) {
        return true;
    }

    // _ Block shape & collision shape.
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.cuboid(0f,0f,0f,1.0f,0.06f,1.0f);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.empty();
    }

    // _ Block placement behaviour
    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockPos blockPos = pos.down();
        BlockState blockState = world.getBlockState(blockPos);
        return blockState.isSideSolidFullSquare(world, blockPos, Direction.UP);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (!canPlaceAt(state, world, pos))
            return Blocks.AIR.getDefaultState();

        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public boolean canReplace(BlockState state, ItemPlacementContext context) {
        return true;
    }

    // _ Entity behaviour
    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (entity instanceof EnderDragonEntity || entity instanceof WitherEntity || entity instanceof ElderGuardianEntity || entity instanceof RavagerEntity || entity instanceof WardenEntity)
            return;
        ((EntityCometBehaviour)entity).setInFreshEndMedium(2);
    }

    @Override
    public boolean canMobSpawnInside() {
        return false;
    }

    // _ On use behaviour.

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack mainHandItemStack = player.getStackInHand(hand);
        if (mainHandItemStack.isOf(Items.GLASS_BOTTLE)){
            if (!player.isCreative())
                player.getMainHandStack().decrement(1);
            if (mainHandItemStack.isEmpty()){
                player.setStackInHand(hand, new ItemStack(Comet.FRESH_END_MEDIUM_BOTTLE));
            } else if (!player.getInventory().insertStack(new ItemStack(Comet.FRESH_END_MEDIUM_BOTTLE))) {
                player.dropItem(new ItemStack(Comet.FRESH_END_MEDIUM_BOTTLE), false);
            }
            world.setBlockState(pos, Blocks.AIR.getDefaultState());
        }

        return super.onUse(state, world, pos, player, hand, hit);
    }
}