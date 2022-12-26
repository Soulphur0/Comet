package com.github.Soulphur0.dimensionalAlloys.block;

import com.github.Soulphur0.dimensionalAlloys.state.property.CometProperties;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.shape.VoxelShape;

import java.util.function.ToIntFunction;

public interface EndRoots {
    VoxelShape SHAPE = Block.createCuboidShape(1.0, 0.0, 1.0, 15.0, 16.0, 15.0);
    BooleanProperty LOADED = CometProperties.LOADED;

    static ToIntFunction<BlockState> getLuminanceSupplier(int luminance) {
       return state -> state.get(CometProperties.LOADED) ? luminance : 0;
    }
}
