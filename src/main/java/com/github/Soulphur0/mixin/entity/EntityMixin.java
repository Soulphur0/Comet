package com.github.Soulphur0.mixin.entity;

import com.github.Soulphur0.dimensionalAlloys.CrystallizedEntityMethods;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityMixin implements CrystallizedEntityMethods {

    // $ Added methods

    @Override
    public void setInFreshEndMedium(int inFreshEndMedium){
        this.inFreshEndMedium = inFreshEndMedium;
    }

    @Override
    public boolean isInFreshEndMedium() {
        return this.inFreshEndMedium > 0;
    }

    @Override
    public void setCrystallizedTicks(int crystallizedTicks) {
        this.dataTracker.set(CRYSTALLIZED_TICKS, crystallizedTicks);
    }

    @Override
    public int getCrystallizedTicks(){
        return this.dataTracker.get(CRYSTALLIZED_TICKS);
    }

    @Override
    public boolean isCrystallized(){
        return this.getCrystallizedTicks() >= this.getCrystallizationFinishedTicks();
    }

    @Override
    public int getCrystallizationFinishedTicks(){
        return 140;
    }

    @Override
    public float getCrystallizationScale(){
        int max = this.getCrystallizationFinishedTicks();
        return (float)Math.min(this.getCrystallizedTicks(), max) / (float)max;
    }

    // $ Injections

    @Shadow
    public World world;

    @Shadow @Final protected DataTracker dataTracker;

    @Shadow public abstract boolean isPlayer();

    @Shadow public abstract BlockPos getBlockPos();

    @Shadow public abstract void setInvulnerable(boolean invulnerable);

    @Shadow public abstract void discard();

    @Shadow public abstract boolean saveNbt(NbtCompound nbt);

    @Shadow public abstract float getHeadYaw();

    @Shadow public abstract void setNoGravity(boolean noGravity);

    @Shadow public abstract boolean isSpectator();

    private static final TrackedData<Integer> CRYSTALLIZED_TICKS = DataTracker.registerData(Entity.class, TrackedDataHandlerRegistry.INTEGER);
    public int inFreshEndMedium;

    @Inject(method="<init>", at = @At("TAIL"))
    public void addCrystallizedTicksTracker(EntityType type, World world, CallbackInfo ci){
        this.dataTracker.startTracking(CRYSTALLIZED_TICKS, 0);
    }

    // - Set inFreshEndMedium to false
    // Calls in every tick in case the entity is no longer in FreshEndMedium.
    @Inject(method="baseTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;updateWaterState()Z"))
    public void unsetInFreshEndMedium(CallbackInfo ci){
        if (!this.world.isClient)
            this.inFreshEndMedium = Math.max(this.inFreshEndMedium - 1, 0);
    }

}
