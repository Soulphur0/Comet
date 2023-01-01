package com.github.Soulphur0.dimensionalAlloys.sound;

import com.github.Soulphur0.Comet;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;


public class CometSoundUtilities {

    public static void playRandomSound(World world, BlockPos pos, float volume, float pitch, boolean useDistance, SoundEvent... soundPool){
        SoundEvent pickedSound = soundPool[(int)Math.round(Math.random()* soundPool.length - 1)];
        world.playSound(pos.getX(), pos.getY(), pos.getZ(), pickedSound, SoundCategory.BLOCKS, volume, pitch, useDistance);
    }
}
