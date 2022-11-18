package com.github.Soulphur0;

import com.github.Soulphur0.dimensionalAlloys.armorMaterial.EndbriteArmorMaterial;
import com.github.Soulphur0.dimensionalAlloys.item.MirrorShieldItem;
import com.github.Soulphur0.registries.CometBlocks;
import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Comet implements ModInitializer {


	// Items
	public static final Item ENDBRITE_SHARD = new Item(new Item.Settings().group(ItemGroup.MISC));
	public static final Item ENDBRITE_FIBER = new Item(new Item.Settings().group(ItemGroup.MISC));
	public static final Item ENDBRITE_INGOT = new Item(new Item.Settings().group(ItemGroup.MISC));

	public static final Item MIRROR_SHIELD = new MirrorShieldItem(new Item.Settings().maxDamage(504).group(ItemGroup.COMBAT));

	// Armor
	public static final ArmorMaterial ENDBRITE_ARMOR_MATERIAL = new EndbriteArmorMaterial();
	public static final Item ENDBRITE_HELMET = new ArmorItem(ENDBRITE_ARMOR_MATERIAL, EquipmentSlot.HEAD, new Item.Settings().group(ItemGroup.COMBAT));
	public static final Item ENDBRITE_CHESTPLATE = new ArmorItem(ENDBRITE_ARMOR_MATERIAL, EquipmentSlot.CHEST, new Item.Settings().group(ItemGroup.COMBAT));
	public static final Item ENDBRITE_LEGGINGS = new ArmorItem(ENDBRITE_ARMOR_MATERIAL, EquipmentSlot.LEGS, new Item.Settings().group(ItemGroup.COMBAT));
	public static final Item ENDBRITE_BOOTS = new ArmorItem(ENDBRITE_ARMOR_MATERIAL, EquipmentSlot.FEET, new Item.Settings().group(ItemGroup.COMBAT));

	@Override
	public void onInitialize() {
		CometBlocks.register();

		// Items
		Registry.register(Registry.ITEM, new Identifier("comet", "endbrite_shard"), ENDBRITE_SHARD);
		Registry.register(Registry.ITEM, new Identifier("comet", "endbrite_fiber"), ENDBRITE_FIBER);
		Registry.register(Registry.ITEM, new Identifier("comet", "endbrite_ingot"), ENDBRITE_INGOT);

		Registry.register(Registry.ITEM, new Identifier("comet", "mirror_shield"), MIRROR_SHIELD);

		// Armor
		Registry.register(Registry.ITEM, new Identifier("comet", "endbrite_helmet"), ENDBRITE_HELMET);
		Registry.register(Registry.ITEM, new Identifier("comet", "endbrite_chestplate"), ENDBRITE_CHESTPLATE);
		Registry.register(Registry.ITEM, new Identifier("comet", "endbrite_leggings"), ENDBRITE_LEGGINGS);
		Registry.register(Registry.ITEM, new Identifier("comet", "endbrite_boots"), ENDBRITE_BOOTS);
	}
}
