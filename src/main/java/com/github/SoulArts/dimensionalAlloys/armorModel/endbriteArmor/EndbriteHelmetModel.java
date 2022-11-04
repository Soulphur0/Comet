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
public class EndbriteHelmetModel extends EntityModel<Entity> {
	private final ModelPart helmet;
	private final ModelPart antennae;

	public EndbriteHelmetModel(ModelPart root) {
		this.helmet = root.getChild("helmet");
		this.antennae = root.getChild("antennae");
	}

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
		ModelPartData helmet = modelPartData.addChild("helmet", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(1.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData antennae = modelPartData.addChild("antennae", ModelPartBuilder.create().uv(2, 19).cuboid(-4.0F, -10.0F, -4.0F, 8.0F, 1.0F, 1.0F, new Dilation(1.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
		return TexturedModelData.of(modelData, 32, 24);
	}

	@Override
	public void setAngles(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		if (entity instanceof ArmorStandEntity armorStandEntity){
			this.helmet.pitch = 0.017453292F * armorStandEntity.getHeadRotation().getPitch();
			this.helmet.yaw = 0.017453292F * armorStandEntity.getHeadRotation().getYaw();
			this.helmet.roll = 0.017453292F * armorStandEntity.getHeadRotation().getRoll();

			this.antennae.pitch = 0.017453292F * armorStandEntity.getHeadRotation().getPitch();
			this.antennae.yaw = 0.017453292F * armorStandEntity.getHeadRotation().getYaw();
			this.antennae.roll = 0.017453292F * armorStandEntity.getHeadRotation().getRoll();
		}
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		helmet.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		antennae.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}
}