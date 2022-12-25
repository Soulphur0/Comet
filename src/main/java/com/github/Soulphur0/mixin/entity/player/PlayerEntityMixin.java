package com.github.Soulphur0.mixin.entity.player;

import com.github.Soulphur0.Comet;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameMode;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {

    // $ Injected ------------------------------------------------------------------------------------------------------

    // _ Crystallization injects.
    // ? Make player unable to sneak during crystallization.
    @Inject(method ="updatePose", at = @At("HEAD"),cancellable = true)
    private void cancelPoseUpdatesWhenCrystallized(CallbackInfo ci){
        if (((PlayerEntity)(Object)this).isCrystallized())
            ci.cancel();
    }

    // ? Make player unable to attack during crystallization.
    @Inject(method="attack", at = @At("HEAD"), cancellable = true)
    private void cancelAttack(Entity target, CallbackInfo ci){
        if (((PlayerEntity)(Object)this).isCrystallized())
            ci.cancel();
    }

    // ? Make player unable to break blocks during crystallization.
    @Inject(method = "isBlockBreakingRestricted", at = @At("HEAD"), cancellable = true)
    private void restrictBlockDestructionWhenCrystallized(World world, BlockPos pos, GameMode gameMode, CallbackInfoReturnable<Boolean> cir){
        if (((PlayerEntity)(Object)this).isCrystallized()){
            cir.setReturnValue(true);
        }
    }

    // ? Make player unable to use consumables during crystallization.
    @Inject(method = "canConsume", at = @At("HEAD"), cancellable = true)
    private void restrictItemConsumptionDuringCrystallization(boolean ignoreHunger, CallbackInfoReturnable<Boolean> cir){
        if (((PlayerEntity)(Object)this).isCrystallized()){
            cir.setReturnValue(false);
        }
    }

    // _ Elytra item injects.
    // ? When checking for an equipped elytra, return true if a flight-compatible item is equipped.
    @WrapOperation(method = "checkFallFlying", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z"))
    private boolean comet_checkForElytraItem(ItemStack instance, Item item, Operation<Boolean> original){
        if (instance.isOf(Comet.ENDBRITE_ELYTRA_CHESTPLATE)){
            return true;
        } else {
            return original.call(instance, item);
        }
    }
}
