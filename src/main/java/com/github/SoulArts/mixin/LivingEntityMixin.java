package com.github.SoulArts.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    @Shadow @Final public int defaultMaxHealth;
    private Vec3d movementVector;

    protected LivingEntityMixin (EntityType<? extends LivingEntity> entityType, World world){
        super(entityType,world);
    }

    @ModifyVariable(method = "travel(Lnet/minecraft/util/math/Vec3d;)V", at = @At("STORE"), ordinal = 1)
    private Vec3d getMovementVector(Vec3d vector) {
        movementVector = vector;
        return vector;
    }

    @ModifyArg(method = "travel(Lnet/minecraft/util/math/Vec3d;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;setVelocity(Lnet/minecraft/util/math/Vec3d;)V", ordinal = 6))
    private Vec3d modifyVelocity(Vec3d vector){
        final double maxAddedMultiplier = 0.0088; // 0.007875=defalut 0.0088=257ms 0.009=310ms
        Vec3d positionVector = super.getPos();
        double playerAltitude = positionVector.y;
        double divider = calcDivider(playerAltitude);
        return movementVector.multiply(0.9900000095367432D + 0.00013/*maxAddedMultiplier/divider*/, 0.9800000190734863D, 0.9900000095367432D + 0.00013/*maxAddedMultiplier/divider*/);
    }

    private double calcDivider(double altitude){
        if (altitude < 250)
            return Math.pow(-0.0355101518068*altitude+10,2);
        else if (altitude > 250 && altitude < 1000)
            return Math.pow(-0.000163282731066666*altitude+1.16328273106666,2);
        else
            return 1;
    }
}
