package com.github.Soulphur0.mixin.entity;

import com.github.Soulphur0.dimensionalAlloys.EntityCometBehaviour;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin implements EntityCometBehaviour {

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

    @Shadow protected abstract int getBurningDuration();

    @Shadow public abstract int getFireTicks();

    @Shadow public abstract Vec3d getEyePos();

    @Shadow public abstract void setAir(int air);

    @Shadow public abstract int getAir();

    // $ Comet ---------------------------------------------------------------------------------------------------------

    // _ Attributes.
    // ? Crystallization process' attributes
    public int inFreshEndMedium;
    private static final TrackedData<Integer> CRYSTALLIZED_TICKS = DataTracker.registerData(Entity.class, TrackedDataHandlerRegistry.INTEGER);
    private boolean crystallizedByStatusEffect;
    protected int crystallizationCooldown;
    protected float onCrystallizationBodyYaw;

    private String statueMaterial;

    // ? Additional fire behaviour attributes
    private int soulFireTicks =  -this.getBurningDuration();
    private int endFireTicks = -this.getBurningDuration();


    // _ Crystallization process' methods.
    @Override
    public int getMaxCrystallizedTicks(){
        return 140;
    }

    @Override
    public float getCrystallizationScale(){
        int max = this.getMaxCrystallizedTicks();
        return (float)Math.min(this.getCrystallizedTicks(), max) / (float)max;
    }

    // ? Accessors
    // * End medium switches.
    @Override
    public void setInEndMedium(int inFreshEndMedium){
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
        return this.getCrystallizedTicks() >= this.getMaxCrystallizedTicks();
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

    // ? Statue methods.
    public String getStatueMaterial() {
        return statueMaterial;
    }

    // _ Fire extra behaviour methods
    // ? Accessors
    // * Soul fire ticks accessors
    public void setSoulFireTicks(int ticks) {
        this.soulFireTicks = ticks;
    }

    public int getSoulFireTicks() {
        return this.soulFireTicks;
    }

    // * End fire ticks accessors
    public void setEndFireTicks(int ticks) {
        this.endFireTicks = ticks;
    }

    public int getEndFireTicks() {
        return this.endFireTicks;
    }

    // $ Injected ------------------------------------------------------------------------------------------------------

    // _ Generic injects.
    // ? Add data tracker for comet behaviours.
    @Inject(method="<init>", at = @At("TAIL"))
    public void comet_addDataTrackers(EntityType type, World world, CallbackInfo ci){
        this.dataTracker.startTracking(CRYSTALLIZED_TICKS, 0);
        this.dataTracker.startTracking(COMET_FLAGS, (byte)0);
    }

    // _ Crystallization methods.
    // ? Set inFreshEndMedium to false
    // This is called every tick, in case the entity is no longer in EndMediumLayer.
    // The inFreshEndMedium attribute is set to true by the onEntityCollision() method from the EndMediumLayer class.
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

    // _ Fire additional behaviour
    private static final TrackedData<Byte> COMET_FLAGS = DataTracker.registerData(Entity.class, TrackedDataHandlerRegistry.BYTE);
    private static final int ON_SOUL_FIRE_FLAG_INDEX = 0;
    private static final int ON_END_FIRE_FLAG_INDEX = 1;

    private void setCometFlag(int index, boolean value) {
        byte b = this.dataTracker.get(COMET_FLAGS);
        if (value) {
            this.dataTracker.set(COMET_FLAGS, (byte)(b | 1 << index));
        } else {
            this.dataTracker.set(COMET_FLAGS, (byte)(b & ~(1 << index)));
        }
    }

    private boolean getCometFlag(int index) {
        return (this.dataTracker.get(COMET_FLAGS) & 1 << index) != 0;
    }

    public void setOnSoulFire(boolean onFire) {
        this.setCometFlag(ON_SOUL_FIRE_FLAG_INDEX, onFire);
    }

    public void setOnEndFire(boolean onFire) {
        this.setCometFlag(ON_END_FIRE_FLAG_INDEX, onFire);
    }

    public boolean isOnSoulFire() {
        boolean bl = this.world != null && this.world.isClient;
        return !((Entity)(Object)this).isFireImmune() && (((Entity)(Object)this).getSoulFireTicks() > 0 || bl && this.getCometFlag(ON_SOUL_FIRE_FLAG_INDEX));
    }

    public boolean isOnEndFire() {
        boolean bl = this.world != null && this.world.isClient;
        return !((Entity)(Object)this).isFireImmune() && (((Entity)(Object)this).getSoulFireTicks() > 0 || bl && this.getCometFlag(ON_END_FIRE_FLAG_INDEX));
    }

    public boolean doesRenderOnSoulFire() {
        return this.isOnSoulFire() && !this.isSpectator();
    }

    public boolean doesRenderOnEndFire() {
        return this.isOnEndFire() && !this.isSpectator();
    }

    @Inject(method="baseTick", at = @At("HEAD"))
    private void updateAdditionalFireBehaviour(CallbackInfo ci){
        if (!this.getWorld().isClient()){
            // Override fire type if it was more recent.
            if (this.getSoulFireTicks() > -20){
                this.setEndFireTicks(-20);
            } else if (this.getEndFireTicks() > -20) {
                this.setSoulFireTicks(-20);
            }

            // Update fire duration
            this.soulFireTicks = (this.soulFireTicks > -20) ? this.getFireTicks() : -20;
            this.endFireTicks = (this.endFireTicks > -20) ? this.getFireTicks() : -20;

            // Set ticks to zero if fire is extinguished
            this.soulFireTicks = (this.getFireTicks() <= -20) ? -20 : this.soulFireTicks;
            this.endFireTicks = (this.getFireTicks() <= -20) ? -20 : this.endFireTicks;

            // Set on fire for the data tracker to track it.
            if (this.isPlayer()){
                this.setOnSoulFire(this.soulFireTicks > -20);
                this.setOnEndFire(this.endFireTicks > -20);
            } else {
                this.setOnSoulFire(this.soulFireTicks > 0);
                this.setOnEndFire(this.endFireTicks > 0);
            }

            //. Update ticks to client might need this for server.
//            if (this.isPlayer()){
//                PacketByteBuf soulFireTicks = PacketByteBufs.create();
//                PacketByteBuf endFireTicks = PacketByteBufs.create();
//
//                soulFireTicks.writeInt(this.soulFireTicks);
//                endFireTicks.writeInt(this.endFireTicks);
//
//                ServerPlayNetworking.send(((ServerPlayerEntity)(Object)this), new Identifier("comet","soul_fire_ticks"), soulFireTicks);
//                ServerPlayNetworking.send(((ServerPlayerEntity)(Object)this), new Identifier("comet","end_fire_ticks"), endFireTicks);
//            }
       }
    }

    //
    @Inject(method="baseTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z"))
    private void applyAdditionalFireBehaviour(CallbackInfo ci){
        Entity thisInstance = ((Entity)(Object)this);

        // Apply fire effects.
        if (this.soulFireTicks > -20 && !thisInstance.isInLava())
            thisInstance.damage(DamageSource.ON_FIRE, 2.0f);

        if (this.endFireTicks > -20 && !thisInstance.isInLava() && thisInstance instanceof LivingEntity livingEntity) {
            if (!world.isClient)
                teleportRandomly(livingEntity);
        }
    }

    protected void teleportRandomly(LivingEntity livingEntity){
        double x = livingEntity.getX();
        double y = livingEntity.getY();
        double z = livingEntity.getZ();
        for (int i = 0; i < 16; ++i) {
            // Get position in square.
            double g = livingEntity.getX() + (livingEntity.getRandom().nextDouble() - 0.5) * 16.0;
            double h = MathHelper.clamp(livingEntity.getY() + (double)(livingEntity.getRandom().nextInt(16) - 8), (double)world.getBottomY(), (double)(world.getBottomY() + ((ServerWorld)world).getLogicalHeight() - 1));
            double j = livingEntity.getZ() + (livingEntity.getRandom().nextDouble() - 0.5) * 16.0;

            // Dismount vehicle.
            if (livingEntity.hasVehicle()) {
                livingEntity.stopRiding();
            }

            // Get pre-teleport position
            Vec3d vec3d = livingEntity.getPos();

            // Try teleport
            if (!livingEntity.teleport(g, h, j, true)) continue;

            // Emit event in pre-teleport position & play sound
            world.emitGameEvent(GameEvent.TELEPORT, vec3d, GameEvent.Emitter.of(livingEntity));
            world.playSound(null, x, y, z, SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT, SoundCategory.PLAYERS, 1.0f, 1.0f);
            livingEntity.playSound(SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT, 1.0f, 1.0f);
            break;
        }
    }
}