package com.github.Soulphur0.mixin.client.render.item;

import com.github.Soulphur0.Comet;
import com.github.Soulphur0.CometClient;
import com.github.Soulphur0.dimensionalAlloys.client.render.entity.model.PortalShieldEntityModel;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BuiltinModelItemRenderer.class)
public class BuiltinModelItemRendererMixin {

    private static final Identifier PORTAL_SHIELD = new Identifier("comet", "/textures/entity/portal_shield.png");

    @Inject(method = "render", at = @At("TAIL"))
    private void comet_addPortalShieldItemRenderer(ItemStack stack, ModelTransformation.Mode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, CallbackInfo ci){
        PortalShieldEntityModel modelShield = new PortalShieldEntityModel(MinecraftClient.getInstance().getEntityModelLoader().getModelPart(CometClient.PORTAL_SHIELD_MODEL_LAYER));
        if (stack.isOf(Comet.PORTAL_SHIELD)){
            matrices.push();
            matrices.scale(1.0f, -1.0f, -1.0f);
            SpriteIdentifier spriteIdentifier = Comet.PORTAL_SHIELD_SPRITE;
            VertexConsumer vertexConsumer = ItemRenderer.getItemGlintConsumer(vertexConsumers, modelShield.getLayer(PORTAL_SHIELD), false, stack.hasGlint());//spriteIdentifier.getSprite().getTextureSpecificVertexConsumer(ItemRenderer.getDirectItemGlintConsumer(vertexConsumers, modelShield.getLayer(spriteIdentifier.getTextureId()), true, stack.hasGlint()));
            modelShield.getHandle().render(matrices, vertexConsumer, light, overlay, 1.0f, 1.0f, 1.0f, 1.0f);
            modelShield.getPlate().render(matrices, vertexConsumer, light, overlay, 1.0f, 1.0f, 1.0f, 1.0f);
            vertexConsumers.getBuffer(RenderLayer.getEndGateway());
            modelShield.getPortal().render(matrices, vertexConsumer, light , overlay , 1.0f, 1.0f, 1.0f, 1.0f);
            matrices.pop();
        }
    }
}
