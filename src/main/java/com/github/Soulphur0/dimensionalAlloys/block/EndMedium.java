package com.github.Soulphur0.dimensionalAlloys.block;

import com.github.Soulphur0.registries.CometBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class EndMedium extends Block {

    public EndMedium(Settings settings) {
        super(settings);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        BlockState blockState = world.getBlockState(pos.up());
        if (random.nextFloat() < 0.1F && blockState.isOf(Blocks.AIR))
            leakFreshMedium(state,world,pos);
    }

    private void leakFreshMedium(BlockState state, WorldAccess world, BlockPos pos){
        world.setBlockState(pos.up(), CometBlocks.FRESH_END_MEDIUM.getDefaultState(), Block.NOTIFY_ALL);
    }
}
