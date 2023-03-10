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
import net.minecraft.block.Blocks;
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
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.recipe.BrewingRecipeRegistry;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialRecipeSerializer;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.event.GameEvent;

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
	public static final SoundEvent CRYSTALLIZATION_GROWS = new SoundEvent(new Identifier("comet", "mob.crystallization.grows"));
	public static final SoundEvent CRYSTALLIZATION_BREAKS = new SoundEvent(new Identifier("comet", "mob.crystallization.breaks"));

	public static final SoundEvent CONCENTRATED_END_MEDIUM_BUCKET_FILL = new SoundEvent(new Identifier("comet", "item.concentrated_end_medium_bucket.fill"));
	public static final SoundEvent CONCENTRATED_END_MEDIUM_BUCKET_EMPTY = new SoundEvent(new Identifier("comet", "item.concentrated_end_medium_bucket.empty"));

	public static final SoundEvent CONCENTRATED_END_MEDIUM_BOTTLE_FILL = new SoundEvent(new Identifier("comet", "item.concentrated_end_medium_bottle.fill"));
	public static final SoundEvent CONCENTRATED_END_MEDIUM_BOTTLE_EMPTY = new SoundEvent(new Identifier("comet", "item.concentrated_end_medium_bottle.empty"));

	public static final SoundEvent CREATURE_STATUE_SCRAP = new SoundEvent(new Identifier("comet", "block.creature_statue_scrap"));

	public static final SoundEvent THORNED_ROOTS_BREAK = new SoundEvent(new Identifier("comet", "block.thorned_roots.break"));

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
	public static final CauldronBehavior FILL_WITH_END_MEDIUM = (state, world, pos, player, hand, stack) -> CauldronBehavior.fillCauldron(world, pos, player, hand, stack, (BlockState) CometBlocks.END_MEDIUM_CAULDRON.getDefaultState().with(LeveledCauldronBlock.LEVEL, 3), Comet.CONCENTRATED_END_MEDIUM_BUCKET_EMPTY);
	public static final CauldronBehavior EMPTY_WITH_BUCKET = (state, world, pos, player, hand, stack) -> CauldronBehavior.emptyCauldron(state, world, pos, player, hand, stack, new ItemStack(CometBlocks.CONCENTRATED_END_MEDIUM_BUCKET), predicateState -> predicateState.getBlock() instanceof LeveledCauldronBlock && predicateState.get(LeveledCauldronBlock.LEVEL) == 3, Comet.CONCENTRATED_END_MEDIUM_BUCKET_FILL);

	public static final CauldronBehavior FILL_WITH_END_MEDIUM_BOTTLE = (state2, world, pos, player, hand, stack) -> {
		if (!world.isClient()){
			Item item = stack.getItem();
			player.setStackInHand(hand, ItemUsage.exchangeStack(stack, player, new ItemStack(Items.GLASS_BOTTLE)));
			player.incrementStat(Stats.USE_CAULDRON);
			player.incrementStat(Stats.USED.getOrCreateStat(item));
			if (world.getBlockState(pos).isOf(CometBlocks.END_MEDIUM_CAULDRON) && world.getBlockState(pos).get(LeveledCauldronBlock.LEVEL) < 3)
				world.setBlockState(pos, CometBlocks.END_MEDIUM_CAULDRON.getDefaultState().with(LeveledCauldronBlock.LEVEL,world.getBlockState(pos).get(LeveledCauldronBlock.LEVEL) + 1));
			else
				world.setBlockState(pos, CometBlocks.END_MEDIUM_CAULDRON.getDefaultState().with(LeveledCauldronBlock.LEVEL,1));
			world.playSound(null, pos, Comet.CONCENTRATED_END_MEDIUM_BOTTLE_EMPTY, SoundCategory.BLOCKS, 1.0f, 1.0f);
			world.emitGameEvent(null, GameEvent.FLUID_PICKUP, pos);
		}
		return ActionResult.success(world.isClient);
	};
	public static final CauldronBehavior EMPTY_WITH_BOTTLE = (state, world, pos, player, hand, stack) -> {
		if (!world.isClient() && state.getBlock() instanceof LeveledCauldronBlock){
			Item item = stack.getItem();
			player.setStackInHand(hand, ItemUsage.exchangeStack(stack, player, new ItemStack(Comet.CONCENTRATED_END_MEDIUM_BOTTLE)));
			player.incrementStat(Stats.USE_CAULDRON);
			player.incrementStat(Stats.USED.getOrCreateStat(item));
			LeveledCauldronBlock.decrementFluidLevel(state, world, pos);
			world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.BLOCKS, 1.0f, 1.0f);
			world.emitGameEvent(null, GameEvent.FLUID_PICKUP, pos);
		}
		return ActionResult.success(world.isClient);
	};

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
		Registry.register(Registry.SOUND_EVENT,new Identifier("comet", "mob.crystallization.grows"), CRYSTALLIZATION_GROWS);
		Registry.register(Registry.SOUND_EVENT,new Identifier("comet", "mob.crystallization.breaks"), CRYSTALLIZATION_BREAKS);

		Registry.register(Registry.SOUND_EVENT,new Identifier("comet", "item.concentrated_end_medium_bucket.fill"), CONCENTRATED_END_MEDIUM_BUCKET_FILL);
		Registry.register(Registry.SOUND_EVENT,new Identifier("comet", "item.concentrated_end_medium_bucket.empty"), CONCENTRATED_END_MEDIUM_BUCKET_EMPTY);

		Registry.register(Registry.SOUND_EVENT,new Identifier("comet", "item.concentrated_end_medium_bottle.fill"), CONCENTRATED_END_MEDIUM_BOTTLE_FILL);
		Registry.register(Registry.SOUND_EVENT,new Identifier("comet", "item.concentrated_end_medium_bottle.empty"), CONCENTRATED_END_MEDIUM_BOTTLE_EMPTY);

		Registry.register(Registry.SOUND_EVENT,new Identifier("comet", "block.creature_statue_scrap"), CREATURE_STATUE_SCRAP);

		Registry.register(Registry.SOUND_EVENT,new Identifier("comet", "block.thorned_roots.break"), THORNED_ROOTS_BREAK);

		// Status effects
		Registry.register(Registry.STATUS_EFFECT, new Identifier("comet", "crystallized"), CRYSTALLIZED);
		Registry.register(Registry.POTION, new Identifier("comet","crystallization"),CRYSTALLIZATION);
		Registry.register(Registry.POTION, new Identifier("comet","long_crystallization"),LONG_CRYSTALLIZATION);
		BrewingRecipeRegistry.registerPotionRecipe(Potions.AWKWARD, CONCENTRATED_END_MEDIUM_BOTTLE, CRYSTALLIZATION);
		BrewingRecipeRegistry.registerPotionRecipe(CRYSTALLIZATION, Items.REDSTONE, LONG_CRYSTALLIZATION);

		// Portal shield model predicate provider
		FabricModelPredicateProviderRegistry.register(PORTAL_SHIELD, new Identifier("blocking"), (stack, world, entity, seed) -> entity != null && entity.isUsingItem() && entity.getActiveItem() == stack ? 1.0F : 0.0F);
	}
}
