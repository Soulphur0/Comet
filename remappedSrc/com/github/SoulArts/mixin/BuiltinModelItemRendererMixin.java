package com.github.SoulArts.mixin;

import com.github.SoulArts.Comet;
import com.github.SoulArts.dimensionalAlloys.shieldModel.MirrorShieldEntityModel;
import com.google.common.collect.Sets;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.SoftOverride;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Set;

@Mixin(BuiltinModelItemRenderer.class)
public class BuiltinModelItemRendererMixin {
    // Hash set con texturas por defecto para los objetos de comet que utilizan el ModelLoader.
    // Utilizan el ModelLoader escudos, tridentes, libro mesa encantamientos, y otros objetos 3D animados.

    private final MirrorShieldEntityModel modelMirrorShield = new MirrorShieldEntityModel();

    private static final SpriteIdentifier MIRROR_SHIELD_BASE = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, new Identifier("entity/mirror_shield"));
    private static final SpriteIdentifier MIRROR_SHIELD_BASE_NO_PATTERN = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, new Identifier("entity/mirror_shield_base_nopattern"));

    boolean bol = true;

    @Inject(at = @At(value = "TAIL", target = "Lnet/minecraft/client/render/item/BuiltinModelItemRenderer;render(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformation$Mode;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;II)V"), method = "render(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformation$Mode;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;II)V")
    public void render(ItemStack stack, ModelTransformation.Mode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, CallbackInfo ci) {
        Set<SpriteIdentifier> DEFAULT_TEXTURES = ModelLoaderAccessor.getDeafaultTextures();
        DEFAULT_TEXTURES.add(MIRROR_SHIELD_BASE);
        DEFAULT_TEXTURES.add(MIRROR_SHIELD_BASE_NO_PATTERN);
        ModelLoaderAccessor.setDeafaultTextures(DEFAULT_TEXTURES);

        if (bol){
            System.out.println(ModelLoaderAccessor.getDeafaultTextures());
            bol = false;
        }


        Item cometItem = stack.getItem();
        if (cometItem == Comet.MIRROR_SHIELD){
            boolean bl = stack.getSubTag("BlockEntityTag") != null;
            matrices.push();
            matrices.scale(1.0F, -1.0F, -1.0F);
            SpriteIdentifier spriteIdentifier = bl ? MIRROR_SHIELD_BASE : MIRROR_SHIELD_BASE_NO_PATTERN;
            VertexConsumer vertexConsumer = spriteIdentifier.getSprite().getTextureSpecificVertexConsumer(ItemRenderer.getDirectItemGlintConsumer(vertexConsumers, this.modelMirrorShield.getLayer(spriteIdentifier.getAtlasId()), true, stack.hasGlint()));
            this.modelMirrorShield.getHandle().render(matrices, vertexConsumer, light, overlay, 1.0F, 1.0F, 1.0F, 1.0F);
            this.modelMirrorShield.getPlate().render(matrices, vertexConsumer, light, overlay, 1.0F, 1.0F, 1.0F, 1.0F);

            matrices.pop();
        }
    }
}
