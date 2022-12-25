package com.github.Soulphur0.mixin.item;

import com.github.Soulphur0.Comet;
import com.google.common.collect.ImmutableMultimap;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ArmorItem.class)
public abstract class ArmorItemMixin {

    @Shadow public abstract ArmorMaterial getMaterial();

    // ? Add attribute modifiers to armor items depending on comet armor materials.
    @ModifyVariable(method = "<init>", at = @At(value="STORE"), ordinal = 0)
    private ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> comet_getArmorItemAttributeModifiersBuilder(ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder){
        if (getMaterial() == Comet.ENDBRITE_ARMOR_MATERIAL)
            builder.put(EntityAttributes.GENERIC_MOVEMENT_SPEED,new EntityAttributeModifier("Armor movement speed", 0.05D, EntityAttributeModifier.Operation.MULTIPLY_BASE));

        return builder;
    }
}
