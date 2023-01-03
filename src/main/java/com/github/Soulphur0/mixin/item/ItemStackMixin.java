package com.github.Soulphur0.mixin.item;

import com.github.Soulphur0.Comet;
import com.llamalad7.mixinextras.injector.WrapWithCondition;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Consumer;

@Mixin(ItemStack.class)
public class ItemStackMixin {

    // $ Cancels item usage when an entity is crystallized.
    // _ Simple item use.
    @Inject(method="use", at = @At("HEAD"), cancellable = true)
    private void comet_cancelItemUseIfPlayerIsCrystallized(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir){
        if (user.isCrystallized())
            cir.setReturnValue(Items.AIR.use(world, user, hand));
    }

    // _ Use on block (shovels, lighters, spawn eggs)
    @Inject(method="useOnBlock", at = @At("HEAD"), cancellable = true)
    private void comet_cancelItemUseOnBlockIfPlayerIsCrystallized(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir){
        if (context.getPlayer() != null && context.getPlayer().isCrystallized())
            cir.setReturnValue(ActionResult.FAIL);
    }

    // _ Use on entity (sheep dye)
    @Inject(method = "useOnEntity", at = @At("HEAD"), cancellable = true)
    private void comet_cancelItemUseOnEntityIfPlayerIsCrystallized(PlayerEntity user, LivingEntity entity, Hand hand, CallbackInfoReturnable<ActionResult> cir){
        if (user.isCrystallized())
            cir.setReturnValue(ActionResult.FAIL);
    }

    // $ Elytra chestplate returns the holding elytra item when broken.
    // _ Skip item break check when the chestplate breaks.
    @WrapWithCondition(method = "damage(ILnet/minecraft/entity/LivingEntity;Ljava/util/function/Consumer;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;decrement(I)V"))
    private boolean comet_returnElytraFromChestplate(ItemStack instance, int quantity){
        return !instance.isOf(Comet.ENDBRITE_ELYTRA_CHESTPLATE);
    }

    // _ Replace the chestplate with the elytra it was holding.
    @Inject(method = "damage(ILnet/minecraft/entity/LivingEntity;Ljava/util/function/Consumer;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;getItem()Lnet/minecraft/item/Item;"))
    private <T extends LivingEntity> void comet_replaceBrokenElytraChestplate(int amount, T entity, Consumer<T> breakCallback, CallbackInfo ci){
        ItemStack stack = ((ItemStack)(Object)this);
        if (stack.isOf(Comet.ENDBRITE_ELYTRA_CHESTPLATE) && stack.hasNbt()){
            NbtCompound elytraData = stack.getNbt().getCompound("elytraData");
            ItemStack elytraItem = new ItemStack(Items.ELYTRA);
            elytraItem.setNbt(elytraData);

            if (entity.getEquippedStack(EquipmentSlot.CHEST).isOf(Comet.ENDBRITE_ELYTRA_CHESTPLATE) || entity.getEquippedStack(EquipmentSlot.CHEST).isOf(Items.AIR))
                entity.equipStack(EquipmentSlot.CHEST, elytraItem);
            else if (entity instanceof PlayerEntity player && !player.getInventory().insertStack(elytraItem))
                player.dropItem(elytraItem, false);

        }
    }
}
