package com.github.Soulphur0.dimensionalAlloys.block;

import com.github.Soulphur0.dimensionalAlloys.CrystallizedEntityMethods;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.TransparentBlock;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class FreshEndMedium extends TransparentBlock implements CrystallizedEntityMethods {

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

    // _ Entity behaviour
    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        ((CrystallizedEntityMethods)entity).setInFreshEndMedium(2);
    }
}
