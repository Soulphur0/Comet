package com.github.Soulphur0.dimensionalAlloys.sound;

import com.github.Soulphur0.Comet;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.OptionalInt;


public class CometSoundUtilities {

    public static void playRandomSound(World world, BlockPos pos, float volume, float pitch, boolean useDistance, SoundCategory soundCategory, SoundEvent... soundPool){
        SoundEvent pickedSound = soundPool[(int)Math.max(0,Math.round(Math.random()* soundPool.length - 1))];
        world.playSound(pos.getX(), pos.getY(), pos.getZ(), pickedSound, soundCategory, volume, pitch, useDistance);
    }

    public static void playWeightedSound(World world, BlockPos pos, float volume, float pitch, boolean useDistance, SoundCategory soundCategory, int[] weightedList, SoundEvent... soundPool){
        OptionalInt weightedListSum = Arrays.stream(weightedList).reduce(Integer::sum);
        int weightedDiceRoll = (int)Math.floor(Math.random() * weightedListSum.getAsInt() + 1);
        for(int i = 0; i<weightedList.length; i++){
            weightedDiceRoll -= weightedList[i];
            if (weightedDiceRoll <= 0)
                world.playSound(pos.getX(), pos.getY(), pos.getZ(), soundPool[i], SoundCategory.BLOCKS, volume, pitch, useDistance);
        }
    }
}
