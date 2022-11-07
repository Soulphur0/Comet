package com.github.SoulArts.dimensionalAlloys;

import com.github.SoulArts.Comet;
import com.github.SoulArts.dimensionalAlloys.armorModel.endbriteArmor.EndbriteArmorModel2;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.DyeableArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class CometArmorFeatureRenderer<T extends LivingEntity, M extends BipedEntityModel<T>, A extends BipedEntityModel<T>> extends FeatureRenderer<T, M>  {
    private static final Identifier ENDBRITE_ARMOR_TEXTURE = new Identifier("textures/endbrite_layer_1.png");
    private final A endbriteArmorModel;

    public CometArmorFeatureRenderer(FeatureRendererContext<T, M> featureRendererContext,A endbriteArmorModel) {
        super(featureRendererContext);
        this.endbriteArmorModel = endbriteArmorModel;
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, T livingEntity, float f, float g, float h, float j, float k, float l) {
        this.renderArmor(matrixStack, vertexConsumerProvider, livingEntity, EquipmentSlot.CHEST, i, endbriteArmorModel, f, g, j, k, l);
        this.renderArmor(matrixStack, vertexConsumerProvider, livingEntity, EquipmentSlot.LEGS, i, endbriteArmorModel, f, g, j, k, l);
        this.renderArmor(matrixStack, vertexConsumerProvider, livingEntity, EquipmentSlot.FEET, i, endbriteArmorModel, f, g, j, k, l);
        this.renderArmor(matrixStack, vertexConsumerProvider, livingEntity, EquipmentSlot.HEAD, i, endbriteArmorModel, f, g, j, k, l);
    }

    private void renderArmor(MatrixStack matrices, VertexConsumerProvider vertexConsumers, T entity, EquipmentSlot armorSlot, int light, A model, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        ItemStack itemStack = ((LivingEntity)entity).getEquippedStack(armorSlot);
        if (!(itemStack.getItem() instanceof ArmorItem)) {
            return;
        }
        ArmorItem armorItem = (ArmorItem)itemStack.getItem();
        if (armorItem.getSlotType() != armorSlot) {
            return;
        }
        this.getContextModel().setAttributes(model);

        // - Hide comet armor models.
        hideEndbriteArmorModel((EndbriteArmorModel2)model);

        // - Rotate comet armor model.
        ((EndbriteArmorModel2)model).setAngles(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

        // - Un-hide comet armor models if a comet item is equipped.
        if (checkIfCometItem(itemStack))
            this.setVisible(model, armorSlot);

        boolean bl2 = itemStack.hasGlint();
        if (armorItem instanceof DyeableArmorItem) {
            int i = ((DyeableArmorItem)armorItem).getColor(itemStack);
            float f = (float)(i >> 16 & 0xFF) / 255.0f;
            float g = (float)(i >> 8 & 0xFF) / 255.0f;
            float h = (float)(i & 0xFF) / 255.0f;
            this.renderArmorParts(matrices, vertexConsumers, light, armorItem, bl2, model, f, g, h, null);
            this.renderArmorParts(matrices, vertexConsumers, light, armorItem, bl2, model, 1.0f, 1.0f, 1.0f, "overlay");
        } else {
            this.renderArmorParts(matrices, vertexConsumers, light, armorItem, bl2, model, 1.0f, 1.0f, 1.0f, null);
        }
    }

    private boolean checkIfCometItem(ItemStack itemStack){
        return  itemStack.isOf(Comet.ENDBRITE_HELMET) ||
                itemStack.isOf(Comet.ENDBRITE_CHESTPLATE) ||
                itemStack.isOf(Comet.ENDBRITE_LEGGINGS) ||
                itemStack.isOf(Comet.ENDBRITE_BOOTS);
    }

    protected void setVisible(BipedEntityModel bipedModel, EquipmentSlot slot) {
        if (bipedModel instanceof EndbriteArmorModel2){
            setVisibleEndbrite((EndbriteArmorModel2) bipedModel, slot);
        }
    }

    private void hideEndbriteArmorModel(EndbriteArmorModel2 bipedModel){
        bipedModel.head.visible = false;

        bipedModel.body.visible = false;
        bipedModel.inner_body.visible = false;

        bipedModel.right_arm.visible = false;
        bipedModel.left_arm.visible = false;

        bipedModel.right_leg.visible = false;
        bipedModel.left_leg.visible =false;

        bipedModel.right_foot.visible = false;
        bipedModel.left_foot.visible = false;
    }

    private void setVisibleEndbrite(EndbriteArmorModel2 model, EquipmentSlot slot){
        switch (slot) {
            case HEAD -> {
                model.head.visible = true;
                model.hat.visible = true;
            }
            case CHEST -> {
                model.body.visible = true;
                model.right_arm.visible = true;
                model.left_arm.visible = true;
            }
            case LEGS -> {
                model.inner_body.visible = true;
                model.right_leg.visible = true;
                model.left_leg.visible = true;
            }
            case FEET -> {
                model.right_foot.visible = true;
                model.left_foot.visible = true;
            }
        }
    }

    private void renderArmorParts(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, ArmorItem item, boolean glint, A model, float red, float green, float blue, @Nullable String overlay) {
        VertexConsumer vertexConsumer = ItemRenderer.getArmorGlintConsumer(vertexConsumers, RenderLayer.getArmorCutoutNoCull(ENDBRITE_ARMOR_TEXTURE), false, glint);
        ((AnimalModel)model).render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, red, green, blue, 1.0f);
    }
}