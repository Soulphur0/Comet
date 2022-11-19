package com.github.Soulphur0.mixin;

import com.github.Soulphur0.dimensionalAlloys.CrystallizedEntityMethods;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(StatusEffectInstance.class)
public abstract class StatusEffectInstanceMixin {

    @Inject(method="update", at = @At("HEAD"), cancellable = true)
    public void cancelUpdate(LivingEntity entity, Runnable overwriteCallback, CallbackInfoReturnable<Boolean> cir){
        if (((CrystallizedEntityMethods)entity).isCrystallized())
            cir.setReturnValue(true);
    }
}
