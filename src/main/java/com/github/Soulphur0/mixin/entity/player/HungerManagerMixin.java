package com.github.Soulphur0.mixin.entity.player;

import com.github.Soulphur0.dimensionalAlloys.EntityCometBehaviour;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HungerManager.class)
public class HungerManagerMixin {

    // ? Cancel natural health regeneration during crystallization.
    PlayerEntity player;
    @Inject(method="update", at= @At("HEAD"), cancellable = true)
    private void cancelUpdate(PlayerEntity player, CallbackInfo ci){
        this.player = player;
        if (((EntityCometBehaviour)player).isCrystallized())
            ci.cancel();
    }
}
