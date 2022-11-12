package com.github.SoulArts.registries;

import com.github.SoulArts.dimensionalAlloys.block.EndbriteTube;
import com.github.SoulArts.dimensionalAlloys.block.FreshEndMedium;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.entity.EntityType;
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
    public static final Block END_IRON_ORE = new Block(FabricBlockSettings
            .of(Material.STONE)
            .hardness(3f)
            .sounds(BlockSoundGroup.STONE));
    public static final BlockItem END_IRON_ORE_BLOCK_ITEM = new BlockItem(END_IRON_ORE, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS));

    // $ Endbrite tube
    public static final Block ENDBRITE_TUBE = new EndbriteTube(FabricBlockSettings
            .of(Material.STONE)
            .hardness(5f)
            .sounds(BlockSoundGroup.ANCIENT_DEBRIS));
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
    public static final Block END_MEDIUM = new Block(FabricBlockSettings
            .of(Material.METAL, MapColor.PURPLE)
            .strength(1.25f, 4.2f)
            .sounds(BlockSoundGroup.LODESTONE));
    public static final BlockItem END_MEDIUM_BLOCK_ITEM = new BlockItem(END_MEDIUM, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS));

    public static final Block FRESH_END_MEDIUM = new FreshEndMedium(FabricBlockSettings
            .of(Material.GLASS, MapColor.PALE_PURPLE)
            .strength(0.3f)
            .velocityMultiplier(0.9f)
            .sounds(BlockSoundGroup.GLASS)
            .nonOpaque().blockVision(CometBlocks::never));

    public static final BlockItem FRESH_END_MEDIUM_BLOCK_ITEM = new BlockItem(FRESH_END_MEDIUM, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS));

    // _ Block predicates
    private static boolean never(BlockState blockState, BlockView blockView, BlockPos blockPos) {
        return false;
    }

    // _ Examples
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

        Registry.register(Registry.BLOCK, new Identifier("comet","endbrite_tube"), ENDBRITE_TUBE);
        Registry.register(Registry.ITEM, new Identifier("comet","endbrite_tube"), ENDBRITE_TUBE_BLOCK_ITEM);

        Registry.register(Registry.BLOCK, new Identifier("comet","chorus_humus"), CHORUS_HUMUS);
        Registry.register(Registry.ITEM, new Identifier("comet","chorus_humus"), CHORUS_HUMUS_BLOCK_ITEM);

        Registry.register(Registry.BLOCK, new Identifier("comet","fresh_chorus_humus"), FRESH_CHORUS_HUMUS);
        Registry.register(Registry.ITEM, new Identifier("comet","fresh_chorus_humus"), FRESH_CHORUS_HUMUS_ITEM);

        Registry.register(Registry.BLOCK, new Identifier("comet", "end_medium"), END_MEDIUM);
        Registry.register(Registry.ITEM, new Identifier("comet", "end_medium"), END_MEDIUM_BLOCK_ITEM);

        Registry.register(Registry.BLOCK, new Identifier("comet", "fresh_end_medium"), FRESH_END_MEDIUM);
        Registry.register(Registry.ITEM, new Identifier("comet", "fresh_end_medium"), FRESH_END_MEDIUM_BLOCK_ITEM);
    }
}