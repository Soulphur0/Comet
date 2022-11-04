package com.github.SoulArts.mixin;

import net.minecraft.client.resource.metadata.AnimationFrameResourceMetadata;
import net.minecraft.client.resource.metadata.AnimationResourceMetadata;
import net.minecraft.client.texture.AbstractTexture;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.texture.TextureStitcher;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

@Mixin(SpriteAtlasTexture.class)
public class SpriteAtlasTextureMixin extends AbstractTexture {
    @Override
    public void load(ResourceManager manager) throws IOException {

    }
    @ModifyVariable(method = "stitch", at = @At("STORE"), ordinal = 0)
    private TextureStitcher addMyTexture(TextureStitcher stitcher) {
        AnimationResourceMetadata animationResourceMetadata = AnimationResourceMetadata.EMPTY;
        stitcher.add(new Sprite.Info(new Identifier("entity/mirror_shield"), 64, 64, animationResourceMetadata));
        stitcher.add(new Sprite.Info(new Identifier("entity/mirror_shield_base_nopattern"), 64, 64,animationResourceMetadata));
        return stitcher;
    }
}
