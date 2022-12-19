package com.github.Soulphur0.dimensionalAlloys.utility;

import net.minecraft.util.math.Direction;

public class DrenchstoneSourceData {

    Direction direction;
    int level;

    public DrenchstoneSourceData(Direction direction, int level){
        this.direction = direction;
        this.level = level;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
