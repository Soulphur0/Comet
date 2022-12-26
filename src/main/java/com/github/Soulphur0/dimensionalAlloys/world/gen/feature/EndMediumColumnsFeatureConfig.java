package com.github.Soulphur0.dimensionalAlloys.world.gen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.world.gen.feature.FeatureConfig;

public record EndMediumColumnsFeatureConfig(int reach, int height) implements FeatureConfig {
    public EndMediumColumnsFeatureConfig(int reach, int height) {
        this.reach = reach;
        this.height = height;
    }

    public static final Codec<EndMediumColumnsFeatureConfig> CODEC = RecordCodecBuilder.create(
            instance ->
                    instance.group(
                            Codecs.POSITIVE_INT.fieldOf("reach").forGetter(EndMediumColumnsFeatureConfig::reach),
                            Codecs.POSITIVE_INT.fieldOf("height").forGetter(EndMediumColumnsFeatureConfig::height))
                    .apply(instance, EndMediumColumnsFeatureConfig::new));

    public int reach() {
        return reach;
    }

    public int height() {
        return height;
    }
}
