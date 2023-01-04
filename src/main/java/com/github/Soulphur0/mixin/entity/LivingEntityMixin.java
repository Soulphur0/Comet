package com.github.Soulphur0.mixin.entity;

import com.github.Soulphur0.Comet;
import com.github.Soulphur0.dimensionalAlloys.entity.effect.CrystallizedStatusEffect;
import com.github.Soulphur0.registries.CometBlocks;
import com.llamalad7.mixinextras.injector.WrapWithCondition;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.GameOptions;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTracker;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.ElderGuardianEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.RavagerEntity;
import net.minecraft.entity.mob.WardenEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
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

    @Shadow protected abstract void initDataTracker();

    @Shadow public abstract void endCombat();

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
    private void playBreakFreeSound(int ticksWhenInterrupted){
        world.playSound(null,
                this.getBlockPos(),
                Comet.CRYSTALLIZATION_BREAKS,
                SoundCategory.BLOCKS,
                this.getCrystallizationScale(ticksWhenInterrupted),
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
            LivingEntity thisInstance = ((LivingEntity)(Object)this);
            int crystallizedTicks = this.getCrystallizedTicks();
            BlockPos pos = thisInstance.getBlockPos();

            // + Play sound if an entity has cancelled crystallization.
            // * Mainly the player, by moving.
            // * Mobs immune to crystallization don't spam this when they are in medium because the flag is directly not set on them.
            if (this.inFreshEndMedium > 0 && crystallizedTicks == 0 && lastCrystallizedTicks > 20)
                this.playBreakFreeSound(this.lastCrystallizedTicks);

            // + Update ticks for entities in end medium.
            // * Update ticks whether the entity is in, or left medium.
            // * Completely crystallized creatures (exclusively the player since others de-spawn) remain crystallized.
            if (this.inFreshEndMedium > 0)
                setCrystallizedTicks(Math.min(this.getMaxCrystallizedTicks(), crystallizedTicks + 1));
            else if (!this.isCrystallized()) {
                this.setCrystallizedTicks(0);
                this.playBreakFreeSound(lastCrystallizedTicks);
            }

            // . Play sound from the entity as crystallization grows.
            PacketByteBuf growSoundPosPacket = PacketByteBufs.create().writeString(this.getUuidAsString());
            if (thisInstance instanceof ServerPlayerEntity)
                ServerPlayNetworking.send((ServerPlayerEntity) thisInstance, new Identifier("comet", "crystallization_grows"), growSoundPosPacket);
            for (ServerPlayerEntity serverPlayer : PlayerLookup.tracking(thisInstance)) {
                ServerPlayNetworking.send((ServerPlayerEntity) serverPlayer, new Identifier("comet", "crystallization_grows"), growSoundPosPacket);
            }

            // + Trigger events for fully crystallized entities.
            // * Apply only once, on crystallization.
            if (this.isCrystallized()){
                if (!this.finishedCrystallization){
                    this.finishedCrystallization = true;

                    // - To mobs
                    if (thisInstance instanceof MobEntity mobEntity){
                        mobEntity.setSilent(true);
                    }

                    // - To all entities.
                    this.onCrystallizationBodyYaw = this.getBodyYaw();
                    this.setNoGravity(true);
                    this.setInvulnerable(true);
                    this.playFinishedCrystallizationSound();

                    // - Hide particle effects.
                    // Since entities can't get more effects if they are crystallized hide particles just once.
                    // Effects can still be added with commands, showing particles, but doing this continuously is less efficient.
                    this.getStatusEffects().forEach(effect ->{
                        if (effect.shouldShowParticles()){
                            effect.setShowParticles(false);
                            effect.setHiddenByCrystallization(true);
                        }
                    });
                    this.markEffectsDirty();
                }

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
                // + Undo on-crystallization effects.
                if (this.finishedCrystallization){
                    // - Play sound and particle effects.
                    playBreakFreeSound(thisInstance.getMaxCrystallizedTicks());

                    // . Particles require to be rendered in the client world.
                    PacketByteBuf posPacket = PacketByteBufs.create().writeBlockPos(pos);
                    for (ServerPlayerEntity serverPlayer : PlayerLookup.tracking((ServerWorld) world, pos)) {
                        ServerPlayNetworking.send((ServerPlayerEntity) serverPlayer, new Identifier("comet", "decrystallization_effects"), posPacket);
                    }

                    // - Regain gravity and vulnerability.
                    this.setNoGravity(false);
                    this.setInvulnerable(false);

                    // - Un-hide particle effects.
                    this.getStatusEffects().forEach(effect ->{
                        if (effect.isHiddenByCrystallization()){
                            effect.setShowParticles(true);
                            effect.setHiddenByCrystallization(false);
                        }
                    });
                    this.markEffectsDirty();

                    // - Special events for players
                    if (thisInstance instanceof PlayerEntity player && (player.isInsideWall() || player.isInLava())){
                        teleportRandomly(player);
                        if (player.isInLava())
                            player.setEndFireTicks(player.getFireTicks());
                    }

                    // - Special events for mobs
                    if (thisInstance instanceof MobEntity mobEntity){
                        mobEntity.setSilent(false);
                    }

                    this.finishedCrystallization = false;
                }

                // + Tick crystallization effect counter down
                this.crystallizationCooldown = (this.crystallizationCooldown > 0) ? this.crystallizationCooldown-1  : this.crystallizationCooldown;
            }

            // ! Sync client
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

        if (thisLivingEntity instanceof ElderGuardianEntity || thisLivingEntity instanceof RavagerEntity || thisLivingEntity instanceof WardenEntity || thisLivingEntity instanceof IronGolemEntity){
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

    // _ Fall flying behaviour for elytra-like items.
    // ? When checking for an equipped elytra, return an elytra item if a flight-compatible item is equipped.
    @WrapOperation(method = "tickFallFlying", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z"))
    private boolean comet_checkForElytraItem(ItemStack instance, Item item, Operation<Boolean> original){
        if (instance.isOf(Comet.ENDBRITE_ELYTRA_CHESTPLATE)){
            return true;
        } else {
            return original.call(instance, item);
        }
    }

    // ? When using an endbrite elytra chestplate, check that the user is wearing a full armor set of endbrite, otherwise the elytra will split from the chestplate.
    @Inject(method="baseTick", at = @At("HEAD"))
    private void comet_checkIfLivingEntityIsWearingEndbriteSet(CallbackInfo ci){
        LivingEntity livingEntity = ((LivingEntity)(Object)this);
        boolean hasEndbriteArmor = livingEntity.getEquippedStack(EquipmentSlot.HEAD).isOf(Comet.ENDBRITE_HELMET) && livingEntity.getEquippedStack(EquipmentSlot.LEGS).isOf(Comet.ENDBRITE_LEGGINGS) && livingEntity.getEquippedStack(EquipmentSlot.FEET).isOf(Comet.ENDBRITE_BOOTS);
        ItemStack chestItem = livingEntity.getEquippedStack(EquipmentSlot.CHEST);

        // + Split endbrite elytra chestplate if the user is not wearing the rest of the armor.
        if (!hasEndbriteArmor && chestItem.isOf(Comet.ENDBRITE_ELYTRA_CHESTPLATE)){
            // - Retrieve elytra data from when the items were combined.
            NbtCompound elytraChestplateData = chestItem.getOrCreateNbt();
            NbtCompound elytraData = elytraChestplateData.getCompound("elytraData");

            // - Set the elytrachestplate data to the chestplate.
            ItemStack endbriteChestplate = new ItemStack(Comet.ENDBRITE_CHESTPLATE);
            endbriteChestplate.setNbt(elytraChestplateData);

            livingEntity.equipStack(EquipmentSlot.CHEST, endbriteChestplate);

            // - If the entity is a player, give back the elytra.
            if (livingEntity instanceof PlayerEntity player){
                ItemStack elytraItem = new ItemStack(Items.ELYTRA);
                elytraItem.setNbt(elytraData);
                if (!player.getInventory().insertStack(elytraItem)) {
                    player.dropItem(elytraItem, false);
                }
            }

            // - If the entity is not a player, drop the elytra on the ground.
            if (!(livingEntity instanceof PlayerEntity)){
                ItemStack elytraItem = new ItemStack(Items.ELYTRA);
                elytraItem.setNbt(elytraData);
                world.spawnEntity(new ItemEntity(world, livingEntity.getPos().getX(), livingEntity.getPos().getY(), livingEntity.getPos().getZ(), elytraItem));
            }
        }
    }
}