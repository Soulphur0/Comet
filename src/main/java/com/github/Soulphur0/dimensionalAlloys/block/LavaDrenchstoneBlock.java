package com.github.Soulphur0.dimensionalAlloys.block;

import net.minecraft.util.math.Direction;

public class LavaDrenchstoneBlock extends DrenchstoneBlock{

    public LavaDrenchstoneBlock(Settings settings) {
        super(settings);
        setDefaultState(getStateManager().getDefaultState().with(LEVEL, 0).with(SOURCE_DIRECTION, Direction.UP));
    }
}