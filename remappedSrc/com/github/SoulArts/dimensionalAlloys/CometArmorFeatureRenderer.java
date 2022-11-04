package com.github.SoulArts.dimensionalAlloys;

import com.github.SoulArts.Comet;
import com.github.SoulArts.dimensionalAlloys.armorModel.endbriteArmor.EndbriteHelmetModel;
import com.github.SoulArts.dimensionalAlloys.armorModel.endbriteArmor.EndbriteRudderArmModel_Left;
import com.github.SoulArts.dimensionalAlloys.armorModel.endbriteArmor.EndbriteRudderArmModel_Right;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class CometArmorFeatureRenderer<T extends LivingEntity, M extends EntityModel<T>> extends FeatureRenderer<T, M> {
    private static final Identifier ENDBRITE_HELMET_TEXTURE = new Identifier("textures/endbrite_helmet.png");
    private static final Identifier ENDBRITE_RUDDERS_TEXTURE = new Identifier("textures/endbrite_rudders.png");

    private final EndbriteHelmetModel<T> endbriteHelmet = new EndbriteHelmetModel();
    private final EndbriteRudderArmModel_Left<T> endbriteRudderArmLeft = new EndbriteRudderArmModel_Left();
    private final EndbriteRudderArmModel_Right<T> endbriteRudderArmRight = new EndbriteRudderArmModel_Right();

    public CometArmorFeatureRenderer(FeatureRendererContext<T, M> featureRendererContext) {
        super(featureRendererContext);
    }

    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, T livingEntity, float f, float g, float h, float j, float k, float l) {
        ItemStack headItem = livingEntity.getEquippedStack(EquipmentSlot.HEAD);
        ItemStack chestItem = livingEntity.getEquippedStack(EquipmentSlot.CHEST);
        if (headItem.getItem() == Comet.ENDBRITE_HELMET) {
            matrixStack.push();
            matrixStack.translate(0.0D, 0.0D, 0.125D);
            this.getContextModel().copyStateTo(this.endbriteHelmet);
            this.endbriteHelmet.setAngles(livingEntity, f, g, j, k, l);
            VertexConsumer vertexConsumer = ItemRenderer.getArmorGlintConsumer(vertexConsumerProvider, RenderLayer.getArmorCutoutNoCull(ENDBRITE_HELMET_TEXTURE), false, headItem.hasGlint());
            this.endbriteHelmet.render(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
            matrixStack.pop();
        }
        if (chestItem.getItem() == Comet.ENDBRITE_CHESTPLATE){
            matrixStack.push();
            matrixStack.translate(0.0D, 0.0D, 0.125D);
            this.getContextModel().copyStateTo(this.endbriteRudderArmLeft);
            this.endbriteRudderArmLeft.setAngles(livingEntity, f, g, j, k, l);
            VertexConsumer vertexConsumer = ItemRenderer.getArmorGlintConsumer(vertexConsumerProvider, RenderLayer.getArmorCutoutNoCull(ENDBRITE_RUDDERS_TEXTURE), false, headItem.hasGlint());
            this.endbriteRudderArmLeft.render(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
            matrixStack.pop();

            matrixStack.push();
            matrixStack.translate(0.0D, 0.0D, 0.125D);
            this.getContextModel().copyStateTo(this.endbriteRudderArmRight);
            this.endbriteRudderArmRight.setAngles(livingEntity, f, g, j, k, l);
            VertexConsumer vertexConsumer2 = ItemRenderer.getArmorGlintConsumer(vertexConsumerProvider, RenderLayer.getArmorCutoutNoCull(ENDBRITE_RUDDERS_TEXTURE), false, headItem.hasGlint());
            this.endbriteRudderArmRight.render(matrixStack, vertexConsumer2, i, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
            matrixStack.pop();
        }
    }
}
