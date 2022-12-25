package com.github.Soulphur0.dimensionalAlloys.item;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;

public class EndbriteElytraChestplateItem extends ArmorItem implements Wearable {

    public EndbriteElytraChestplateItem(ArmorMaterial material, EquipmentSlot slot, Settings settings) {
        super(material, slot, settings);
    }

    public static boolean isUsable(ItemStack stack) {
        return stack.getDamage() < stack.getMaxDamage() - 1;
    }
}
