package com.github.Soulphur0.dimensionalAlloys.world.biome.source;

import net.fabricmc.fabric.mixin.biome.TheEndBiomeSourceMixin;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;

import java.util.HashSet;

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
