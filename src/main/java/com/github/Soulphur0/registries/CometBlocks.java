package com.github.Soulphur0.registries;

import com.github.Soulphur0.dimensionalAlloys.block.*;
import com.github.Soulphur0.dimensionalAlloys.block.entity.CrystallizedCreatureBlockEntity;
import com.github.Soulphur0.dimensionalAlloys.block.entity.EndIronOreBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.BlockView;

public class CometBlocks {

    // $ End iron ore
    public static final Block END_IRON_ORE = new EndIronOre(FabricBlockSettings
            .of(Material.STONE)
            .hardness(3f)
            .sounds(BlockSoundGroup.STONE));
    public static final BlockItem END_IRON_ORE_BLOCK_ITEM = new BlockItem(END_IRON_ORE, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS));

    // * Revealed block
    public static final Block END_IRON_ORE_REVEALED = new Block(FabricBlockSettings
            .of(Material.STONE)
            .hardness(3f)
            .sounds(BlockSoundGroup.STONE));

    // * Block entity
    public static final BlockEntityType<EndIronOreBlockEntity> END_IRON_ORE_BLOCK_ENTITY = Registry.register(
            Registry.BLOCK_ENTITY_TYPE,
            new Identifier("comet", "end_iron_ore"),
            FabricBlockEntityTypeBuilder.create(EndIronOreBlockEntity::new, END_IRON_ORE).build()
    );

    // $ Endbrite tube
    public static final Block ENDBRITE_TUBE = new EndbriteTube(FabricBlockSettings
            .of(Material.STONE)
            .hardness(5f)
            .sounds(BlockSoundGroup.ANCIENT_DEBRIS)
            .ticksRandomly());
    public static final BlockItem ENDBRITE_TUBE_BLOCK_ITEM = new BlockItem(ENDBRITE_TUBE, new Item.Settings().group(ItemGroup.MISC));

    // $ Chorus humus
    public static final Block CHORUS_HUMUS = new Block(FabricBlockSettings
            .of(Material.STONE)
            .hardness(3f)
            .sounds(BlockSoundGroup.STONE));
    public static final BlockItem CHORUS_HUMUS_BLOCK_ITEM = new BlockItem(CHORUS_HUMUS, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS));

    public static final Block FRESH_CHORUS_HUMUS = new Block(FabricBlockSettings
            .of(Material.STONE)
            .hardness(3f)
            .sounds(BlockSoundGroup.STONE));
    public static final BlockItem FRESH_CHORUS_HUMUS_ITEM = new BlockItem(FRESH_CHORUS_HUMUS, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS));

    // $ End medium
    public static final Block END_MEDIUM = new EndMediumBlock(FabricBlockSettings
            .of(Material.METAL, MapColor.PURPLE)
            .strength(1.25f, 4.2f)
            .sounds(BlockSoundGroup.GILDED_BLACKSTONE)
            .dynamicBounds()
            .ticksRandomly());
    public static final BlockItem END_MEDIUM_BLOCK_ITEM = new BlockItem(END_MEDIUM, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS));

    // End medium layer
    public static final Block END_MEDIUM_LAYER = new EndMediumLayer(FabricBlockSettings
            .of(Material.SNOW_LAYER, MapColor.PALE_PURPLE)
            .strength(0.3f)
            // velocityMultiplier(0.9f)
            .sounds(BlockSoundGroup.GLASS)
            .nonOpaque().blockVision(CometBlocks::never));
    public static final BlockItem END_MEDIUM_LAYER_BLOCK_ITEM = new BlockItem(END_MEDIUM_LAYER, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS));

    // $ Crystallized creature
    public static final Block CRYSTALLIZED_CREATURE = new CrystallizedCreature(FabricBlockSettings
            .of(Material.GLASS, MapColor.PALE_PURPLE)
            .strength(0.3f)
            .sounds(BlockSoundGroup.GLASS)
            .nonOpaque());
    public static final BlockItem CRYSTALLIZED_CREATURE_BLOCK_ITEM = new BlockItem(CRYSTALLIZED_CREATURE, new Item.Settings().group(ItemGroup.DECORATIONS));

    // * Trimmed version
    public static final Block TRIMMED_CRYSTALLIZED_CREATURE = new TrimmedCrystallizedCreature(FabricBlockSettings
            .of(Material.GLASS, MapColor.PALE_PURPLE)
            .strength(0.3f)
            .sounds(BlockSoundGroup.GLASS)
            .nonOpaque());
    public static final BlockItem TRIMMED_CRYSTALLIZED_CREATURE_BLOCK_ITEM = new BlockItem(TRIMMED_CRYSTALLIZED_CREATURE, new Item.Settings().group(ItemGroup.DECORATIONS));

    // * Mob statue
    public static final Block CREATURE_STATUE = new CreatureStatue(FabricBlockSettings
            .of(Material.STONE, MapColor.LIGHT_BLUE)
            .strength(0.3f)
            .sounds(BlockSoundGroup.STONE)
            .nonOpaque());
    public static final BlockItem CREATURE_STATUE_BLOCK_ITEM = new BlockItem(CREATURE_STATUE, new Item.Settings().group(ItemGroup.DECORATIONS));

    // * Block entity
    public static final BlockEntityType<CrystallizedCreatureBlockEntity> CRYSTALLIZED_CREATURE_BLOCK_ENTITY = Registry.register(
            Registry.BLOCK_ENTITY_TYPE,
            new Identifier("comet", "crystallized_creature"),
            FabricBlockEntityTypeBuilder.create(CrystallizedCreatureBlockEntity::new, CRYSTALLIZED_CREATURE, TRIMMED_CRYSTALLIZED_CREATURE, CREATURE_STATUE).build()
    );

    // $ End fire
    public static final Block END_FIRE = new EndFireBlock(FabricBlockSettings
            .of(Material.FIRE, MapColor.PURPLE)
            .noCollision()
            .breakInstantly()
            .luminance(state -> 15)
            .sounds(BlockSoundGroup.WOOL));

    // $ Pumice stone
    public static final Block PUMICE_STONE = new Block(FabricBlockSettings
            .of(Material.STONE)
            .strength(0.4f)
            .sounds(BlockSoundGroup.NETHERRACK));
    public static final Item PUMICE_STONE_BLOCK_ITEM = new BlockItem(PUMICE_STONE, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS));

    public static final Block WATER_PUMICE_STONE = new Block(FabricBlockSettings
            .of(Material.STONE)
            .strength(0.4f)
            .sounds(BlockSoundGroup.NETHERRACK));
    public static final Item WATER_PUMICE_STONE_BLOCK_ITEM = new BlockItem(WATER_PUMICE_STONE, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS));

    public static final Block LAVA_PUMICE_STONE = new Block(FabricBlockSettings
            .of(Material.STONE)
            .strength(0.4f)
            .sounds(BlockSoundGroup.NETHERRACK));
    public static final Item LAVA_PUMICE_STONE_BLOCK_ITEM = new BlockItem(LAVA_PUMICE_STONE, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS));

    // $ Drenchstone
    public static final Block DRENCHSTONE = new DrenchstoneBlock(FabricBlockSettings
            .of(Material.STONE)
            .strength(1.5f, 6.0f)
            .sounds(BlockSoundGroup.STONE));
    public static final Item DRENCHSTONE_BLOCK_ITEM = new BlockItem(DRENCHSTONE, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS));

    public static final Block WATER_DRENCHSTONE = new WaterDrenchstoneBlock(FabricBlockSettings
            .of(Material.STONE)
            .strength(1.5f, 6.0f)
            .sounds(BlockSoundGroup.STONE));

    public static final Block LAVA_DRENCHSTONE = new LavaDrenchstoneBlock(FabricBlockSettings
            .of(Material.STONE)
            .strength(1.5f, 6.0f)
            .sounds(BlockSoundGroup.STONE));


    // $ Nether Drenchstone
    public static final Block NETHER_DRENCHSTONE = new Block(FabricBlockSettings
            .of(Material.STONE)
            .strength(0.4f)
            .sounds(BlockSoundGroup.NETHERRACK));
    public static final Item NETHER_DRENCHSTONE_BLOCK_ITEM = new BlockItem(NETHER_DRENCHSTONE, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS));

    // $ End Drenchstone
    public static final Block END_DRENCHSTONE = new Block(FabricBlockSettings
            .of(Material.STONE)
            .strength(1.5f, 6.0f)
            .sounds(BlockSoundGroup.STONE));
    public static final Item END_DRENCHSTONE_BLOCK_ITEM = new BlockItem(END_DRENCHSTONE, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS));

    // $ Dry Rooted endstone
    public static final Block DRY_ROOTED_ENDSTONE = new Block(FabricBlockSettings
            .of(Material.STONE)
            .strength(1.5f, 6.0f)
            .sounds(BlockSoundGroup.STONE));
    public static final Item DRY_ROOTED_ENDSTONE_BLOCK_ITEM = new BlockItem(DRY_ROOTED_ENDSTONE, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS));

    // $ Fresh Rooted endstone
    public static final Block FRESH_ROOTED_ENDSTONE = new Block(FabricBlockSettings
            .of(Material.STONE)
            .strength(1.5f, 6.0f)
            .sounds(BlockSoundGroup.STONE));
    public static final Item FRESH_ROOTED_ENDSTONE_BLOCK_ITEM = new BlockItem(FRESH_ROOTED_ENDSTONE, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS));

    // _ Block predicates
    private static boolean never(BlockState blockState, BlockView blockView, BlockPos blockPos) {
        return false;
    }

    // _ Examples
