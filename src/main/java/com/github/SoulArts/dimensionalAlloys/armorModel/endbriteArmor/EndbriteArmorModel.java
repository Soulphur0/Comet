// Made with Blockbench 3.7.5
// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports

package com.github.SoulArts.dimensionalAlloys.armorModel.endbriteArmor;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;

@Environment(EnvType.CLIENT)
public class EndbriteArmorModel<T extends LivingEntity> extends BipedEntityModel<T> {

    // * Vanilla
    public final ModelPart hat;
    public final ModelPart head;
    public final ModelPart body;
    public final ModelPart left_arm;
    public final ModelPart right_arm;
    public final ModelPart left_leg;
    public final ModelPart right_leg;

    public final ModelPart inner_body;
    public final ModelPart right_foot;
    public final ModelPart left_foot;

    public EndbriteArmorModel(ModelPart root) {
        super(root);
        this.hat = root.getChild("hat");
        this.head = root.getChild("head");
        this.body = root.getChild("body");
        this.left_arm = root.getChild("left_arm");
        this.right_arm = root.getChild("right_arm");
        this.left_leg = root.getChild("left_leg");
        this.right_leg = root.getChild("right_leg");

        this.inner_body = root.getChild("inner_body");
        this.right_foot = root.getChild("right_foot");
        this.left_foot = root.getChild("left_foot");
    }


    // TODO: Scale's the helmet and antennae properly so enchantments don't glitch out.
    // TODO: Add to the project's schema all learned & used with the Endbrite Helmet
    // TODO: Add extra attributes and special behaviour to armor.
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        // _ Vanilla biped entity model parts.

        ModelPartData hat = modelPartData.addChild("hat", ModelPartBuilder.create().uv(0, 0).cuboid(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData head = modelPartData.addChild("head", ModelPartBuilder.create().uv(8, 38).cuboid(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(1.0F))
        .uv(10, 57).cuboid(-4.0F, -10.0F, -4.0F, 8.0F, 1.0F, 1.0F, new Dilation(1.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create().uv(8, 2).cuboid(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new Dilation(1.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData left_arm = modelPartData.addChild("left_arm", ModelPartBuilder.create().uv(24, 25).mirrored().cuboid(-1.0f, -2.0f, -2.0f, 4.0f, 12.0f, 4.0f, new Dilation(1.0F)).mirrored(false)
        .uv(44, 45).cuboid(4.5f, -2.0f, 1.0f, 4.0F, 5.0F, 1.0F, new Dilation(1.0F)), ModelTransform.pivot(5.0f, 2.0f, 0.0f));

        ModelPartData right_arm = modelPartData.addChild("right_arm", ModelPartBuilder.create().uv(24, 25).cuboid(-3.0f, -2.0f, -2.0f, 4.0f, 12.0f, 4.0f, new Dilation(1.0F))
        .uv(44, 45).mirrored().cuboid(-7.5f, -2.0f, 1.0f, 3.0F, 4.0F, 1.0F, new Dilation(1.0F)).mirrored(false), ModelTransform.pivot(-5.0f, 2.0f, 0.0f));

        ModelPartData left_leg = modelPartData.addChild("left_leg", ModelPartBuilder.create().uv(40, 22).mirrored().cuboid(-2.0F, -0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.6F)).mirrored(false)
        .uv(40, 45).cuboid(2.5F, -0.0F, 0.0F, 1.0F, 7.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(1.9F, 12.0F, 0.0F));

        ModelPartData right_leg = modelPartData.addChild("right_leg", ModelPartBuilder.create().uv(40, 22).cuboid(-2.0F, -0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.6F))
        .uv(40, 45).cuboid(-3.5F, -0.0F, 0.0F, 1.0F, 7.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(-1.9F, 12.0F, 0.0F));

        // _ Endbrite armor parts and different vanilla part setup

        ModelPartData inner_body = modelPartData.addChild("inner_body", ModelPartBuilder.create().uv(32, 6).cuboid(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new Dilation(0.6F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData right_foot = modelPartData.addChild("right_foot", ModelPartBuilder.create().uv(8, 22).cuboid(-2.0F, -0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(1.0F)), ModelTransform.pivot(-1.9F, 12.0F, 0.0F));

        ModelPartData left_foot = modelPartData.addChild("left_foot", ModelPartBuilder.create().uv(8, 22).mirrored().cuboid(-2.0F, -0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(1.0F)).mirrored(false), ModelTransform.pivot(1.9F, 12.0F, 0.0F));

        return TexturedModelData.of(modelData, 64, 64);
    }

    public void setAngles(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    public void specialSetAngles(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, BipedEntityModel model){
        this.head.copyTransform(model.head);

        this.body.copyTransform(model.body);
        this.inner_body.copyTransform(model.body);

        this.right_arm.copyTransform(model.rightArm);
        this.left_arm.copyTransform(model.leftArm);

        this.right_leg.copyTransform(model.rightLeg);
        this.left_leg.copyTransform(model.leftLeg);

        this.right_foot.copyTransform(model.rightLeg);
        this.left_foot.copyTransform(model.leftLeg);
    }

    @Override
    protected Iterable<ModelPart> getBodyParts() {
        return Iterables.concat(super.getBodyParts(), ImmutableList.of(this.inner_body, this.left_foot, this.right_foot));
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        head.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);

        body.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);

        left_arm.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        right_arm.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);

        left_leg.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        right_leg.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);

        inner_body.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);

        right_foot.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        left_foot.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
    }
}
