package com.github.Soulphur0;

import com.github.Soulphur0.dimensionalAlloys.client.render.armorMaterial.EndbriteArmorMaterial;
import com.github.Soulphur0.dimensionalAlloys.entity.effect.CrystallizedStatusEffect;
import com.github.Soulphur0.dimensionalAlloys.item.MirrorShieldItem;
import com.github.Soulphur0.dimensionalAlloys.recipe.CreatureStatueRecipe;
import com.github.Soulphur0.registries.CometBlocks;
import net.fabricmc.api.ModInitializer;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeveledCauldronBlock;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.client.render.CameraSubmersionType;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.*;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.recipe.BrewingRecipeRegistry;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialRecipeSerializer;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.Map;

public class Comet implements ModInitializer {

	// Items
	public static final Item ENDBRITE_SHARD = new Item(new Item.Settings().group(ItemGroup.MISC));
	public static final Item ENDBRITE_FIBER = new Item(new Item.Settings().group(ItemGroup.MISC));
	public static final Item ENDBRITE_INGOT = new Item(new Item.Settings().group(ItemGroup.MISC));
	public static final Item CONCENTRATED_END_MEDIUM_BOTTLE = new Item(new Item.Settings().group(ItemGroup.MISC));

	public static final Item MIRROR_SHIELD = new MirrorShieldItem(new Item.Settings().maxDamage(504).group(ItemGroup.COMBAT));

	// Armor
	public static final ArmorMaterial ENDBRITE_ARMOR_MATERIAL = new EndbriteArmorMaterial();
	public static final Item ENDBRITE_HELMET = new ArmorItem(ENDBRITE_ARMOR_MATERIAL, EquipmentSlot.HEAD, new Item.Settings().group(ItemGroup.COMBAT));
	public static final Item ENDBRITE_CHESTPLATE = new ArmorItem(ENDBRITE_ARMOR_MATERIAL, EquipmentSlot.CHEST, new Item.Settings().group(ItemGroup.COMBAT));
	public static final Item ENDBRITE_LEGGINGS = new ArmorItem(ENDBRITE_ARMOR_MATERIAL, EquipmentSlot.LEGS, new Item.Settings().group(ItemGroup.COMBAT));
	public static final Item ENDBRITE_BOOTS = new ArmorItem(ENDBRITE_ARMOR_MATERIAL, EquipmentSlot.FEET, new Item.Settings().group(ItemGroup.COMBAT));

	// Sounds
	public static final SoundEvent CRYSTALLIZATION_GROWS = new SoundEvent(new Identifier("comet", "crystallization_grows"));
	public static final SoundEvent CRYSTALLIZATION_BREAKS = new SoundEvent(new Identifier("comet", "crystallization_breaks"));

	// Crafting recipes
	public static final RecipeSerializer<CreatureStatueRecipe> CREATURE_STATUE = RecipeSerializer.register("crafting_special_creaturestatue", new SpecialRecipeSerializer<CreatureStatueRecipe>(CreatureStatueRecipe::new));

	// Status effects
	public static final StatusEffect CRYSTALLIZED = new CrystallizedStatusEffect();
	public static final Potion CRYSTALLIZATION = new Potion(new StatusEffectInstance(CRYSTALLIZED, 160));
	public static final Potion LONG_CRYSTALLIZATION = new Potion(new StatusEffectInstance(CRYSTALLIZED, 320));

	// Sprites
	public static final SpriteIdentifier SOUL_FIRE_0 = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, new Identifier("block/soul_fire_0"));
	public static final SpriteIdentifier SOUL_FIRE_1 = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, new Identifier("block/soul_fire_1"));
	public static final SpriteIdentifier END_FIRE_0 = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, new Identifier("comet","block/end_fire_0"));
	public static final SpriteIdentifier END_FIRE_1 = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, new Identifier("comet","block/end_fire_1"));

	// Damage sources
	public static final DamageSource END_MEDIUM_DROWN = new DamageSource("end_medidum_drown").setBypassesArmor();
	public static final DamageSource END_MEDIUM_DROWN_RARE = new DamageSource("end_medidum_drown_rare").setBypassesArmor();

	// Cauldron behaviour
	public static final Map<Item, CauldronBehavior> END_MEDIUM_CAULDRON_BEHAVIOR = CauldronBehavior.createMap();
	public static final CauldronBehavior FILL_WITH_END_MEDIUM = (state, world, pos, player, hand, stack) -> CauldronBehavior.fillCauldron(world, pos, player, hand, stack, (BlockState) CometBlocks.END_MEDIUM_CAULDRON.getDefaultState().with(LeveledCauldronBlock.LEVEL, 3), SoundEvents.ITEM_BUCKET_EMPTY);

	@Override
	public void onInitialize() {
		CometBlocks.register();

		// Items
		Registry.register(Registry.ITEM, new Identifier("comet", "endbrite_shard"), ENDBRITE_SHARD);
		Registry.register(Registry.ITEM, new Identifier("comet", "endbrite_fiber"), ENDBRITE_FIBER);
		Registry.register(Registry.ITEM, new Identifier("comet", "endbrite_ingot"), ENDBRITE_INGOT);
		Registry.register(Registry.ITEM, new Identifier("comet", "concentrated_end_medium_bottle"), CONCENTRATED_END_MEDIUM_BOTTLE);

		Registry.register(Registry.ITEM, new Identifier("comet", "mirror_shield"), MIRROR_SHIELD);

		// Armor
		Registry.register(Registry.ITEM, new Identifier("comet", "endbrite_helmet"), ENDBRITE_HELMET);
		Registry.register(Registry.ITEM, new Identifier("comet", "endbrite_chestplate"), ENDBRITE_CHESTPLATE);
		Registry.register(Registry.ITEM, new Identifier("comet", "endbrite_leggings"), ENDBRITE_LEGGINGS);
		Registry.register(Registry.ITEM, new Identifier("comet", "endbrite_boots"), ENDBRITE_BOOTS);

		// Sounds
		Registry.register(Registry.SOUND_EVENT,new Identifier("comet", "crystallization_grows"), CRYSTALLIZATION_GROWS);
		Registry.register(Registry.SOUND_EVENT,new Identifier("comet", "crystallization_breaks"), CRYSTALLIZATION_BREAKS);

		// Status effects
		Registry.register(Registry.STATUS_EFFECT, new Identifier("comet", "crystallized"), CRYSTALLIZED);
		Registry.register(Registry.POTION, new Identifier("comet","crystallization"),CRYSTALLIZATION);
		Registry.register(Registry.POTION, new Identifier("comet","long_crystallization"),LONG_CRYSTALLIZATION);
		BrewingRecipeRegistry.registerPotionRecipe(Potions.AWKWARD, CONCENTRATED_END_MEDIUM_BOTTLE, CRYSTALLIZATION);
		BrewingRecipeRegistry.registerPotionRecipe(CRYSTALLIZATION, Items.REDSTONE, LONG_CRYSTALLIZATION);

		END_MEDIUM_CAULDRON_BEHAVIOR.put(Items.BUCKET, (state2, world, pos, player, hand, stack) -> CauldronBehavior.emptyCauldron(state2, world, pos, player, hand, stack, new ItemStack(CometBlocks.CONCENTRATED_END_MEDIUM_BUCKET), state -> state.get(LeveledCauldronBlock.LEVEL) == 3, SoundEvents.ITEM_BUCKET_FILL));
		CauldronBehavior.registerBucketBehavior(END_MEDIUM_CAULDRON_BEHAVIOR);
	}
}
