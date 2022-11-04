package com.github.SoulArts;

import com.github.SoulArts.dimensionalAlloys.armorMaterial.EndbriteArmorMaterial;
import com.github.SoulArts.dimensionalAlloys.block.EndbriteTube;
import com.github.SoulArts.dimensionalAlloys.item.MirrorShieldItem;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.TheEndBiomes;
import net.fabricmc.fabric.api.item.v1.FabricItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.mininglevel.v1.FabricMineableTags;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fabricmc.fabric.impl.biome.TheEndBiomeData;
import net.fabricmc.fabric.impl.client.rendering.EntityModelLayerImpl;
import net.fabricmc.fabric.mixin.biome.TheEndBiomeSourceMixin;
import net.fabricmc.fabric.mixin.client.rendering.EntityModelLayersAccessor;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.TheEndBiomeCreator;
import net.minecraft.world.biome.source.BiomeSource;

public class Comet implements ModInitializer {
	// Blocks
	public static final Block END_IRON_ORE = new Block(FabricBlockSettings
			.of(Material.STONE)
			.hardness(3f)
			.sounds(BlockSoundGroup.STONE));
	public static final BlockItem END_IRON_ORE_BLOCK_ITEM = new BlockItem(END_IRON_ORE, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS));

	public static final Block ENDBRITE_TUBE = new EndbriteTube(FabricBlockSettings
			.of(Material.STONE)
			.hardness(5f)
			.sounds(BlockSoundGroup.ANCIENT_DEBRIS));
	public static final BlockItem ENDBRITE_TUBE_BLOCK_ITEM = new BlockItem(ENDBRITE_TUBE, new Item.Settings().group(ItemGroup.MISC));

	public static final Block CHORUS_HUMUS = new Block(FabricBlockSettings
			.of(Material.STONE)
			.hardness(3f)
			.sounds(BlockSoundGroup.STONE));
	public static final BlockItem CHORUS_HUMUS_BLOCK_ITEM = new BlockItem(CHORUS_HUMUS, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS));

	public static final Block CHORUS_HUMUS2 = new Block(FabricBlockSettings
			.of(Material.STONE)
			.hardness(3f)
			.sounds(BlockSoundGroup.STONE));
	public static final BlockItem CHORUS_HUMUS_BLOCK_ITEM2 = new BlockItem(CHORUS_HUMUS2, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS));

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
		// Blocks
		Registry.register(Registry.BLOCK, new Identifier("comet","end_iron_ore"), END_IRON_ORE);
		Registry.register(Registry.ITEM, new Identifier("comet", "end_iron_ore"), END_IRON_ORE_BLOCK_ITEM);

		Registry.register(Registry.BLOCK, new Identifier("comet","endbrite_tube"), ENDBRITE_TUBE);
		Registry.register(Registry.ITEM, new Identifier("comet","endbrite_tube"), ENDBRITE_TUBE_BLOCK_ITEM);

		Registry.register(Registry.BLOCK, new Identifier("comet","chorus_humus"), CHORUS_HUMUS);
		Registry.register(Registry.ITEM, new Identifier("comet","chorus_humus"), CHORUS_HUMUS_BLOCK_ITEM);

		Registry.register(Registry.BLOCK, new Identifier("comet","chorus_humus2"), CHORUS_HUMUS2);
		Registry.register(Registry.ITEM, new Identifier("comet","chorus_humus2"), CHORUS_HUMUS_BLOCK_ITEM2);

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
