package com.github.SoulArts.dimensionalAlloys.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
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
        return context.getStack().getItem() == this.asItem() && (Integer)state.get(TUBES) < 7 ? true : super.canReplace(state, context);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockPos blockPos = pos.up();
        BlockState blockState = world.getBlockState(blockPos);
        return blockState.isSideSolidFullSquare(world, blockPos, Direction.DOWN);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext context) {
        switch (state.get(TUBES)){
            case 1:
                return Block.createCuboidShape(6.0D, 12.0D, 6.0D, 10.0D, 16.0D, 10.0D);
            case 2:
                return Block.createCuboidShape(4.0D, 10.0D, 4.0D, 12.0D, 16.0D, 12.0D);
            case 3:
                return Block.createCuboidShape(4.0D, 8.0D, 4.0D, 12.0D, 16.0D, 12.0D);
            case 4:
                return Block.createCuboidShape(4.0D, .0D, 4.0D, 12.0D, 16.0D, 12.0D);
            case 5:
                return Block.createCuboidShape(4.0D, 4.0D, 2.0D, 12.0D, 16.0D, 14.0D);
            default:
                return Block.createCuboidShape(2.0D, 4.0D, 2.0D, 14.0D, 16.0D, 14.0D);
        }
    }

    @Environment(EnvType.CLIENT)
    private static void createParticle(World world, BlockPos pos, BlockState state, Random random) {
        ParticleEffect particleEffect = ParticleTypes.DRIPPING_OBSIDIAN_TEAR;
        world.addParticle(particleEffect, pos.getX()+random.nextDouble()-0.1D, pos.getY()+1.0D, pos.getZ()+random.nextDouble()+0.1D, 0.0D, 0.0D, 0.0D);
    }

    @Environment(EnvType.CLIENT)
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
            float f = random.nextFloat();
            if (f <= 0.12F) {
                createParticle(world, pos, state, random);
            }
    }
}
