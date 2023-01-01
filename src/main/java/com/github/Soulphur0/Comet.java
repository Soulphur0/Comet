package com.github.Soulphur0;

import com.github.Soulphur0.dimensionalAlloys.item.armorMaterial.EndbriteArmorMaterial;
import com.github.Soulphur0.dimensionalAlloys.entity.effect.CrystallizedStatusEffect;
import com.github.Soulphur0.dimensionalAlloys.item.EndbriteElytraChestplateItem;
import com.github.Soulphur0.dimensionalAlloys.item.PortalShieldItem;
import com.github.Soulphur0.dimensionalAlloys.recipe.CreatureStatueRecipe;
import com.github.Soulphur0.dimensionalAlloys.recipe.EndbriteElytraChestplateItemRecipe;
import com.github.Soulphur0.registries.CometBlocks;
import com.github.Soulphur0.registries.CometEndFeatures;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeveledCauldronBlock;
import net.minecraft.block.cauldron.CauldronBehavior;
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
	public static final Item CONCENTRATED_END_MEDIUM_BOTTLE = new Item(new Item.Settings().group(ItemGroup.MISC).maxCount(16));

	public static final Item PORTAL_SHIELD = new PortalShieldItem(new Item.Settings().maxDamage(504).group(ItemGroup.COMBAT));

	// Armor
	public static final ArmorMaterial ENDBRITE_ARMOR_MATERIAL = new EndbriteArmorMaterial();
	public static final Item ENDBRITE_HELMET = new ArmorItem(ENDBRITE_ARMOR_MATERIAL, EquipmentSlot.HEAD, new Item.Settings().group(ItemGroup.COMBAT));
	public static final Item ENDBRITE_CHESTPLATE = new ArmorItem(ENDBRITE_ARMOR_MATERIAL, EquipmentSlot.CHEST, new Item.Settings().group(ItemGroup.COMBAT));
	public static final Item ENDBRITE_ELYTRA_CHESTPLATE = new EndbriteElytraChestplateItem(ENDBRITE_ARMOR_MATERIAL, EquipmentSlot.CHEST, new Item.Settings().group(ItemGroup.COMBAT));
	public static final Item ENDBRITE_LEGGINGS = new ArmorItem(ENDBRITE_ARMOR_MATERIAL, EquipmentSlot.LEGS, new Item.Settings().group(ItemGroup.COMBAT));
	public static final Item ENDBRITE_BOOTS = new ArmorItem(ENDBRITE_ARMOR_MATERIAL, EquipmentSlot.FEET, new Item.Settings().group(ItemGroup.COMBAT));

	// Sounds
	public static final SoundEvent CRYSTALLIZATION_GROWS = new SoundEvent(new Identifier("comet", "crystallization_grows"));
	public static final SoundEvent CRYSTALLIZATION_BREAKS = new SoundEvent(new Identifier("comet", "crystallization_breaks"));

	public static final SoundEvent CONCENTRATED_END_MEDIUM_BUCKET_FILL_1 = new SoundEvent(new Identifier("comet", "concentrated_end_medium_bucket_fill_1"));
	public static final SoundEvent CONCENTRATED_END_MEDIUM_BUCKET_FILL_2 = new SoundEvent(new Identifier("comet", "concentrated_end_medium_bucket_fill_2"));
	public static final SoundEvent CONCENTRATED_END_MEDIUM_BUCKET_EMPTY_1 = new SoundEvent(new Identifier("comet", "concentrated_end_medium_bucket_empty_1"));
	public static final SoundEvent CONCENTRATED_END_MEDIUM_BUCKET_EMPTY_2 = new SoundEvent(new Identifier("comet", "concentrated_end_medium_bucket_empty_2"));
	public static final SoundEvent CONCENTRATED_END_MEDIUM_BUCKET = new SoundEvent(new Identifier("comet", "concentrated_end_medium_bucket"));

	public static final SoundEvent CONCENTRATED_END_MEDIUM_BOTTLE_FILL_1 = new SoundEvent(new Identifier("comet", "concentrated_end_medium_bottle_fill_1"));
	public static final SoundEvent CONCENTRATED_END_MEDIUM_BOTTLE_FILL_2 = new SoundEvent(new Identifier("comet", "concentrated_end_medium_bottle_fill_2"));
	public static final SoundEvent CONCENTRATED_END_MEDIUM_BOTTLE_EMPTY_1 = new SoundEvent(new Identifier("comet", "concentrated_end_medium_bottle_empty_1"));
	public static final SoundEvent CONCENTRATED_END_MEDIUM_BOTTLE_EMPTY_2 = new SoundEvent(new Identifier("comet", "concentrated_end_medium_bottle_empty_2"));

	public static final SoundEvent CREATURE_STATUE_SCRAP_1 = new SoundEvent(new Identifier("comet", "creature_statue_scrap_1"));
	public static final SoundEvent CREATURE_STATUE_SCRAP_2 = new SoundEvent(new Identifier("comet", "creature_statue_scrap_2"));
	public static final SoundEvent CREATURE_STATUE_SCRAP_3 = new SoundEvent(new Identifier("comet", "creature_statue_scrap_3"));

	public static final SoundEvent THORNED_ROOTS_BREAK_1 = new SoundEvent(new Identifier("comet", "thorned_roots_break_1"));
	public static final SoundEvent THORNED_ROOTS_BREAK_2 = new SoundEvent(new Identifier("comet", "thorned_roots_break_2"));
	public static final SoundEvent THORNED_ROOTS_BREAK_3 = new SoundEvent(new Identifier("comet", "thorned_roots_break_3"));
	public static final SoundEvent THORNED_ROOTS_BREAK_4 = new SoundEvent(new Identifier("comet", "thorned_roots_break_4"));

	// Crafting recipes
	public static final RecipeSerializer<CreatureStatueRecipe> CREATURE_STATUE = RecipeSerializer.register("crafting_special_creaturestatue", new SpecialRecipeSerializer<CreatureStatueRecipe>(CreatureStatueRecipe::new));
	public static final RecipeSerializer<EndbriteElytraChestplateItemRecipe> ENDBRITE_ELYTRA_CHESTPLATE_RECIPE = RecipeSerializer.register("crafting_special_endbriteelytrachestplate", new SpecialRecipeSerializer<EndbriteElytraChestplateItemRecipe>(EndbriteElytraChestplateItemRecipe::new));

	// Status effects
	public static final StatusEffect CRYSTALLIZED = new CrystallizedStatusEffect();
	public static final Potion CRYSTALLIZATION = new Potion(new StatusEffectInstance(CRYSTALLIZED, 160));
	public static final Potion LONG_CRYSTALLIZATION = new Potion(new StatusEffectInstance(CRYSTALLIZED, 320));

	// Sprites
	public static final SpriteIdentifier SOUL_FIRE_0 = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, new Identifier("block/soul_fire_0"));
	public static final SpriteIdentifier SOUL_FIRE_1 = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, new Identifier("block/soul_fire_1"));
	public static final SpriteIdentifier END_FIRE_0 = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, new Identifier("comet","block/end_fire_0"));
	public static final SpriteIdentifier END_FIRE_1 = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, new Identifier("comet","block/end_fire_1"));

	public static final SpriteIdentifier PORTAL_SHIELD_SPRITE = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, new Identifier("comet", "entity/portal_shield"));

	// Damage sources
	public static final DamageSource END_MEDIUM_DROWN = new DamageSource("end_medidum_drown").setBypassesArmor();
	public static final DamageSource END_MEDIUM_DROWN_RARE = new DamageSource("end_medidum_drown_rare").setBypassesArmor();

	// Cauldron behaviour
	public static final Map<Item, CauldronBehavior> END_MEDIUM_CAULDRON_BEHAVIOR = CauldronBehavior.createMap();
	public static final CauldronBehavior FILL_WITH_END_MEDIUM = (state, world, pos, player, hand, stack) -> CauldronBehavior.fillCauldron(world, pos, player, hand, stack, (BlockState) CometBlocks.END_MEDIUM_CAULDRON.getDefaultState().with(LeveledCauldronBlock.LEVEL, 3), SoundEvents.ITEM_BUCKET_EMPTY);

	@Override
	public void onInitialize() {
		// _ Register all blocks.
		CometBlocks.register();

		// _ Register all terrain features.
		CometEndFeatures.register();

		// Items
		Registry.register(Registry.ITEM, new Identifier("comet", "endbrite_shard"), ENDBRITE_SHARD);
		Registry.register(Registry.ITEM, new Identifier("comet", "endbrite_fiber"), ENDBRITE_FIBER);
		Registry.register(Registry.ITEM, new Identifier("comet", "endbrite_ingot"), ENDBRITE_INGOT);
		Registry.register(Registry.ITEM, new Identifier("comet", "concentrated_end_medium_bottle"), CONCENTRATED_END_MEDIUM_BOTTLE);

		Registry.register(Registry.ITEM, new Identifier("comet", "portal_shield"), PORTAL_SHIELD);

		// Armor
		Registry.register(Registry.ITEM, new Identifier("comet", "endbrite_helmet"), ENDBRITE_HELMET);
		Registry.register(Registry.ITEM, new Identifier("comet", "endbrite_chestplate"), ENDBRITE_CHESTPLATE);
		Registry.register(Registry.ITEM, new Identifier("comet", "endbrite_elytra_chestplate"), ENDBRITE_ELYTRA_CHESTPLATE);
		Registry.register(Registry.ITEM, new Identifier("comet", "endbrite_leggings"), ENDBRITE_LEGGINGS);
		Registry.register(Registry.ITEM, new Identifier("comet", "endbrite_boots"), ENDBRITE_BOOTS);

		// Sounds
		Registry.register(Registry.SOUND_EVENT,new Identifier("comet", "crystallization_grows"), CRYSTALLIZATION_GROWS);
		Registry.register(Registry.SOUND_EVENT,new Identifier("comet", "crystallization_breaks"), CRYSTALLIZATION_BREAKS);

		Registry.register(Registry.SOUND_EVENT,new Identifier("comet", "concentrated_end_medium_bucket_fill_1"), CONCENTRATED_END_MEDIUM_BUCKET_FILL_1);
		Registry.register(Registry.SOUND_EVENT,new Identifier("comet", "concentrated_end_medium_bucket_fill_2"), CONCENTRATED_END_MEDIUM_BUCKET_FILL_2);
		Registry.register(Registry.SOUND_EVENT,new Identifier("comet", "concentrated_end_medium_bucket_empty_1"), CONCENTRATED_END_MEDIUM_BUCKET_EMPTY_1);
		Registry.register(Registry.SOUND_EVENT,new Identifier("comet", "concentrated_end_medium_bucket_empty_2"), CONCENTRATED_END_MEDIUM_BUCKET_EMPTY_2);
		Registry.register(Registry.SOUND_EVENT,new Identifier("comet", "concentrated_end_medium_bucket"), CONCENTRATED_END_MEDIUM_BUCKET);

		Registry.register(Registry.SOUND_EVENT,new Identifier("comet", "concentrated_end_medium_bottle_fill_1"), CONCENTRATED_END_MEDIUM_BOTTLE_FILL_1);
		Registry.register(Registry.SOUND_EVENT,new Identifier("comet", "concentrated_end_medium_bottle_fill_2"), CONCENTRATED_END_MEDIUM_BOTTLE_FILL_2);
		Registry.register(Registry.SOUND_EVENT,new Identifier("comet", "concentrated_end_medium_bottle_empty_1"),CONCENTRATED_END_MEDIUM_BOTTLE_EMPTY_1);
		Registry.register(Registry.SOUND_EVENT,new Identifier("comet", "concentrated_end_medium_bottle_empty_2"), CONCENTRATED_END_MEDIUM_BOTTLE_EMPTY_2);

		Registry.register(Registry.SOUND_EVENT,new Identifier("comet", "creature_statue_scrap_1"), CREATURE_STATUE_SCRAP_1);
		Registry.register(Registry.SOUND_EVENT,new Identifier("comet", "creature_statue_scrap_2"), CREATURE_STATUE_SCRAP_2);
		Registry.register(Registry.SOUND_EVENT,new Identifier("comet", "creature_statue_scrap_3"), CREATURE_STATUE_SCRAP_3);

		Registry.register(Registry.SOUND_EVENT,new Identifier("comet", "thorned_roots_break_1"), THORNED_ROOTS_BREAK_1);
		Registry.register(Registry.SOUND_EVENT,new Identifier("comet", "thorned_roots_break_2"), THORNED_ROOTS_BREAK_2);
		Registry.register(Registry.SOUND_EVENT,new Identifier("comet", "thorned_roots_break_3"), THORNED_ROOTS_BREAK_3);
		Registry.register(Registry.SOUND_EVENT,new Identifier("comet", "thorned_roots_break_4"), THORNED_ROOTS_BREAK_4);

		// Status effects
		Registry.register(Registry.STATUS_EFFECT, new Identifier("comet", "crystallized"), CRYSTALLIZED);
		Registry.register(Registry.POTION, new Identifier("comet","crystallization"),CRYSTALLIZATION);
		Registry.register(Registry.POTION, new Identifier("comet","long_crystallization"),LONG_CRYSTALLIZATION);
		BrewingRecipeRegistry.registerPotionRecipe(Potions.AWKWARD, CONCENTRATED_END_MEDIUM_BOTTLE, CRYSTALLIZATION);
		BrewingRecipeRegistry.registerPotionRecipe(CRYSTALLIZATION, Items.REDSTONE, LONG_CRYSTALLIZATION);

		// Cauldron behaviour
		END_MEDIUM_CAULDRON_BEHAVIOR.put(Items.BUCKET, (state2, world, pos, player, hand, stack) -> CauldronBehavior.emptyCauldron(state2, world, pos, player, hand, stack, new ItemStack(CometBlocks.CONCENTRATED_END_MEDIUM_BUCKET), state -> state.get(LeveledCauldronBlock.LEVEL) == 3, SoundEvents.ITEM_BUCKET_FILL));
		CauldronBehavior.registerBucketBehavior(END_MEDIUM_CAULDRON_BEHAVIOR);

		// Portal shield model predicate provider
		FabricModelPredicateProviderRegistry.register(PORTAL_SHIELD, new Identifier("blocking"), (stack, world, entity, seed) -> entity != null && entity.isUsingItem() && entity.getActiveItem() == stack ? 1.0F : 0.0F);
	}
}
