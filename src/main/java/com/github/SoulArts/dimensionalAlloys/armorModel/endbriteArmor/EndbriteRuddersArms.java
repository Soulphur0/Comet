/*
package com.github.SoulArts.dimensionalAlloys.armorModel.endbriteArmor;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;

public class EndbriteRuddersArms<T extends LivingEntity> extends AnimalModel<T> {
    private final ModelPart bb_main;

    public EndbriteRuddersArms() {
        textureWidth = 16;
        textureHeight = 16;
        bb_main = new ModelPart(this);
        bb_main.setPivot(0.0F, 10.0F, 0.0F);
        bb_main.setTextureOffset(0, 0).addCuboid(-13.0F, -11.0F, -1.0F, 4.0F, 7.0F, 1.0F, 0.0F, false);
        bb_main.setTextureOffset(0, 7).addCuboid(9.0F, -11.0F, -1.0F, 4.0F, 7.0F, 1.0F, 0.0F, false);
    }

    @Override
    public void setAngles(T livingEntity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
        //previously the render function, render code was moved to a method below

        //this.bb_main.pitch = headPitch * 0.017453292F;
        //this.bb_main.yaw = netHeadYaw * 0.017453292F;
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        bb_main.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    @Override
    protected Iterable<ModelPart> getHeadParts() {
        return null;
    }

    @Override
    protected Iterable<ModelPart> getBodyParts() {
        return null;
    }

    public void setRotationAngle(ModelPart bone, float x, float y, float z) {
        bone.pitch = x;
        bone.yaw = y;
        bone.roll = z;
    }
}
 */
