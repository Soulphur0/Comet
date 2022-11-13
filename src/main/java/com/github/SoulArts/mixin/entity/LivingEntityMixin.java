package com.github.SoulArts.mixin.entity;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.input.Input;
import net.minecraft.client.input.KeyboardInput;
import net.minecraft.client.option.GameOptions;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends EntityMixin {

    private boolean isPlayerEntityMoving(){
        return this.settings.forwardKey.isPressed() || this.settings.backKey.isPressed() || this.settings.leftKey.isPressed() ||
                this.settings.rightKey.isPressed() || this.settings.jumpKey.isPressed() || this.settings.sneakKey.isPressed();
    }


    // $ Injected ------------------------------------------------------------------------------------------------------

    @Shadow public abstract boolean damage(DamageSource source, float amount);

    GameOptions settings;

    private static int scSwitch;
    @Inject(method="tickMovement", at = @At("HEAD"))
    public void modifyCrystallizedTicks(CallbackInfo ci){
        if (!this.world.isClient){
            int crystallizedTicks = this.getCrystallizedTicks();

            if (this.inFreshEndMedium > 0)
                setCrystallizedTicks(Math.min(this.getCrystallizationFinishedTicks(), crystallizedTicks + 1));
            else
                this.setCrystallizedTicks(0);

            // ! Debug: damage by standing
            if(this.isCrystallized())
                this.damage(DamageSource.DRAGON_BREATH, 5);
            // ! Debug: damage by standing

            if (this.isPlayer()){
                this.settings = MinecraftClient.getInstance().options;
                if (this.isPlayerEntityMoving()){
                    this.setCrystallizedTicks(0);
                }
            }

            scSwitch = this.inFreshEndMedium;
        } else {
            this.setInFreshEndMedium(scSwitch);
        }

    }
}