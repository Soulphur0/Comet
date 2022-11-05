// Made with Blockbench 3.7.5
// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports

package com.github.SoulArts.dimensionalAlloys.armorModel.endbriteArmor;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ArmorStandEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Environment(EnvType.CLIENT)
public class EndbriteArmorModel extends EntityModel<Entity> {
    private final ModelPart helmet;
    private final ModelPart antennae;
    private final ModelPart body;
    private final ModelPart leftArm;
    private final ModelPart rightArm;

    public EndbriteArmorModel(ModelPart root) {
        this.helmet = root.getChild("helmet");
        this.antennae = root.getChild("antennae");
        this.body = root.getChild("body");
        this.leftArm = root.getChild("leftArm");
        this.rightArm = root.getChild("rightArm");
    }

    // TODO: Make antennae tilt backwards when moving, a bit when running, a bit more when flying.
    // TODO: get head pitch right with the armor stand's/Entity head rotation
    // TODO: Scale's the helmet and antennae properly.
    // TODO: Add to the project's schema all learned & used with the Endbrite Helmet
    // TODO: Add feature renderers/models for mobs and players.
    // TODO: Add texture to bottom pieces.
    // TODO: Change helmet texture
    // TODO: Tone down and even out body textures
    // TODO: Add extra attributes and special behaviour.
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData helmet = modelPartData.addChild("helmet", ModelPartBuilder.create().uv(8, 38).cuboid(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(1.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData antennae = modelPartData.addChild("antennae", ModelPartBuilder.create().uv(10, 57).cuboid(-4.0F, -10.0F, -4.0F, 8.0F, 1.0F, 1.0F, new Dilation(1.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create().uv(8, 2).cuboid(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new Dilation(1.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData leftArm = modelPartData.addChild("leftArm", ModelPartBuilder.create().uv(24, 25).mirrored().cuboid(-1.0f, -2.0f, -2.0f, 4.0f, 12.0f, 4.0f, new Dilation(1.0F)).mirrored(false)
        .uv(32, 10).cuboid(4.5f, -2.0f, 1.0f, 4.0F, 5.0F, 1.0F, new Dilation(1.0F)), ModelTransform.pivot(5.0f, 2.0f, 0.0f));

        ModelPartData rightArm = modelPartData.addChild("rightArm", ModelPartBuilder.create().uv(24, 25).cuboid(-3.0f, -2.0f, -2.0f, 4.0f, 12.0f, 4.0f, new Dilation(1.0F))
        .uv(32, 10).mirrored().cuboid(-7.5f, -2.0f, 1.0f, 3.0F, 4.0F, 1.0F, new Dilation(1.0F)).mirrored(false), ModelTransform.pivot(-5.0f, 2.0f, 0.0f));

        return TexturedModelData.of(modelData, 32, 24);
    }

    @Override
    public void setAngles(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        if (entity instanceof ArmorStandEntity armorStandEntity){
            this.helmet.pitch = (float)Math.PI / 180 * armorStandEntity.getHeadRotation().getPitch();
            this.helmet.yaw = (float)Math.PI / 180 * armorStandEntity.getHeadRotation().getYaw();
            this.helmet.roll = (float)Math.PI / 180 * armorStandEntity.getHeadRotation().getRoll();

            this.antennae.pitch = (float)Math.PI / 180 * armorStandEntity.getHeadRotation().getPitch();
            this.antennae.yaw = (float)Math.PI / 180 * armorStandEntity.getHeadRotation().getYaw();
            this.antennae.roll = (float)Math.PI / 180 * armorStandEntity.getHeadRotation().getRoll();

            this.body.pitch = (float)Math.PI / 180 * armorStandEntity.getBodyRotation().getPitch();
            this.body.yaw = (float)Math.PI / 180 * armorStandEntity.getBodyRotation().getYaw();
            this.body.roll = (float)Math.PI / 180 * armorStandEntity.getBodyRotation().getRoll();

            this.leftArm.pitch = (float)Math.PI / 180 * armorStandEntity.getLeftArmRotation().getPitch();
            this.leftArm.yaw = (float)Math.PI / 180 * armorStandEntity.getLeftArmRotation().getYaw();
            this.leftArm.roll = (float)Math.PI / 180 * armorStandEntity.getLeftArmRotation().getRoll();

            this.rightArm.pitch = (float)Math.PI / 180 * armorStandEntity.getRightArmRotation().getPitch();
            this.rightArm.yaw = (float)Math.PI / 180 * armorStandEntity.getRightArmRotation().getYaw();
            this.rightArm.roll = (float)Math.PI / 180 * armorStandEntity.getRightArmRotation().getRoll();
        }
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        helmet.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        antennae.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
    }
}