// Made with Blockbench 3.7.5
// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports
/*
package com.github.SoulArts.dimensionalAlloys.armorModel.endbriteArmor;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.CrossbowPosing;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;

import static net.minecraft.client.render.entity.model.BipedEntityModel.ArmPose.*;

@Environment(EnvType.CLIENT)
public class EndbriteArmorModel extends EntityModel<Entity> {

    private final ModelPart head;
    private final ModelPart antennae;
    private final ModelPart body;
    private final ModelPart leftArm;
    private final ModelPart rightArm;

    public EndbriteArmorModel(ModelPart root) {
        this.head = root.getChild("head");
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
        ModelPartData head = modelPartData.addChild("head", ModelPartBuilder.create().uv(8, 38).cuboid(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(1.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

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
            setAnglesForArmorStand(armorStandEntity);
            return;
        }

        if (entity instanceof LivingEntity livingEntity){
            setAnglesForLivingEntity(livingEntity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        }
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        head.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        antennae.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
    }

    // * Extras --------------------------------------------------------------------------------------------------------

    private void setAnglesForArmorStand(ArmorStandEntity armorStandEntity){
        this.head.pitch = (float)Math.PI / 180 * armorStandEntity.getHeadRotation().getPitch();
        this.head.yaw = (float)Math.PI / 180 * armorStandEntity.getHeadRotation().getYaw();
        this.head.roll = (float)Math.PI / 180 * armorStandEntity.getHeadRotation().getRoll();

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

    // * Method for posing the armor model extracted from BipedEntityModel

    public BipedEntityModel.ArmPose leftArmPose = BipedEntityModel.ArmPose.EMPTY;
    public BipedEntityModel.ArmPose rightArmPose = BipedEntityModel.ArmPose.EMPTY;
    public float leaningPitch;

    private void setAnglesForLivingEntity(LivingEntity livingEntity, float f, float g, float h, float i, float j){
        boolean bl3;
        boolean bl = ((LivingEntity)livingEntity).getRoll() > 4;
        boolean bl2 = ((LivingEntity)livingEntity).isInSwimmingPose();
        this.head.yaw = i * ((float)Math.PI / 180);
        this.head.pitch = bl ? -0.7853982f : (this.leaningPitch > 0.0f ? (bl2 ? this.lerpAngle(this.leaningPitch, this.head.pitch, -0.7853982f) : this.lerpAngle(this.leaningPitch, this.head.pitch, j * ((float)Math.PI / 180))) : j * ((float)Math.PI / 180));
        this.body.yaw = 0.0f;
        this.rightArm.pivotZ = 0.0f;
        this.rightArm.pivotX = -5.0f;
        this.leftArm.pivotZ = 0.0f;
        this.leftArm.pivotX = 5.0f;
        float k = 1.0f;
        if (bl) {
            k = (float)((Entity)livingEntity).getVelocity().lengthSquared();
            k /= 0.2f;
            k *= k * k;
        }
        if (k < 1.0f) {
            k = 1.0f;
        }
        this.rightArm.pitch = MathHelper.cos(f * 0.6662f + (float)Math.PI) * 2.0f * g * 0.5f / k;
        this.leftArm.pitch = MathHelper.cos(f * 0.6662f) * 2.0f * g * 0.5f / k;
        this.rightArm.roll = 0.0f;
        this.leftArm.roll = 0.0f;
        /*
        this.rightLeg.pitch = MathHelper.cos(f * 0.6662f) * 1.4f * g / k;
        this.leftLeg.pitch = MathHelper.cos(f * 0.6662f + (float)Math.PI) * 1.4f * g / k;
        this.rightLeg.yaw = 0.0f;
        this.leftLeg.yaw = 0.0f;
        this.rightLeg.roll = 0.0f;
        this.leftLeg.roll = 0.0f;
        if (this.riding) {
            this.rightArm.pitch += -0.62831855f;
            this.leftArm.pitch += -0.62831855f;
            this.rightLeg.pitch = -1.4137167f;
            this.rightLeg.yaw = 0.31415927f;
            this.rightLeg.roll = 0.07853982f;
            this.leftLeg.pitch = -1.4137167f;
            this.leftLeg.yaw = -0.31415927f;
            this.leftLeg.roll = -0.07853982f;
        }

        this.rightArm.yaw = 0.0f;
        this.leftArm.yaw = 0.0f;
        boolean bl4 = bl3 = ((LivingEntity)livingEntity).getMainArm() == Arm.RIGHT;
        if (((LivingEntity)livingEntity).isUsingItem()) {
            boolean bl5 = bl4 = ((LivingEntity)livingEntity).getActiveHand() == Hand.MAIN_HAND;
            if (bl4 == bl3) {
                this.positionRightArm(livingEntity);
            } else {
                this.positionLeftArm(livingEntity);
            }
        } else {
            boolean bl6 = bl4 = bl3 ? this.leftArmPose.isTwoHanded() : this.rightArmPose.isTwoHanded();
            if (bl3 != bl4) {
                this.positionLeftArm(livingEntity);
                this.positionRightArm(livingEntity);
            } else {
                this.positionRightArm(livingEntity);
                this.positionLeftArm(livingEntity);
            }
        }
        this.animateArms(livingEntity, h);
        if (this.sneaking) {
            this.body.pitch = 0.5f;
            this.rightArm.pitch += 0.4f;
            this.leftArm.pitch += 0.4f;
            this.rightLeg.pivotZ = 4.0f;
            this.leftLeg.pivotZ = 4.0f;
            this.rightLeg.pivotY = 12.2f;
            this.leftLeg.pivotY = 12.2f;
            this.head.pivotY = 4.2f;
            this.body.pivotY = 3.2f;
            this.leftArm.pivotY = 5.2f;
            this.rightArm.pivotY = 5.2f;
        } else {
            this.body.pitch = 0.0f;
            this.rightLeg.pivotZ = 0.1f;
            this.leftLeg.pivotZ = 0.1f;
            this.rightLeg.pivotY = 12.0f;
            this.leftLeg.pivotY = 12.0f;
            this.head.pivotY = 0.0f;
            this.body.pivotY = 0.0f;
            this.leftArm.pivotY = 2.0f;
            this.rightArm.pivotY = 2.0f;
        }
        if (this.rightArmPose != SPYGLASS) {
            CrossbowPosing.swingArm(this.rightArm, h, 1.0f);
        }
        if (this.leftArmPose != SPYGLASS) {
            CrossbowPosing.swingArm(this.leftArm, h, -1.0f);
        }
        if (this.leaningPitch > 0.0f) {
            float o;
            float n;
            float l = f % 26.0f;
            Arm arm = this.getPreferredArm(livingEntity);
            float m = arm == Arm.RIGHT && this.handSwingProgress > 0.0f ? 0.0f : this.leaningPitch;
            float f2 = n = arm == Arm.LEFT && this.handSwingProgress > 0.0f ? 0.0f : this.leaningPitch;
            if (!((LivingEntity)livingEntity).isUsingItem()) {
                if (l < 14.0f) {
                    this.leftArm.pitch = this.lerpAngle(n, this.leftArm.pitch, 0.0f);
                    this.rightArm.pitch = MathHelper.lerp(m, this.rightArm.pitch, 0.0f);
                    this.leftArm.yaw = this.lerpAngle(n, this.leftArm.yaw, (float)Math.PI);
                    this.rightArm.yaw = MathHelper.lerp(m, this.rightArm.yaw, (float)Math.PI);
                    this.leftArm.roll = this.lerpAngle(n, this.leftArm.roll, (float)Math.PI + 1.8707964f * this.method_2807(l) / this.method_2807(14.0f));
                    this.rightArm.roll = MathHelper.lerp(m, this.rightArm.roll, (float)Math.PI - 1.8707964f * this.method_2807(l) / this.method_2807(14.0f));
                } else if (l >= 14.0f && l < 22.0f) {
                    o = (l - 14.0f) / 8.0f;
                    this.leftArm.pitch = this.lerpAngle(n, this.leftArm.pitch, 1.5707964f * o);
                    this.rightArm.pitch = MathHelper.lerp(m, this.rightArm.pitch, 1.5707964f * o);
                    this.leftArm.yaw = this.lerpAngle(n, this.leftArm.yaw, (float)Math.PI);
                    this.rightArm.yaw = MathHelper.lerp(m, this.rightArm.yaw, (float)Math.PI);
                    this.leftArm.roll = this.lerpAngle(n, this.leftArm.roll, 5.012389f - 1.8707964f * o);
                    this.rightArm.roll = MathHelper.lerp(m, this.rightArm.roll, 1.2707963f + 1.8707964f * o);
                } else if (l >= 22.0f && l < 26.0f) {
                    o = (l - 22.0f) / 4.0f;
                    this.leftArm.pitch = this.lerpAngle(n, this.leftArm.pitch, 1.5707964f - 1.5707964f * o);
                    this.rightArm.pitch = MathHelper.lerp(m, this.rightArm.pitch, 1.5707964f - 1.5707964f * o);
                    this.leftArm.yaw = this.lerpAngle(n, this.leftArm.yaw, (float)Math.PI);
                    this.rightArm.yaw = MathHelper.lerp(m, this.rightArm.yaw, (float)Math.PI);
                    this.leftArm.roll = this.lerpAngle(n, this.leftArm.roll, (float)Math.PI);
                    this.rightArm.roll = MathHelper.lerp(m, this.rightArm.roll, (float)Math.PI);
                }
            }
            o = 0.3f;
            float p = 0.33333334f;
            this.leftLeg.pitch = MathHelper.lerp(this.leaningPitch, this.leftLeg.pitch, 0.3f * MathHelper.cos(f * 0.33333334f + (float)Math.PI));
            this.rightLeg.pitch = MathHelper.lerp(this.leaningPitch, this.rightLeg.pitch, 0.3f * MathHelper.cos(f * 0.33333334f));
        }
        this.hat.copyTransform(this.head);
    }

    private void positionRightArm(T entity) {
        switch (this.rightArmPose) {
            case EMPTY: {
                this.rightArm.yaw = 0.0f;
                break;
            }
            case BLOCK: {
                this.rightArm.pitch = this.rightArm.pitch * 0.5f - 0.9424779f;
                this.rightArm.yaw = -0.5235988f;
                break;
            }
            case ITEM: {
                this.rightArm.pitch = this.rightArm.pitch * 0.5f - 0.31415927f;
                this.rightArm.yaw = 0.0f;
                break;
            }
            case THROW_SPEAR: {
                this.rightArm.pitch = this.rightArm.pitch * 0.5f - (float)Math.PI;
                this.rightArm.yaw = 0.0f;
                break;
            }
            case BOW_AND_ARROW: {
                this.rightArm.yaw = -0.1f + this.head.yaw;
                this.leftArm.yaw = 0.1f + this.head.yaw + 0.4f;
                this.rightArm.pitch = -1.5707964f + this.head.pitch;
                this.leftArm.pitch = -1.5707964f + this.head.pitch;
                break;
            }
            case CROSSBOW_CHARGE: {
                CrossbowPosing.charge(this.rightArm, this.leftArm, entity, true);
                break;
            }
            case CROSSBOW_HOLD: {
                CrossbowPosing.hold(this.rightArm, this.leftArm, this.head, true);
                break;
            }
            case SPYGLASS: {
                this.rightArm.pitch = MathHelper.clamp(this.head.pitch - 1.9198622f - (((Entity)entity).isInSneakingPose() ? 0.2617994f : 0.0f), -2.4f, 3.3f);
                this.rightArm.yaw = this.head.yaw - 0.2617994f;
                break;
            }
            case TOOT_HORN: {
                this.rightArm.pitch = MathHelper.clamp(this.head.pitch, -1.2f, 1.2f) - 1.4835298f;
                this.rightArm.yaw = this.head.yaw - 0.5235988f;
            }
        }
    }

    private void positionLeftArm(Entity entity) {
        switch (this.leftArmPose) {
            case EMPTY: {
                this.leftArm.yaw = 0.0f;
                break;
            }
            case BLOCK: {
                this.leftArm.pitch = this.leftArm.pitch * 0.5f - 0.9424779f;
                this.leftArm.yaw = 0.5235988f;
                break;
            }
            case ITEM: {
                this.leftArm.pitch = this.leftArm.pitch * 0.5f - 0.31415927f;
                this.leftArm.yaw = 0.0f;
                break;
            }
            case THROW_SPEAR: {
                this.leftArm.pitch = this.leftArm.pitch * 0.5f - (float)Math.PI;
                this.leftArm.yaw = 0.0f;
                break;
            }
            case BOW_AND_ARROW: {
                this.rightArm.yaw = -0.1f + this.head.yaw - 0.4f;
                this.leftArm.yaw = 0.1f + this.head.yaw;
                this.rightArm.pitch = -1.5707964f + this.head.pitch;
                this.leftArm.pitch = -1.5707964f + this.head.pitch;
                break;
            }
            case CROSSBOW_CHARGE: {
                CrossbowPosing.charge(this.rightArm, this.leftArm, entity, false);
                break;
            }
            case CROSSBOW_HOLD: {
                CrossbowPosing.hold(this.rightArm, this.leftArm, this.head, false);
                break;
            }
            case SPYGLASS: {
                this.leftArm.pitch = MathHelper.clamp(this.head.pitch - 1.9198622f - (((Entity)entity).isInSneakingPose() ? 0.2617994f : 0.0f), -2.4f, 3.3f);
                this.leftArm.yaw = this.head.yaw + 0.2617994f;
                break;
            }
            case TOOT_HORN: {
                this.leftArm.pitch = MathHelper.clamp(this.head.pitch, -1.2f, 1.2f) - 1.4835298f;
                this.leftArm.yaw = this.head.yaw + 0.5235988f;
            }
        }
    }

    protected float lerpAngle(float angleOne, float angleTwo, float magnitude) {
        float f = (magnitude - angleTwo) % ((float)Math.PI * 2);
        if (f < (float)(-Math.PI)) {
            f += (float)Math.PI * 2;
        }
        if (f >= (float)Math.PI) {
            f -= (float)Math.PI * 2;
        }
        return angleTwo + angleOne * f;
    }

}
*/