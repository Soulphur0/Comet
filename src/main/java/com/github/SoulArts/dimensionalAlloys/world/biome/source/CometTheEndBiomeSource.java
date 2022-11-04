package com.github.SoulArts.dimensionalAlloys.world.biome.source;

import com.github.SoulArts.Comet;
import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import net.fabricmc.fabric.mixin.biome.TheEndBiomeSourceMixin;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.biome.TheEndBiomeCreator;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.biome.source.util.MultiNoiseUtil;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Stream;

public class CometTheEndBiomeSource extends TheEndBiomeSourceMixin {

    HashSet<RegistryEntry<Biome>> biomeSet;
    private final RegistryEntry<Biome> highlandsBiome;

    public CometTheEndBiomeSource(Registry<Biome> biomeRegistry){
        this(biomeRegistry.getOrCreateEntry(BiomeKeys.END_HIGHLANDS));
    }

    private CometTheEndBiomeSource(RegistryEntry<Biome> highlandsBiomeReplazable){
        this.highlandsBiome = highlandsBiomeReplazable;

        biomeSet = new HashSet<>();
        biomeSet.add(highlandsBiome);
        super.fabric_modifyBiomeSet(biomeSet);

    }

}
