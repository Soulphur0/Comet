package com.github.Soulphur0.dimensionalAlloys.world.gen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.world.gen.feature.FeatureConfig;

public record EndIronOreFeatureConfig(int blockAmount) implements FeatureConfig {
    public static final Codec<EndIronOreFeatureConfig> CODEC = RecordCodecBuilder.create(
            instance ->
                    instance.group(
                            Codecs.POSITIVE_INT.fieldOf("block_amount").forGetter(EndIronOreFeatureConfig::blockAmount))
                    .apply(instance, EndIronOreFeatureConfig::new));
}
