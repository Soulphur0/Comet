package com.github.Soulphur0.mixin.entity.player;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameMode;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {

    // $ Injected ------------------------------------------------------------------------------------------------------

    // ? Make player unable to sneak during crystallization.
    @Inject(method ="updatePose", at = @At("HEAD"),cancellable = true)
    private void cancelPoseUpdatesWhenCrystallized(CallbackInfo ci){
        if (((PlayerEntity)(Object)this).isCrystallized())
            ci.cancel();
    }

    // ? Make player unable to attack during crystallization.
    @Inject(method="attack", at = @At("HEAD"), cancellable = true)
    private void cancelAttack(Entity target, CallbackInfo ci){
        if (((PlayerEntity)(Object)this).isCrystallized())
            ci.cancel();
    }

    // ? Make player unable to break blocks during crystallization.
    @Inject(method = "isBlockBreakingRestricted", at = @At("HEAD"), cancellable = true)
    private void restrictBlockDestructionWhenCrystallized(World world, BlockPos pos, GameMode gameMode, CallbackInfoReturnable<Boolean> cir){
        if (((PlayerEntity)(Object)this).isCrystallized()){
            cir.setReturnValue(true);
        }
    }

    // ? Make player unable to use consumables during crystallization.
    @Inject(method = "canConsume", at = @At("HEAD"), cancellable = true)
    private void restrictItemConsumptionDuringCrystallization(boolean ignoreHunger, CallbackInfoReturnable<Boolean> cir){
        if (((PlayerEntity)(Object)this).isCrystallized()){
            cir.setReturnValue(false);
        }
    }
}
