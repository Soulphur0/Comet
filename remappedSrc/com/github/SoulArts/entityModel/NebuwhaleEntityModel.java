package com.github.SoulArts.entityModel;

import com.github.SoulArts.dimensionalAlloys.entity.NebuwhaleEntity;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.world.Difficulty;

public class NebuwhaleEntityModel extends EntityModel<NebuwhaleEntity> {

    private final ModelPart tail;
    private final ModelPart fluke;
    private final ModelPart belly;
    private final ModelPart Head;
    private final ModelPart bb_main;

    public NebuwhaleEntityModel() {
        textureWidth = 128;
        textureHeight = 128;
        tail = new ModelPart(this);
        tail.setPivot(-6.0F, 20.0F, 30.0F);
        tail.setTextureOffset(0, 0).addCuboid(-164.1F, -84.1F, 236.1F, 328.0F, 168.0F, 328.0F, 0.1F, false);
        tail.setTextureOffset(0, 0).addCuboid(-204.1F, -124.1F, 236.1F, 408.0F, 88.0F, 248.0F, 0.1F, false);

        fluke = new ModelPart(this);
        fluke.setPivot(-234.0F, -156.0F, 1170.0F);
        tail.addChild(fluke);
        fluke.setTextureOffset(0, 0).addCuboid(-6.0F, 76.0F, -610.0F, 520.0F, 160.0F, 160.0F, 0.0F, false);
        fluke.setTextureOffset(0, 0).addCuboid(74.0F, 116.0F, -450.0F, 360.0F, 80.0F, 280.0F, 0.0F, false);
        fluke.setTextureOffset(0, 0).addCuboid(434.0F, 76.0F, -450.0F, 160.0F, 160.0F, 480.0F, 0.0F, false);
        fluke.setTextureOffset(0, 0).addCuboid(-86.0F, 76.0F, -450.0F, 160.0F, 160.0F, 480.0F, 0.0F, false);

        belly = new ModelPart(this);
        belly.setPivot(0.0F, 24.0F, 0.0F);
        belly.setTextureOffset(0, 0).addCuboid(-206.0F, 156.0F, -890.0F, 400.0F, 40.0F, 680.0F, 0.0F, false);
        belly.setTextureOffset(0, 0).addCuboid(-166.0F, 116.0F, -210.0F, 320.0F, 40.0F, 440.0F, 0.0F, false);

        Head = new ModelPart(this);
        Head.setPivot(0.0F, 21.0F, -1.0F);
        Head.setTextureOffset(0, 0).addCuboid(-286.0F, -201.0F, -1209.0F, 560.0F, 120.0F, 320.0F, 0.0F, false);
        Head.setTextureOffset(0, 0).addCuboid(-286.0F, -81.0F, -1209.0F, 560.0F, 80.0F, 200.0F, 0.0F, false);
        Head.setTextureOffset(0, 0).addCuboid(-286.0F, -201.0F, -889.0F, 560.0F, 120.0F, 200.0F, 0.0F, false);
        Head.setTextureOffset(0, 0).addCuboid(-246.0F, -41.0F, -1169.0F, 480.0F, 160.0F, 320.0F, 0.0F, false);
        Head.setTextureOffset(0, 0).addCuboid(-246.0F, -241.0F, -1169.0F, 40.0F, 40.0F, 200.0F, 0.0F, false);
        Head.setTextureOffset(0, 0).addCuboid(194.0F, -241.0F, -1169.0F, 40.0F, 40.0F, 200.0F, 0.0F, false);

        bb_main = new ModelPart(this);
        bb_main.setPivot(0.0F, 24.0F, 0.0F);
        bb_main.setTextureOffset(0, 0).addCuboid(-250.1F, -168.1F, -1173.9F, 488.0F, 328.0F, 968.0F, 0.1F, false);
        bb_main.setTextureOffset(0, 0).addCuboid(-210.1F, -128.1F, -213.9F, 408.0F, 248.0F, 488.0F, 0.1F, false);
        bb_main.setTextureOffset(0, 0).addCuboid(-254.2F, -132.2F, -217.8F, 496.0F, 136.0F, 536.0F, 0.2F, false);
        bb_main.setTextureOffset(0, 0).addCuboid(-286.0F, -204.0F, -690.0F, 560.0F, 160.0F, 520.0F, 0.0F, false);
    }

    @Override
    public void setAngles(NebuwhaleEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
        //previously the render function, render code was moved to a method below
    }
    @Override
    public void render(MatrixStack matrixStack, VertexConsumer	buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){

        tail.render(matrixStack, buffer, packedLight, packedOverlay);
        belly.render(matrixStack, buffer, packedLight, packedOverlay);
        Head.render(matrixStack, buffer, packedLight, packedOverlay);
        bb_main.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    public void setRotationAngle(ModelPart bone, float x, float y, float z) {
        bone.pitch = x;
        bone.yaw = y;
        bone.roll = z;
    }

}
