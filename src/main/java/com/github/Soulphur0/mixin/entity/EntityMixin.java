package com.github.Soulphur0.mixin.entity;

import com.github.Soulphur0.dimensionalAlloys.CrystallizedEntityMethods;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(Entity.class)
public abstract class EntityMixin implements CrystallizedEntityMethods {

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

    @Shadow public abstract World getWorld();

    @Shadow public abstract Vec3d getPos();

    @Shadow public abstract @Nullable Entity getVehicle();

    @Shadow public abstract Text getName();

    @Shadow public abstract @Nullable Entity getPrimaryPassenger();

    // $ Comet ---------------------------------------------------------------------------------------------------------

    // _ Crystallization process' attributes.
    public int inFreshEndMedium;
    private static final TrackedData<Integer> CRYSTALLIZED_TICKS = DataTracker.registerData(Entity.class, TrackedDataHandlerRegistry.INTEGER);
    private String statueMaterial;
    private boolean crystallizedByStatusEffect;
    protected int crystallizationCooldown;
    protected float onCrystallizationBodyYaw;

    @Override
    public int getCrystallizationFinishedTicks(){
        return 140;
    }

    @Override
    public float getCrystallizationScale(){
        int max = this.getCrystallizationFinishedTicks();
        return (float)Math.min(this.getCrystallizedTicks(), max) / (float)max;
    }

    // _ Crystallization process' accessors.
    // * End medium switches.
    @Override
    public void setInFreshEndMedium(int inFreshEndMedium){
        this.inFreshEndMedium = inFreshEndMedium;
    }

    @Override
    public boolean isInFreshEndMedium() {
        return this.inFreshEndMedium > 0;
    }

    // * Crystallization ticks accessors.
    @Override
    public void setCrystallizedTicks(int crystallizedTicks) {
        this.dataTracker.set(CRYSTALLIZED_TICKS, crystallizedTicks);
    }

    @Override
    public int getCrystallizedTicks(){
        return this.dataTracker.get(CRYSTALLIZED_TICKS);
    }

    // * Crystallization accessors.
    @Override
    public boolean isCrystallized(){
        return this.getCrystallizedTicks() >= this.getCrystallizationFinishedTicks();
    }

    // * Status effect accessors.
    public void setCrystallizedByStatusEffect(boolean crystallizedByStatusEffect) {
        this.crystallizedByStatusEffect = crystallizedByStatusEffect;
    }

    public boolean isCrystallizedByStatusEffect() {
        return crystallizedByStatusEffect;
    }

    // * On-crystallization attributes accessors.
    public float getOnCrystallizationBodyYaw(){return this.onCrystallizationBodyYaw;}

    public void setOnCrystallizationBodyYaw(float bodyYaw) { this.onCrystallizationBodyYaw = bodyYaw;}

    // _ Statue material accessors.
    @Override
    public String getStatueMaterial() {
        return statueMaterial;
    }

    // $ Injected ------------------------------------------------------------------------------------------------------

    // _ Crystallization methods.

    // ? Add data tracker for crystallization.
    @Inject(method="<init>", at = @At("TAIL"))
    public void addCrystallizedTicksTracker(EntityType type, World world, CallbackInfo ci){
        this.dataTracker.startTracking(CRYSTALLIZED_TICKS, 0);
    }

    // ? Set inFreshEndMedium to false
    // This is called every tick, in case the entity is no longer in FreshEndMedium.
    // The inFreshEndMedium attribute is set to true by the onEntityCollision() method from the FreshEndMedium class.
    @Inject(method="baseTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;updateWaterState()Z"))
    public void unsetInFreshEndMedium(CallbackInfo ci){
        if (!this.world.isClient)
            this.inFreshEndMedium = Math.max(this.inFreshEndMedium - 1, 0);
    }

    // ? Make entities vulnerable to status effects when crystallized by a potion
    @Inject(method="isInvulnerableTo", at = @At("HEAD"), cancellable = true)
    private void vulnerableToStatusEffects(DamageSource damageSource, CallbackInfoReturnable<Boolean> cir){
        Entity entityInstance = ((Entity)(Object)this);
        if (entityInstance.isCrystallizedByStatusEffect() && (damageSource.isMagic() || damageSource == DamageSource.WITHER)){
            if (entityInstance.isPlayer() && ((PlayerEntity)entityInstance).isCreative()){
                return;
            }
            cir.setReturnValue(false);
        }
    }

    // ? Make crystallized entities unable to look around.
    @Inject(method="changeLookDirection", at = @At("HEAD"), cancellable = true)
    private void cancelLookDirection(double cursorDeltaX, double cursorDeltaY, CallbackInfo ci){
        if (this.isCrystallized())
            ci.cancel();
    }

    // _ Statue nbt data.

    // ? Get statue material from entity NBT.
    @Inject(method="readNbt", at = @At(value = "INVOKE", target = "Lnet/minecraft/nbt/NbtCompound;getList(Ljava/lang/String;I)Lnet/minecraft/nbt/NbtList;", ordinal = 0))
    private void putStatueMaterial(NbtCompound nbt, CallbackInfo ci){
        this.statueMaterial = nbt.getString("StatueMaterial");
    }
}