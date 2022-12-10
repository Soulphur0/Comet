package com.github.Soulphur0.dimensionalAlloys.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

import java.util.Random;

public class EndbriteTube extends Block {
    public static final IntProperty TUBES = IntProperty.of("tubes", 1, 7);

    public EndbriteTube(Settings settings){
        super(settings);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager) {
        stateManager.add(TUBES);
    }

    // _ Block placement behaviour
    @Override
    public BlockState getPlacementState(ItemPlacementContext ipc) {
        BlockState blockState = ipc.getWorld().getBlockState(ipc.getBlockPos());
        if (blockState.isOf(this)) {
            return (BlockState) blockState.with(TUBES, Math.min(7, (Integer) blockState.get(TUBES) + 1));
        } else {
            return (BlockState)this.getDefaultState().with(TUBES, 1);
        }
    }

    @Override
    public boolean canReplace(BlockState state, ItemPlacementContext context) {
        return context.getStack().getItem() == this.asItem() && (Integer) state.get(TUBES) < 7 || super.canReplace(state, context);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockPos blockPos = pos.up();
        BlockState blockState = world.getBlockState(blockPos);
        return blockState.isSideSolidFullSquare(world, blockPos, Direction.DOWN);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (!canPlaceAt(state, world,pos))
            return Blocks.AIR.getDefaultState();

        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    // _ Block shape
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext context) {
        return switch (state.get(TUBES)) {
            case 1 -> Block.createCuboidShape(6.0D, 12.0D, 6.0D, 10.0D, 16.0D, 10.0D);
            case 2 -> Block.createCuboidShape(4.0D, 10.0D, 4.0D, 12.0D, 16.0D, 12.0D);
            case 3 -> Block.createCuboidShape(4.0D, 8.0D, 4.0D, 12.0D, 16.0D, 12.0D);
            case 4 -> Block.createCuboidShape(4.0D, 4.0D, 4.0D, 12.0D, 16.0D, 12.0D);
            case 5 -> Block.createCuboidShape(4.0D, 4.0D, 2.0D, 12.0D, 16.0D, 14.0D);
            default -> Block.createCuboidShape(2.0D, 4.0D, 2.0D, 14.0D, 16.0D, 14.0D);
        };
    }

