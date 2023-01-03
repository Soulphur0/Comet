package com.github.Soulphur0.mixin.entity.effect;

import com.github.Soulphur0.dimensionalAlloys.StatusEffectInstanceMethods;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(StatusEffectInstance.class)
public abstract class StatusEffectInstanceMixin implements StatusEffectInstanceMethods {

    @Shadow
    private boolean showParticles;

    @Shadow
    int duration;
    // $ Comet ---------------------------------------------------------------------------------------------------------
    private boolean hiddenByCrystallization;

    public void setDuration(int duration){ this.duration = duration;}

    public void setShowParticles(boolean showParticles){
        this.showParticles = showParticles;
    }

    public void setHiddenByCrystallization(boolean hiddenByCrystallization){
        this.hiddenByCrystallization = hiddenByCrystallization;
    }

    public boolean isHiddenByCrystallization(){
        return hiddenByCrystallization;
    }

    @Inject(method="update", at = @At("HEAD"), cancellable = true)
    private void cancelUpdate(LivingEntity entity, Runnable overwriteCallback, CallbackInfoReturnable<Boolean> cir){
        if (entity.isCrystallized() && !entity.isCrystallizedByStatusEffect())
            cir.setReturnValue(true);
    }
}
