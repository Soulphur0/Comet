package com.github.Soulphur0.dimensionalAlloys.block;

import net.minecraft.util.math.Direction;

public class WaterDrenchstoneBlock extends DrenchstoneBlock{

    public WaterDrenchstoneBlock(Settings settings) {
        super(settings);
        setDefaultState(getStateManager().getDefaultState().with(LEVEL, 0).with(SOURCE_DIRECTION, Direction.UP));
    }
}

