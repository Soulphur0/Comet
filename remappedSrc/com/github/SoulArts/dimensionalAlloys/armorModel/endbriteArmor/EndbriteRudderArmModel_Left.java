package com.github.SoulArts.dimensionalAlloys.armorModel.endbriteArmor;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;

public class EndbriteRudderArmModel_Left<T extends LivingEntity> extends AnimalModel<T> {
    private final ModelPart bb_main;

    public EndbriteRudderArmModel_Left() {
        textureWidth = 16;
        textureHeight = 16;
        bb_main = new ModelPart(this);
        bb_main.setPivot(11.0F, 10.0F, -2.0F);
        bb_main.setTextureOffset(0, 7).addCuboid(-2.0F, -11.0F, 0.0F, 4.0F, 7.0F, 1.0F, 0.0F, false);
    }

    @Override
    public void setAngles(T livingEntity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
        boolean isWalking = livingEntity.getRoll() > 4;

        float k = 1.0F;

        if (isWalking) {
            k = (float)livingEntity.getVelocity().lengthSquared();
            k /= 0.2F;
            k *= k * k;
        }

        if (k < 1.0F) {
            k = 1.0F;
        }

        this.bb_main.pitch = MathHelper.cos(limbSwing * 0.6662F) * 1.0F * limbSwingAmount * 0.25F / k;
        this.bb_main.roll = 0.0F;
        this.bb_main.yaw = 0.0F;
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
