package com.github.SoulArts.mixin;

import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.util.SpriteIdentifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Set;

@Mixin(ModelLoader.class)
public interface ModelLoaderAccessor {
    @Accessor("DEFAULT_TEXTURES")
    static Set<SpriteIdentifier> getDeafaultTextures(){
        throw new AssertionError();
    }

    @Accessor("DEFAULT_TEXTURES")
    static void setDeafaultTextures(Set<SpriteIdentifier> textures){
        throw new AssertionError();
    }
}
