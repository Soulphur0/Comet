package com.github.Soulphur0.dimensionalAlloys.block;

import com.github.Soulphur0.registries.CometBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;

public class NetherDrenchstoneBlock extends Block {

    public NetherDrenchstoneBlock(Settings settings) {
        super(settings);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        Direction[] directions = {Direction.UP, Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST};
        for (int i = 0; i<5;i++){
            if (world.getBlockState(pos.offset(directions[i])).getBlock() == Blocks.WATER){
                world.setBlockState(pos, CometBlocks.TEAR_BLOCK.getDefaultState(),2);
            } else if (world.getBlockState(pos.offset(directions[i])).getBlock() == Blocks.LAVA){
                world.setBlockState(pos, Blocks.MAGMA_BLOCK.getDefaultState(),2);
            }
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }
}
