package com.github.SoulArts.mixin.entity;

import com.github.SoulArts.registries.CometBlocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.GameOptions;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends EntityMixin {

    // $ Comet ---------------------------------------------------------------------------------------------------------

    private boolean isPlayerEntityMoving(){
        return this.settings.forwardKey.isPressed() || this.settings.backKey.isPressed() || this.settings.leftKey.isPressed() ||
                this.settings.rightKey.isPressed() || this.settings.jumpKey.isPressed() || this.settings.sneakKey.isPressed();
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

    GameOptions settings;

    private static int scSwitch;
    private boolean finishedCrystallization = false;
    @Inject(method="tickMovement", at = @At("HEAD"))
    public void modifyCrystallizedTicks(CallbackInfo ci){
        if (!this.world.isClient){
            int crystallizedTicks = this.getCrystallizedTicks();

            // Update ticks whether the entity is in or left medium.
            if (this.inFreshEndMedium > 0)
                setCrystallizedTicks(Math.min(this.getCrystallizationFinishedTicks(), crystallizedTicks + 1));
            else {
                this.playBreakFreeSound();
                this.setCrystallizedTicks(0);
            }

            // Update ticks if the player moved while in medium.
            if (this.isPlayer()){
                this.settings = MinecraftClient.getInstance().options;
                if (this.isPlayerEntityMoving()){
                    if (crystallizedTicks >= 20)
                        this.playBreakFreeSound();
                    this.setCrystallizedTicks(0);
                }
            }

            // - Apply effects for fully crystallized entities
            // todo make it so player is invulnerable if crystallized, but not remove player invulnerability in every tick
            if (this.isCrystallized()){
                if (!this.finishedCrystallization){
                    this.playFinishedCrystallizationSound();
                    this.finishedCrystallization = true;
                }

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
                this.finishedCrystallization = false;
            }


            // Sync client
            scSwitch = this.inFreshEndMedium;
        } else {
            this.setInFreshEndMedium(scSwitch);
        }
    }
}