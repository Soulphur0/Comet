package com.github.Soulphur0.mixin.entity;

import com.github.Soulphur0.registries.CometBlocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.GameOptions;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageTracker;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collection;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends EntityMixin {

    // $ Comet ---------------------------------------------------------------------------------------------------------

    @Shadow public abstract float getHeadYaw();

    @Shadow public abstract boolean isAlive();

    @Shadow public abstract boolean isClimbing();

    @Shadow public abstract DamageTracker getDamageTracker();

    GameOptions settings = MinecraftClient.getInstance().options;
    private boolean isPlayerEntityMoving(){
        boolean playerMoved = this.settings.forwardKey.isPressed() || this.settings.backKey.isPressed() || this.settings.leftKey.isPressed() || this.settings.rightKey.isPressed() || this.settings.jumpKey.isPressed() || this.settings.sneakKey.isPressed();

        if (this.finishedCrystallization && this.getHeadYaw() != onCrystallizationRotation){
            playerMoved = true;
        }

        return playerMoved;
    }

    private void playBreakFreeSound(){
        world.playSound(null,
                this.getBlockPos(),
                SoundEvents.BLOCK_GLASS_BREAK,
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

    private static int scSwitch;
    private boolean finishedCrystallization = false;
    float onCrystallizationRotation;

    @Inject(method="tickMovement", at = @At("HEAD"))
    public void modifyCrystallizedTicks(CallbackInfo ci){
        if (!this.world.isClient){
            int crystallizedTicks = this.getCrystallizedTicks();

            // - Update ticks for entities in end medium.
            // * Update ticks whether the entity is in, or left medium.
            // Completely crystallized creatures (exclusively the player since others de-spawn) remain crystallized.
            if (this.inFreshEndMedium > 0)
                setCrystallizedTicks(Math.min(this.getCrystallizationFinishedTicks(), crystallizedTicks + 1));
            else if (!this.isCrystallized()) {
                this.playBreakFreeSound();
                this.setCrystallizedTicks(0);
            }

            // * Update ticks if the player moved while in medium.
            if (this.isPlayer()){
                if (this.isPlayerEntityMoving() || this.getDamageTracker().hasDamage()){
                    if (crystallizedTicks >= 20)
                        this.playBreakFreeSound();
                    this.setCrystallizedTicks(0);
                }
            }

            // - Apply effects for fully crystallized entities
            if (this.isCrystallized()){
                // * Apply on-crystallization effects.
                if (!this.finishedCrystallization){
                    if (this.isPlayer()){
                        // Set player invulnerable.
                        this.setNoGravity(true);
                        this.setInvulnerable(true);
                        this.onCrystallizationRotation = this.getHeadYaw();
                    }
                    this.playFinishedCrystallizationSound();
                    this.finishedCrystallization = true;
                }

                // * Turn non-player entities into blocks.
                if (!this.isPlayer()){
                    NbtCompound nbtCompound = new NbtCompound();
                    this.saveNbt(nbtCompound);

                    this.world.setBlockState(this.getBlockPos(), CometBlocks.CRYSTALLIZED_CREATURE.getDefaultState());
                    this.world.getBlockEntity(this.getBlockPos(), CometBlocks.CRYSTALLIZED_CREATURE_BLOCK_ENTITY).ifPresent((blockEntity) -> {
                        blockEntity.writeData(nbtCompound);
                    });

                    this.discard();
                }
            } else {
                // * Remove player invulnerability.
                if (this.finishedCrystallization){
                    this.setNoGravity(false);
                    this.setInvulnerable(false);
                    this.finishedCrystallization = false;
                }
            }

            // Sync client
            scSwitch = this.inFreshEndMedium;
        } else {
            this.setInFreshEndMedium(scSwitch);
        }
    }

    @Inject(method="isPushable", at=@At("HEAD"), cancellable = true)
    public void isPushableInject(CallbackInfoReturnable<Boolean> cir){
        cir.setReturnValue(this.isAlive() && !this.isSpectator() && !this.isClimbing() && !this.isCrystallized());
    }
}