package com.github.Soulphur0.mixin;

import net.minecraft.block.ChorusFlowerBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(ChorusFlowerBlock.class)
public interface ChorusFlowerBlockInvoker {

    @Invoker("isSurroundedByAir")
    static boolean invokerIsSurroundedByAir(WorldView world, BlockPos pos, @Nullable Direction exceptDirection){
        throw new AssertionError();
    }

    @Invoker("grow")
    void invokerGrow(World world, BlockPos pos, int age);

    @Invoker("die")
    void invokerDie(World world, BlockPos pos);

}
