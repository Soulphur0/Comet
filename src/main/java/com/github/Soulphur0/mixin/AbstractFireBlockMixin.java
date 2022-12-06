package com.github.Soulphur0.mixin;

import com.github.Soulphur0.registries.CometBlocks;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractFireBlock.class)
public class AbstractFireBlockMixin {

    @Inject(method ="getState", at = @At("HEAD"), cancellable = true)
    private static void addCometCustomFire(BlockView world, BlockPos pos, CallbackInfoReturnable<BlockState> cir){
        BlockPos blockPos = pos.down();
        BlockState blockState = world.getBlockState(blockPos);
        if (blockState == CometBlocks.END_MEDIUM.getDefaultState()) {
            cir.setReturnValue(CometBlocks.END_FIRE.getDefaultState());
        }
    }
}
