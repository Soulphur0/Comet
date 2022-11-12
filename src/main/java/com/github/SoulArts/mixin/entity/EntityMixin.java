package com.github.SoulArts.mixin.entity;

import com.github.SoulArts.dimensionalAlloys.CrystallizedEntityMethods;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
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
    public void setInFreshEndMedium(boolean inFreshEndMedium){
        this.inFreshEndMedium = inFreshEndMedium;
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
    public int getCrystallizationFinishedTicks(){
        return 1400;
    }

    @Override
    public boolean isCrystallized(){
        return this.getCrystallizedTicks() >= this.getCrystallizationFinishedTicks();
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

    @Shadow public abstract BlockPos getBlockPos();

    private static final TrackedData<Integer> CRYSTALLIZED_TICKS = DataTracker.registerData(Entity.class, TrackedDataHandlerRegistry.INTEGER);
    public boolean inFreshEndMedium;

    @Inject(method="<init>", at = @At("TAIL"))
    public void addCrystallizedTicksTracker(EntityType type, World world, CallbackInfo ci){
        this.dataTracker.startTracking(CRYSTALLIZED_TICKS, 0);
    }

    // - Set inFreshEndMedium to false
    // Calls in every tick in case the entity is no longer in FreshEndMedium
    @Inject(method="baseTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;updateWaterState()Z"))
    public void unsetInFreshEndMedium(CallbackInfo ci){
        // ! DEBUG
        if (this.world.isClient())
            System.out.println("Ticks: " + getCrystallizedTicks());
        // ! DEBUG


        if(this.world.isClient())
            this.inFreshEndMedium = false;
    }

}
