package com.github.Soulphur0.registries;

import com.github.Soulphur0.dimensionalAlloys.world.gen.feature.EndMediumColumnsFeature;
import com.github.Soulphur0.dimensionalAlloys.world.gen.feature.EndMediumColumnsFeatureConfig;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.placementmodifier.SquarePlacementModifier;

import java.util.List;

public class CometEndFeatures {

    // $ End medium columns
    public static final Identifier END_MEDIUM_COLUMNS_FEATURE_ID =  new Identifier("comet", "end_medium_columns");
    public static final Feature<EndMediumColumnsFeatureConfig> END_MEDIUM_COLUMNS_FEATURE = new EndMediumColumnsFeature(EndMediumColumnsFeatureConfig.CODEC);
    public static final ConfiguredFeature<EndMediumColumnsFeatureConfig, EndMediumColumnsFeature> END_MEDIUM_COLUMNS_FEATURE_CONFIGURED = new ConfiguredFeature<>((EndMediumColumnsFeature) END_MEDIUM_COLUMNS_FEATURE, new EndMediumColumnsFeatureConfig(4, 5));
    public static PlacedFeature END_MEDIUM_COLUMNS_PLACED_FEATURE = new PlacedFeature(RegistryEntry.of(END_MEDIUM_COLUMNS_FEATURE_CONFIGURED), List.of(SquarePlacementModifier.of()));

    public static void register(){
        // _ End medium columns
        Registry.register(Registry.FEATURE, END_MEDIUM_COLUMNS_FEATURE_ID, END_MEDIUM_COLUMNS_FEATURE);
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, END_MEDIUM_COLUMNS_FEATURE_ID, END_MEDIUM_COLUMNS_FEATURE_CONFIGURED);
        Registry.register(BuiltinRegistries.PLACED_FEATURE, END_MEDIUM_COLUMNS_FEATURE_ID, END_MEDIUM_COLUMNS_PLACED_FEATURE);

        // ? Add to world generation
        BiomeModifications.addFeature(
                BiomeSelectors.foundInTheEnd(),
                GenerationStep.Feature.VEGETAL_DECORATION,
                RegistryKey.of(Registry.PLACED_FEATURE_KEY, END_MEDIUM_COLUMNS_FEATURE_ID));
    }
}