    // _ Particle display
    @Environment(EnvType.CLIENT)
    private static void createParticle(World world, BlockPos pos, BlockState state, net.minecraft.util.math.random.Random random) {
        ParticleEffect particleEffect = ParticleTypes.DRIPPING_OBSIDIAN_TEAR;
        switch (state.get(TUBES)){
            case 1:
                world.addParticle(particleEffect, pos.getX()+0.525D, pos.getY()+0.8D, pos.getZ()+0.525D, 0.0D, 0.0D, 0.0D);
                break;
            case 2:
                int tube2 = (int)Math.floor(random.nextFloat() * 2);
                if (tube2 == 0)
                    world.addParticle(particleEffect, pos.getX()+0.625D, pos.getY()+0.7D, pos.getZ()+0.555D, 0.0D, 0.0D, 0.0D);
                else
                    world.addParticle(particleEffect, pos.getX()+0.375D, pos.getY()+0.7D, pos.getZ()+0.425D, 0.0D, 0.0D, 0.0D);
                break;
            case 3:
                int tube3 = (int)Math.floor(random.nextFloat() * 3);
                if (tube3 == 0)
                    world.addParticle(particleEffect, pos.getX()+0.625D, pos.getY()+0.7D, pos.getZ()+0.625D, 0.0D, 0.0D, 0.0D);
                else if (tube3 == 1)
                    world.addParticle(particleEffect, pos.getX()+0.375D, pos.getY()+0.7D, pos.getZ()+0.5D, 0.0D, 0.0D, 0.0D);
                else
                    world.addParticle(particleEffect, pos.getX()+0.625D, pos.getY()+0.7D, pos.getZ()+0.375D, 0.0D, 0.0D, 0.0D);
                break;
            case 4:
                int tube4 = (int)Math.floor(random.nextFloat() * 4);
                if (tube4 == 0)
                    world.addParticle(particleEffect, pos.getX()+0.625D, pos.getY()+0.4D, pos.getZ()+0.625D, 0.0D, 0.0D, 0.0D);
                else if (tube4 == 1)
                    world.addParticle(particleEffect, pos.getX()+0.375D, pos.getY()+0.6D, pos.getZ()+0.625D, 0.0D, 0.0D, 0.0D);
                else if (tube4 == 2)
                    world.addParticle(particleEffect, pos.getX()+0.375D, pos.getY()+0.4D, pos.getZ()+0.375D, 0.0D, 0.0D, 0.0D);
                else
                    world.addParticle(particleEffect, pos.getX()+0.625D, pos.getY()+0.4D, pos.getZ()+0.375D, 0.0D, 0.0D, 0.0D);
                break;
            case 5:
                int tube5 = (int)Math.floor(random.nextFloat() * 5);
                if (tube5 == 0)
                    world.addParticle(particleEffect, pos.getX()+0.625D, pos.getY()+0.4D, pos.getZ()+0.75D, 0.0D, 0.0D, 0.0D);
                else if (tube5 == 1)
                    world.addParticle(particleEffect, pos.getX()+0.375D, pos.getY()+0.6D, pos.getZ()+0.625D, 0.0D, 0.0D, 0.0D);
                else if (tube5 == 2)
                    world.addParticle(particleEffect, pos.getX()+0.375D, pos.getY()+0.4D, pos.getZ()+0.375D, 0.0D, 0.0D, 0.0D);
                else if (tube5 == 3)
                    world.addParticle(particleEffect, pos.getX()+0.625D, pos.getY()+0.4D, pos.getZ()+0.5D, 0.0D, 0.0D, 0.0D);
                else
                    world.addParticle(particleEffect, pos.getX()+0.625D, pos.getY()+0.45D, pos.getZ()+0.25D, 0.0D, 0.0D, 0.0D);
                break;
            case 6:
                int tube6 = (int)Math.floor(random.nextFloat() * 6);
                if (tube6 == 0)
                    world.addParticle(particleEffect, pos.getX()+0.5D, pos.getY()+0.4D, pos.getZ()+0.75D, 0.0D, 0.0D, 0.0D);
                else if (tube6 == 1)
                    world.addParticle(particleEffect, pos.getX()+0.25D, pos.getY()+0.6D, pos.getZ()+0.625D, 0.0D, 0.0D, 0.0D);
                else if (tube6 == 2)
                    world.addParticle(particleEffect, pos.getX()+0.25D, pos.getY()+0.4D, pos.getZ()+0.375D, 0.0D, 0.0D, 0.0D);
                else if (tube6 == 3)
                    world.addParticle(particleEffect, pos.getX()+0.5D, pos.getY()+0.4D, pos.getZ()+0.5D, 0.0D, 0.0D, 0.0D);
                else if (tube6 == 4)
                    world.addParticle(particleEffect, pos.getX()+0.5D, pos.getY()+0.45D, pos.getZ()+0.25D, 0.0D, 0.0D, 0.0D);
                else
                    world.addParticle(particleEffect, pos.getX()+0.75D, pos.getY()+0.6D, pos.getZ()+0.5D, 0.0D, 0.0D, 0.0D);
                break;
            default:
                int tube7 = (int)Math.floor(random.nextFloat() * 7);
                if (tube7 == 0)
                    world.addParticle(particleEffect, pos.getX()+0.5D, pos.getY()+0.4D, pos.getZ()+0.75D, 0.0D, 0.0D, 0.0D);
                else if (tube7 == 1)
                    world.addParticle(particleEffect, pos.getX()+0.25D, pos.getY()+0.6D, pos.getZ()+0.625D, 0.0D, 0.0D, 0.0D);
                else if (tube7 == 2)
                    world.addParticle(particleEffect, pos.getX()+0.25D, pos.getY()+0.4D, pos.getZ()+0.375D, 0.0D, 0.0D, 0.0D);
                else if (tube7 == 3)
                    world.addParticle(particleEffect, pos.getX()+0.5D, pos.getY()+0.4D, pos.getZ()+0.5D, 0.0D, 0.0D, 0.0D);
                else if (tube7 == 4)
                    world.addParticle(particleEffect, pos.getX()+0.5D, pos.getY()+0.45D, pos.getZ()+0.25D, 0.0D, 0.0D, 0.0D);
                else if (tube7 == 5)
                    world.addParticle(particleEffect, pos.getX()+0.75D, pos.getY()+0.5D, pos.getZ()+0.625D, 0.0D, 0.0D, 0.0D);
                else
                    world.addParticle(particleEffect, pos.getX()+0.75D, pos.getY()+0.7D, pos.getZ()+0.375D, 0.0D, 0.0D, 0.0D);
                break;
        }
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, net.minecraft.util.math.random.Random random) {
        float f = random.nextFloat();
            if (f <= 0.125F) {
                createParticle(world, pos, state, random);
            }
    }
}