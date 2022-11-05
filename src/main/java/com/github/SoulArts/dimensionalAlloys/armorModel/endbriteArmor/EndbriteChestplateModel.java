// Made with Blockbench 3.7.5
// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports

package com.github.SoulArts.dimensionalAlloys.armorModel.endbriteArmor;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.mob.ZombieEntity;

@Environment(EnvType.CLIENT)
public class EndbriteChestplateModel extends EntityModel<Entity> {
	private final ModelPart body;
	private final ModelPart leftArm;
	private final ModelPart rightArm;

	public EndbriteChestplateModel(ModelPart root) {
		this.body = root.getChild("body");
		this.leftArm = root.getChild("leftArm");
		this.rightArm = root.getChild("rightArm");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();

		ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create().uv(1, 3).cuboid(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new Dilation(1.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData leftArm = modelPartData.addChild("leftArm", ModelPartBuilder.create().uv(1, 20).mirrored().cuboid(-1.0f, -2.0f, -2.0f, 4.0f, 12.0f, 4.0f, new Dilation(1.0F)).mirrored(false)
		.uv(17, 28).cuboid(4.5f, -2.0f, 1.0f, 4.0F, 5.0F, 1.0F, new Dilation(1.0F)), ModelTransform.pivot(5.0f, 2.0f, 0.0f));

		ModelPartData rightArm = modelPartData.addChild("rightArm", ModelPartBuilder.create().uv(1, 20).cuboid(-3.0f, -2.0f, -2.0f, 4.0f, 12.0f, 4.0f, new Dilation(1.0F))
		.uv(17, 28).mirrored().cuboid(-7.5f, -2.0f, 1.0f, 3.0F, 4.0F, 1.0F, new Dilation(1.0F)).mirrored(false), ModelTransform.pivot(-5.0f, 2.0f, 0.0f));

		return TexturedModelData.of(modelData, 28, 42);
	}

	@Override
	public void setAngles(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		if (entity instanceof ArmorStandEntity armorStandEntity){
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
		body.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		leftArm.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		rightArm.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}
}