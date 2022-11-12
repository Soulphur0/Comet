package com.github.SoulArts.mixin.entity;

import com.github.SoulArts.dimensionalAlloys.CrystallizedEntityMethods;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends EntityMixin {

    // $ Injected ------------------------------------------------------------------------------------------------------

    @Shadow public abstract boolean damage(DamageSource source, float amount);

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

            scSwitch = this.inFreshEndMedium;
        } else {
            this.setInFreshEndMedium(scSwitch);
        }

    }
}