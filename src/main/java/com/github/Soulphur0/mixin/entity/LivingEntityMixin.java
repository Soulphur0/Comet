package com.github.Soulphur0.mixin.entity;

import com.github.Soulphur0.Comet;
import com.github.Soulphur0.registries.CometBlocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.GameOptions;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageTracker;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
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

    // $ Comet ---------------------------------------------------------------------------------------------------------

    // _ Crystallization interrupt.
    GameOptions settings = MinecraftClient.getInstance().options;
    float onCrystallizationPlayerLookDirection;
    public boolean isPlayerInterruptingCrystallization(){
        boolean playerMoved = this.settings.forwardKey.isPressed() || this.settings.backKey.isPressed() || this.settings.leftKey.isPressed() || this.settings.rightKey.isPressed() || this.settings.jumpKey.isPressed() || this.settings.sneakKey.isPressed() || this.settings.attackKey.isPressed() || this.settings.useKey.isPressed()|| this.settings.pickItemKey.isPressed();

        if (this.finishedCrystallization && this.getHeadYaw() != onCrystallizationPlayerLookDirection){
            playerMoved = true;
        }

        return playerMoved;
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
    public void modifyCrystallizedTicks(CallbackInfo ci){
        if (!this.world.isClient){
            int crystallizedTicks = this.getCrystallizedTicks();

            // + Update ticks for entities in end medium.
            // - Update ticks whether the entity is in, or left medium.
            // Completely crystallized creatures (exclusively the player since others de-spawn) remain crystallized.
            if (this.inFreshEndMedium > 0)
                setCrystallizedTicks(Math.min(this.getCrystallizationFinishedTicks(), crystallizedTicks + 1));
            else if (!this.isCrystallized()) {
                this.playBreakFreeSound();
                this.setCrystallizedTicks(0);
            }

            // - Update ticks if the player moved while in medium.
            if (this.isPlayer()){
                if (this.isPlayerInterruptingCrystallization() || this.getDamageTracker().hasDamage()){
                    if (crystallizedTicks >= 20)
                        this.playBreakFreeSound();
                    this.setCrystallizedTicks(0);
                }
            }

            // + Apply effects for fully crystallized entities
            if (this.isCrystallized()){
                // - Trigger on-crystallization events.
                if (!this.finishedCrystallization){
                    // To player.
                    if (this.isPlayer()){
                        this.setNoGravity(true);
                        this.setInvulnerable(true);
                        this.onCrystallizationPlayerLookDirection = this.getHeadYaw();
                    }
                    // To all entities.
                    this.playFinishedCrystallizationSound();
                    this.finishedCrystallization = true;
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
                if (!this.isPlayer()){
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
                // - Reset player to normal state.
                if (this.isPlayer() && this.finishedCrystallization){
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
            }

            // Sync client
            scSwitch = this.inFreshEndMedium;
        } else {
            this.setInFreshEndMedium(scSwitch);
        }
    }

    // ? Make crystallized player un-pushable.
    @Inject(method="isPushable", at=@At("HEAD"), cancellable = true)
    public void isPushableInject(CallbackInfoReturnable<Boolean> cir){
        cir.setReturnValue(this.isAlive() && !this.isSpectator() && !this.isClimbing() && !this.isCrystallized());
    }
}