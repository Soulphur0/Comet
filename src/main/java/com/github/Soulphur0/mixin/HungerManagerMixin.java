package com.github.Soulphur0.mixin;

import com.github.Soulphur0.dimensionalAlloys.CrystallizedEntityMethods;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HungerManager.class)
public class HungerManagerMixin {

    PlayerEntity player;
    @Inject(method="update", at= @At("HEAD"), cancellable = true)
    private void cancelUpdate(PlayerEntity player, CallbackInfo ci){
        this.player = player;
        if (((CrystallizedEntityMethods)player).isCrystallized())
            ci.cancel();
    }

    @Inject(method="add", at = @At("HEAD"), cancellable = true)
    private void cancelAdd(int food, float saturationModifier, CallbackInfo ci){
        if (((CrystallizedEntityMethods)player).isCrystallized())
            ci.cancel();
    }
}
