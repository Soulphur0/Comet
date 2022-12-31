package com.github.Soulphur0.dimensionalAlloys.block;

import com.github.Soulphur0.dimensionalAlloys.EntityCometBehaviour;
import com.github.Soulphur0.registries.CometBlocks;
import net.minecraft.block.*;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.*;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class EndMediumBlock extends Block {
    public EndMediumBlock(Settings settings) {
        super(settings);
    }

    // $ Disable culling, and only cull if the adjacent block is of the same type.
    @Override
    public VoxelShape getCullingShape(BlockState state, BlockView world, BlockPos pos) {
        return VoxelShapes.empty();
    }

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

    // $ Disallow mobs from pathfinding though.
    @Override
    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return false;
    }

    // $ Collision behaviour, sink if not moving.
    private boolean entityIsInEndMedium(BlockView world, Entity entity){
        return entity.getBlockStateAtPos().isOf(this) || world.getBlockState(entity.getBlockPos()).getBlock() == CometBlocks.END_MEDIUM || world.getBlockState(entity.getBlockPos().up()).getBlock() == CometBlocks.END_MEDIUM;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        EntityShapeContext entityShapeContext;
        Entity entity;

        // ? If an entity collides with the block.
        if (context instanceof EntityShapeContext && (entity = (entityShapeContext = (EntityShapeContext)context).getEntity()) != null) {

            // + Return a full collision shape if...
            // - The entity is a player, and it is jumping.
            // - The entity colliding is not moving with a very low horizontal speed.
            // - The entity's falling distance is greater than 0.25.
            // - The entity is a falling block.
            if (entity instanceof ClientPlayerEntity clientPlayer && clientPlayer.input.jumping || entity.getVelocity().horizontalLength() > 0.03 || entity.fallDistance > 0.25f || entity instanceof FallingBlockEntity) {
                // - Check that the entity is not inside end medium.
                if (!entityIsInEndMedium(world, entity))
                    return super.getCollisionShape(state, world, pos, context);
            }
        }

        return VoxelShapes.empty();
    }

    // _ Apply medium effects if the entity is in end medium.
    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (!(entity instanceof LivingEntity) || entity.getBlockStateAtPos().isOf(this) || entityIsInEndMedium(world, entity)) {
            // ? Slow down entity, and sink it if it is not jumping, otherwise it will slowly rise.
            // This multiplies entity movement in each axis by the specified value, slowing the entity, these values are set like this to simulate high viscosity.
            if (!(entity instanceof ClientPlayerEntity clientPlayer && clientPlayer.input.jumping))
                entity.slowMovement(state, new Vec3d(0.5f, 0.1, 0.5f));
            else
                entity.slowMovement(state, new Vec3d(0.5f, -0.5, 0.5f));

            // ? Set state for crystallization ticking.
            if (!(entity instanceof EnderDragonEntity || entity instanceof WitherEntity || entity instanceof ElderGuardianEntity || entity instanceof RavagerEntity || entity instanceof WardenEntity ||
                entity instanceof EndermanEntity || entity instanceof EndermiteEntity || entity instanceof ShulkerEntity))
                ((EntityCometBehaviour)entity).setInEndMedium(2);

            // ? Render particles when submerged.
            if (world.isClient) {
                Random random = world.getRandom();
                boolean entityMoved = entity.lastRenderX != entity.getX() || entity.lastRenderZ != entity.getZ();
                if (entityMoved && random.nextBoolean()) {
                    world.addParticle(ParticleTypes.PORTAL, entity.getX(), pos.getY() + 1, entity.getZ(), MathHelper.nextBetween(random, -1.0f, 1.0f) * 0.083333336f, 0.05f, MathHelper.nextBetween(random, -1.0f, 1.0f) * 0.083333336f);
                }
            }
        }
    }

    // $ Allow to set (end)fire.
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack handItemStack = player.getStackInHand(hand);

        // + If the player has a lighter item and the block got used from above, light on fire if there's room for it.
        if (handItemStack.isOf(Items.FLINT_AND_STEEL) || handItemStack.isOf(Items.FIRE_CHARGE)){
            if (hit.getSide() == Direction.UP && world.getBlockState(pos.up()).isOf(Blocks.AIR)){
                world.setBlockState(pos.up(), CometBlocks.END_FIRE.getDefaultState());
            }
        }
        return super.onUse(state, world, pos, player, hand, hit);
    }

    // $ Place an end medium layer on top of the block on random tick (if the random seed allows it)
    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (random.nextFloat() < 0.1F && world.getBlockState(pos.up()).isOf(Blocks.AIR)){
            int seed = pos.getX() * 7 + pos.getZ();
            random.setSeed(seed);

            // For some reason, it requires to do a nextBoolean() first.
            random.nextBoolean();

            if (random.nextBoolean())
                world.setBlockState(pos.up(), CometBlocks.END_MEDIUM_LAYER.getDefaultState(), Block.NOTIFY_ALL);
        }
    }

    // $ Spawn particles around

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (random.nextFloat() <= 0.1)
            world.addParticle(ParticleTypes.DRAGON_BREATH, pos.getX() + random.nextDouble()*8.0D - 4.0D, pos.getY() + random.nextDouble()*4.0D, pos .getZ() + random.nextDouble()*8.0D - 4.0D, random.nextDouble()*0.01D,random.nextDouble()*0.02D,random.nextDouble()*0.01D);
        super.randomDisplayTick(state, world, pos, random);
    }
}
