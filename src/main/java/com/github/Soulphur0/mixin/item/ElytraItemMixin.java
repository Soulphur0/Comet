package com.github.Soulphur0.mixin.item;

import com.github.Soulphur0.Comet;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ElytraItem.class)
public class ElytraItemMixin {

    // ? If the player is wearing a full endbrite armor, make the player able to place an elytra on their chestplate via item right-click.
    @Inject(method = "use", at = @At("HEAD"))
    private void comet_equipElytraChestplate(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir){
        boolean playerHasEndbriteArmor = user.getEquippedStack(EquipmentSlot.HEAD).isOf(Comet.ENDBRITE_HELMET) && user.getEquippedStack(EquipmentSlot.CHEST).isOf(Comet.ENDBRITE_CHESTPLATE) && user.getEquippedStack(EquipmentSlot.LEGS).isOf(Comet.ENDBRITE_LEGGINGS) && user.getEquippedStack(EquipmentSlot.FEET).isOf(Comet.ENDBRITE_BOOTS);
        ItemStack elytraItem = user.getStackInHand(hand);

        if (playerHasEndbriteArmor){
            // + Store the elytra data in the chestplate's NBT.
            NbtCompound elytraData = elytraItem.getOrCreateNbt();
            NbtCompound chestplateData = user.getEquippedStack(EquipmentSlot.CHEST).getOrCreateNbt();
            chestplateData.put("elytraData", elytraData);

            // + Set the combined item nbt to the chestplate's nbt.
            ItemStack elytraChestplate = new ItemStack(Comet.ENDBRITE_ELYTRA_CHESTPLATE);
            elytraChestplate.setNbt(chestplateData);

            user.equipStack(EquipmentSlot.CHEST, elytraChestplate);
            
            if (!user.isCreative())
                elytraItem.setCount(0);
        }
    }
}
