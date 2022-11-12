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

    @Inject(method="tickMovement", at = @At("HEAD"))
    public void modifyCrystallizedTicks(CallbackInfo ci){
        int crystallizedTicks = this.getCrystallizedTicks();
        /*
        if (!this.world.isClient && this.inFreshEndMedium)
            setCrystallizedTicks(Math.min(this.getCrystallizationFinishedTicks(), crystallizedTicks + 1));
        else if (!this.world.isClient)*/
            this.setCrystallizedTicks(Math.max(0, crystallizedTicks-1));

        // ! Debug: damage by standing
        if(!this.world.isClient && this.isCrystallized()){
            this.damage(DamageSource.DRAGON_BREATH, 5);
        }
    }
}