// -    Blocks.register("soul_fire", new SoulFireBlock(AbstractBlock.Settings
//            .of(Material.FIRE, MapColor.LIGHT_BLUE)
//            .noCollision()
//            .breakInstantly()
//            .luminance(state -> 10)
//            .sounds(BlockSoundGroup.WOOL)));

    // - Blocks.register("soul_sand", new SoulSandBlock(AbstractBlock.Settings
    // .of(Material.AGGREGATE, MapColor.BROWN)
    // .strength(0.5f)
    // .velocityMultiplier(0.4f)
    // .sounds(BlockSoundGroup.SOUL_SAND)
    // .allowsSpawning(Blocks::always)
    // .solidBlock(Blocks::always)
    // .blockVision(Blocks::always)
    // .suffocates(Blocks::always)));

    // - Blocks.register("glass", new GlassBlock(AbstractBlock.Settings
    // .of(Material.GLASS)
    // .strength(0.3f)
    // .sounds(BlockSoundGroup.GLASS)
    // .nonOpaque()
    // .allowsSpawning(Blocks::never)
    // .solidBlock(Blocks::never)
    // .suffocates(Blocks::never)
    // .blockVision(Blocks::never)));

    public static void register(){
        Registry.register(Registry.BLOCK, new Identifier("comet","end_iron_ore"), END_IRON_ORE);
        Registry.register(Registry.ITEM, new Identifier("comet", "end_iron_ore"), END_IRON_ORE_BLOCK_ITEM);

        Registry.register(Registry.BLOCK, new Identifier("comet","end_iron_ore_revealed"), END_IRON_ORE_REVEALED);

        Registry.register(Registry.BLOCK, new Identifier("comet","endbrite_tube"), ENDBRITE_TUBE);
        Registry.register(Registry.ITEM, new Identifier("comet","endbrite_tube"), ENDBRITE_TUBE_BLOCK_ITEM);

        Registry.register(Registry.BLOCK, new Identifier("comet","chorus_humus"), CHORUS_HUMUS);
        Registry.register(Registry.ITEM, new Identifier("comet","chorus_humus"), CHORUS_HUMUS_BLOCK_ITEM);

        Registry.register(Registry.BLOCK, new Identifier("comet","fresh_chorus_humus"), FRESH_CHORUS_HUMUS);
        Registry.register(Registry.ITEM, new Identifier("comet","fresh_chorus_humus"), FRESH_CHORUS_HUMUS_ITEM);

        Registry.register(Registry.BLOCK, new Identifier("comet", "end_medium"), END_MEDIUM);
        Registry.register(Registry.ITEM, new Identifier("comet", "end_medium"), END_MEDIUM_BLOCK_ITEM);

        Registry.register(Registry.BLOCK, new Identifier("comet", "end_medium_layer"), END_MEDIUM_LAYER);
        Registry.register(Registry.ITEM, new Identifier("comet", "end_medium_layer"), END_MEDIUM_LAYER_BLOCK_ITEM);

        Registry.register(Registry.BLOCK, new Identifier("comet", "crystallized_creature"), CRYSTALLIZED_CREATURE);
        Registry.register(Registry.ITEM, new Identifier("comet", "crystallized_creature"), CRYSTALLIZED_CREATURE_BLOCK_ITEM);

        Registry.register(Registry.BLOCK, new Identifier("comet", "trimmed_crystallized_creature"), TRIMMED_CRYSTALLIZED_CREATURE);
        Registry.register(Registry.ITEM, new Identifier("comet", "trimmed_crystallized_creature"), TRIMMED_CRYSTALLIZED_CREATURE_BLOCK_ITEM);

        Registry.register(Registry.BLOCK, new Identifier("comet", "creature_statue"), CREATURE_STATUE);
        Registry.register(Registry.ITEM, new Identifier("comet", "creature_statue"), CREATURE_STATUE_BLOCK_ITEM);

        Registry.register(Registry.BLOCK, new Identifier("comet", "end_fire"), END_FIRE);

        Registry.register(Registry.BLOCK, new Identifier("comet", "pumice_stone"), PUMICE_STONE);
        Registry.register(Registry.ITEM, new Identifier("comet", "pumice_stone"), PUMICE_STONE_BLOCK_ITEM);

        Registry.register(Registry.BLOCK, new Identifier("comet", "water_pumice_stone"), WATER_PUMICE_STONE);
        Registry.register(Registry.ITEM, new Identifier("comet", "water_pumice_stone"), WATER_PUMICE_STONE_BLOCK_ITEM);

        Registry.register(Registry.BLOCK, new Identifier("comet", "lava_pumice_stone"), LAVA_PUMICE_STONE);
        Registry.register(Registry.ITEM, new Identifier("comet", "lava_pumice_stone"), LAVA_PUMICE_STONE_BLOCK_ITEM);

        Registry.register(Registry.BLOCK, new Identifier("comet", "drenchstone"), DRENCHSTONE);
        Registry.register(Registry.ITEM, new Identifier("comet", "drenchstone"), DRENCHSTONE_BLOCK_ITEM);

        Registry.register(Registry.BLOCK, new Identifier("comet", "water_drenchstone"), WATER_DRENCHSTONE);

        Registry.register(Registry.BLOCK, new Identifier("comet", "lava_drenchstone"), LAVA_DRENCHSTONE);

        Registry.register(Registry.BLOCK, new Identifier("comet", "nether_drenchstone"), NETHER_DRENCHSTONE);
        Registry.register(Registry.ITEM, new Identifier("comet", "nether_drenchstone"), NETHER_DRENCHSTONE_BLOCK_ITEM);

        Registry.register(Registry.BLOCK, new Identifier("comet", "end_drenchstone"), END_DRENCHSTONE);
        Registry.register(Registry.ITEM, new Identifier("comet", "end_drenchstone"), END_DRENCHSTONE_BLOCK_ITEM);

        Registry.register(Registry.BLOCK, new Identifier("comet", "dry_rooted_endstone"), DRY_ROOTED_ENDSTONE);
        Registry.register(Registry.ITEM, new Identifier("comet", "dry_rooted_endstone"), DRY_ROOTED_ENDSTONE_BLOCK_ITEM);

        Registry.register(Registry.BLOCK, new Identifier("comet", "fresh_rooted_endstone"), FRESH_ROOTED_ENDSTONE);
        Registry.register(Registry.ITEM, new Identifier("comet", "fresh_rooted_endstone"), FRESH_ROOTED_ENDSTONE_BLOCK_ITEM);
    }
}
