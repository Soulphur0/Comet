package com.github.Soulphur0.mixin.entity;

import com.github.Soulphur0.Comet;
import com.github.Soulphur0.dimensionalAlloys.entity.effect.CrystallizedStatusEffect;
import com.github.Soulphur0.registries.CometBlocks;
import com.llamalad7.mixinextras.injector.WrapWithCondition;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.GameOptions;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTracker;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.ElderGuardianEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.RavagerEntity;
import net.minecraft.entity.mob.WardenEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collection;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends EntityMixin {

    @Shadow public abstract float getHeadYaw();

    @Shadow public abstract boolean isAlive();

    @Shadow public abstract boolean isClimbing();

    @Shadow public abstract DamageTracker getDamageTracker();

    @Shadow public abstract Collection<StatusEffectInstance> getStatusEffects();

    @Shadow protected abstract void markEffectsDirty();

    @Shadow public abstract float getBodyYaw();

    @Shadow protected abstract int getNextAirUnderwater(int air);

    @Shadow public abstract boolean damage(DamageSource source, float amount);

    // $ Comet ---------------------------------------------------------------------------------------------------------
    // _ Crystallization interrupt.
    GameOptions settings = MinecraftClient.getInstance().options;
    public boolean isCrystallizationInterrupted(){
        // + Player interrupted crystallization by moving.
        boolean interrupted = this.settings.forwardKey.isPressed() || this.settings.backKey.isPressed() || this.settings.leftKey.isPressed() || this.settings.rightKey.isPressed() || this.settings.jumpKey.isPressed() || this.settings.attackKey.isPressed() || this.settings.useKey.isPressed()|| this.settings.pickItemKey.isPressed();

        // + Crystallization got interrupted by damage.
        interrupted = this.getDamageTracker().hasDamage() || interrupted;

        return !this.isCrystallizedByStatusEffect() && interrupted;
    }

    // _ Sound events.
    private void playBreakFreeSound(){
        world.playSound(null,
                this.getBlockPos(),
                Comet.CRYSTALLIZATION_BREAKS,
                SoundCategory.BLOCKS,
                this.getCrystallizationScale(),
                1f);
    }

    private void playFinishedCrystallizationSound(){
        world.playSound(null,
                this.getBlockPos(),
                SoundEvents.BLOCK_RESPAWN_ANCHOR_CHARGE,
                SoundCategory.BLOCKS,
                1f,
                1f);
    }

    // $ Injected ------------------------------------------------------------------------------------------------------

    // _ Crystallization methods.

    // ? Entity crystallization process.
    private static int scSwitch;
    private boolean finishedCrystallization = false;
    @Inject(method="tickMovement", at = @At("HEAD"))
    public void updateCrystallizedTicks(CallbackInfo ci){
        if (!this.world.isClient){
            int crystallizedTicks = this.getCrystallizedTicks();

            // + Update ticks for entities in end medium.
            // - Update ticks whether the entity is in, or left medium.
            // Completely crystallized creatures (exclusively the player since others de-spawn) remain crystallized.
            if (this.inFreshEndMedium > 0)
                setCrystallizedTicks(Math.min(this.getMaxCrystallizedTicks(), crystallizedTicks + 1));
            else if (!this.isCrystallized()) {
                this.playBreakFreeSound();
                this.setCrystallizedTicks(0);
            }

            // - Update ticks if the player moved while in medium or got hit before crystallized.
            if (this.isPlayer()){
                if (this.isCrystallizationInterrupted()){
                    if (crystallizedTicks >= 20)
                        this.playBreakFreeSound();
                    this.setCrystallizedTicks(0);
                }
            }

            // + Apply effects for fully crystallized entities
            if (this.isCrystallized()){
                // - Trigger on-crystallization events.
                if (!this.finishedCrystallization){
                    this.finishedCrystallization = true;

                    // To mobs
                    if (((LivingEntity)(Object)this) instanceof MobEntity mobEntity){
                        mobEntity.setSilent(true);
                    }

                    // To all entities.
                    this.onCrystallizationBodyYaw = this.getBodyYaw();
                    this.setNoGravity(true);
                    this.setInvulnerable(true);
                    this.playFinishedCrystallizationSound();
                }

                // - Hide particle effects.
                this.getStatusEffects().forEach(effect ->{
                    if (effect.shouldShowParticles()){
                        effect.setShowParticles(false);
                        effect.setHiddenByCrystallization(true);
                    }
                });
                this.markEffectsDirty();

                // - Turn non-player entities into blocks.
                if (!this.isPlayer() && !this.isCrystallizedByStatusEffect()){
                    NbtCompound nbtCompound = new NbtCompound();

                    NbtCompound mobDataCompound = new NbtCompound();
                    this.saveNbt(mobDataCompound);
                    nbtCompound.put("mobData",mobDataCompound);

                    this.world.setBlockState(this.getBlockPos(), CometBlocks.CRYSTALLIZED_CREATURE.getDefaultState());
                    this.world.getBlockEntity(this.getBlockPos(), CometBlocks.CRYSTALLIZED_CREATURE_BLOCK_ENTITY).ifPresent((blockEntity) -> {
                        blockEntity.writeData(nbtCompound);
                    });

                    this.discard();
                }
            } else {
                // - Undo on-crystallization effects.
                if (this.finishedCrystallization){
                    this.setNoGravity(false);
                    this.setInvulnerable(false);

                    this.finishedCrystallization = false;

                    this.getStatusEffects().forEach(effect ->{
                        if (effect.isHiddenByCrystallization()){
                            effect.setShowParticles(true);
                            effect.setHiddenByCrystallization(false);
                        }
                    });
                    this.markEffectsDirty();
                }

                // For mobs
                if (((LivingEntity)(Object)this) instanceof MobEntity mobEntity){
                    mobEntity.setSilent(false);
                }

                // Tick crystallization effect counter down
                this.crystallizationCooldown = (this.crystallizationCooldown > 0) ? this.crystallizationCooldown-1  : this.crystallizationCooldown;
            }

            // Sync client
            scSwitch = this.inFreshEndMedium;
        } else {
            this.setInEndMedium(scSwitch);
        }
    }

    // ? Make crystallized player un-pushable.
    @Inject(method="isPushable", at=@At("HEAD"), cancellable = true)
    public void isPushableInject(CallbackInfoReturnable<Boolean> cir){
        cir.setReturnValue(this.isAlive() && !this.isSpectator() && !this.isClimbing() && !this.isCrystallized());
    }

    // ? Make crystallized entities unable to move, either is the player, a mob, or a vehicle.
    @Inject(method="travel", at = @At("HEAD"), cancellable = true)
    private void cancelTravel(Vec3d movementInput, CallbackInfo ci){
        if (this.isCrystallized()){
            ci.cancel();
        }

        if (this.getVehicle() != null){
            if (this.getVehicle().isCrystallized()){
                ci.cancel();
            }
        }

        if(this.getPrimaryPassenger() != null){
            if(this.getPrimaryPassenger().isCrystallized()){
                ci.cancel();
            }
        }
    }

    // ? Make crystallized mobs unable to move without disabling their AI.
    @Inject(method = "canMoveVoluntarily", at = @At("HEAD"), cancellable = true)
    private void restrictMovement(CallbackInfoReturnable<Boolean> cir){
        if (this.isCrystallized())
            cir.setReturnValue(false);
    }

    // ? Custom behaviour when receiving status effects.
    // If the entity is getting the crystallization effect by other than itself, it won't allow to apply the effect until a cooldown equivalent to double the length of the effect has passed.
    // If the entity is crystallized, it can't get more effects.
    // If the entity is a mini-boss, the effect lasts a third of the duration.
    @Inject(method="addStatusEffect(Lnet/minecraft/entity/effect/StatusEffectInstance;Lnet/minecraft/entity/Entity;)Z", at = @At("HEAD"), cancellable = true)
    private void addStatusEffect_cometExtraBehaviour(StatusEffectInstance effect, Entity source, CallbackInfoReturnable<Boolean> cir){
        LivingEntity thisLivingEntity = ((LivingEntity)(Object)this);

        if (effect.getEffectType() instanceof CrystallizedStatusEffect){
            if (source != thisLivingEntity) {
                if (crystallizationCooldown <= 0) {
                    this.crystallizationCooldown = effect.getDuration() * 2;
                } else {
                    cir.setReturnValue(false);
                }
            }
        }

        if (thisLivingEntity.isCrystallized()){
            cir.setReturnValue(false);
        }

        if (thisLivingEntity instanceof ElderGuardianEntity || thisLivingEntity instanceof RavagerEntity || thisLivingEntity instanceof WardenEntity){
            effect.setDuration(effect.getDuration()/3);
        }
    }

    // _ Make entities submerged in end medium lose air and drown.

    // ? Tick air
    @Inject(method="baseTick", at = @At("HEAD"))
    private void tickAir_cometExtraBehaviour(CallbackInfo ci){
        // + Check if the entity's head is submerged in end medium, if so, lose air.
        if (world.getBlockState(new BlockPos(this.getEyePos())).getBlock() == CometBlocks.END_MEDIUM && !((LivingEntity)(Object)this).isCrystallized()){
            this.setAir(this.getNextAirUnderwater(this.getAir()));

            // - If the entity has no air left, take damage.
            // There's an Easter egg death message with a small chance.
            if (this.getAir() == -20) {
                this.setAir(0);
                Random random = Random.create();
                float damageTypeChance = random.nextFloat();
                if (damageTypeChance > 0.15)
                    this.damage(Comet.END_MEDIUM_DROWN, 2.0f);
                else
                    this.damage(Comet.END_MEDIUM_DROWN_RARE, 2.0f);
            }
        }
    }

    // ? Make the air counter not reset if the entity is submerged in end medium.
    @WrapWithCondition(method="baseTick", at = @At(value ="INVOKE", target="Lnet/minecraft/entity/LivingEntity;setAir(I)V", ordinal = 2))
    private boolean isOutsideOfEndMedium_cometExtraBehaviour(LivingEntity instance, int air){
        return !(world.getBlockState(new BlockPos(this.getEyePos())).getBlock() == CometBlocks.END_MEDIUM) && !instance.isCrystallized();
    }
}