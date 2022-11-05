package com.github.SoulArts.dimensionalAlloys;

import com.github.SoulArts.Comet;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class CometArmorFeatureRenderer<T extends LivingEntity, M extends BipedEntityModel<T>, A extends EntityModel<T>> extends FeatureRenderer<T, M>  {
    private static final Identifier ENDBRITE_HELMET_TEXTURE = new Identifier("textures/endbrite_helmet.png");
    private static final Identifier ENDBRITE_CHESTPLATE_TEXTURE = new Identifier("textures/endbrite_chestplate.png");

    private final A endbriteHelmet;
    private final A endbriteChestplate;

    public CometArmorFeatureRenderer(FeatureRendererContext<T, M> featureRendererContext, A endbriteHelmet, A endbriteChestplate) {
        super(featureRendererContext);
        this.endbriteHelmet = endbriteHelmet;
        this.endbriteChestplate = endbriteChestplate;
    }

    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, T livingEntity, float f, float g, float h, float j, float k, float l) {
        ItemStack headItem = livingEntity.getEquippedStack(EquipmentSlot.HEAD);
        ItemStack chestItem = livingEntity.getEquippedStack(EquipmentSlot.CHEST);

        // - Render helmet
        if (headItem.getItem() == Comet.ENDBRITE_HELMET) {
            matrixStack.push();
            matrixStack.translate(0.0D, 0.0625D, 0.0D);
            this.getContextModel().copyStateTo(this.endbriteHelmet);
            this.endbriteHelmet.setAngles(livingEntity, f, g, j, k, l);
            VertexConsumer vertexConsumer = ItemRenderer.getArmorGlintConsumer(vertexConsumerProvider, RenderLayer.getArmorCutoutNoCull(ENDBRITE_HELMET_TEXTURE), false, headItem.hasGlint());
            this.endbriteHelmet.render(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
            matrixStack.pop();
        }

        // - Render chestplate
        if (chestItem.getItem() == Comet.ENDBRITE_CHESTPLATE){
            matrixStack.push();
            matrixStack.translate(0.0D, 0.0D, 0.0D);
            this.getContextModel().copyStateTo(this.endbriteChestplate);
            this.endbriteChestplate.setAngles(livingEntity, f, g, j, k, l);
            VertexConsumer vertexConsumer = ItemRenderer.getArmorGlintConsumer(vertexConsumerProvider, RenderLayer.getArmorCutoutNoCull(ENDBRITE_CHESTPLATE_TEXTURE), false, headItem.hasGlint());
            this.endbriteChestplate.render(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
            matrixStack.pop();
        }
    }
}