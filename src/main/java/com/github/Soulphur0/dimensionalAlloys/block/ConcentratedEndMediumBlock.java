package com.github.Soulphur0.dimensionalAlloys.block;

import com.github.Soulphur0.dimensionalAlloys.EntityCometBehaviour;
import com.github.Soulphur0.registries.CometBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.mob.ElderGuardianEntity;
import net.minecraft.entity.mob.RavagerEntity;
import net.minecraft.entity.mob.WardenEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class ConcentratedEndMediumBlock extends Block {

    public ConcentratedEndMediumBlock(Settings settings) {
        super(settings);
    }

    // $ Shape and collision
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.fullCube();
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.empty();
    }

    @Override
    public boolean isSideInvisible(BlockState state, BlockState stateFrom, Direction direction) {
        if (stateFrom.isOf(this)) {
            return true;
        }
        return super.isSideInvisible(state, stateFrom, direction);
    }

    // $ Entity behaviour
    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (entity instanceof EnderDragonEntity || entity instanceof WitherEntity || entity instanceof ElderGuardianEntity || entity instanceof RavagerEntity || entity instanceof WardenEntity)
            return;
        ((EntityCometBehaviour)entity).setCrystallizedTicks(entity.getMaxCrystallizedTicks());
        world.setBlockState(pos, Blocks.AIR.getDefaultState());
    }

    @Override
    public boolean canMobSpawnInside() {
        return false;
    }

    // $ Ticking
    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        boolean canDryOut = !(world.getBlockState(pos.down()).getBlock() == CometBlocks.CONCENTRATED_END_MEDIUM || world.getBlockState(pos.down()).getBlock() == Blocks.OCHRE_FROGLIGHT || world.getBlockState(pos.down()).getBlock() == Blocks.PEARLESCENT_FROGLIGHT || world.getBlockState(pos.down()).getBlock() == Blocks.VERDANT_FROGLIGHT);
        if (canDryOut){
            float hardenChance = random.nextFloat();
            if (hardenChance <= 0.025f)
                world.setBlockState(pos, CometBlocks.END_MEDIUM.getDefaultState());
        }
        super.randomTick(state, world, pos, random);
    }

    // $ On use behaviour
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack handItemStack = player.getStackInHand(hand);
        if(handItemStack.isOf(Items.BUCKET)){
            if (!player.isCreative())
                player.getMainHandStack().decrement(1);
            if (handItemStack.isEmpty()){
                player.setStackInHand(hand, new ItemStack(CometBlocks.CONCENTRATED_END_MEDIUM_BUCKET));
            } else if (!player.getInventory().insertStack(new ItemStack(CometBlocks.CONCENTRATED_END_MEDIUM_BUCKET))) {
                player.dropItem(new ItemStack(CometBlocks.CONCENTRATED_END_MEDIUM_BUCKET), false);
            }
            world.setBlockState(pos, Blocks.AIR.getDefaultState());
        }
        return super.onUse(state, world, pos, player, hand, hit);
    }
}
