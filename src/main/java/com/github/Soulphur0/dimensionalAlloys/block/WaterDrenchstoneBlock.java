package com.github.Soulphur0.dimensionalAlloys.block;

import com.github.Soulphur0.registries.CometBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;

public class WaterDrenchstoneBlock extends DrenchstoneBlock{

    public WaterDrenchstoneBlock(Settings settings) {
        super(settings);
        setDefaultState(getStateManager().getDefaultState().with(LEVEL, 0).with(SOURCE_DIRECTION, Direction.UP));
    }

    @Override
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        return new ItemStack(CometBlocks.DRENCHSTONE);
    }
}

