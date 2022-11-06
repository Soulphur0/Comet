// Made with Blockbench 3.7.5
// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports

package com.github.SoulArts.dimensionalAlloys.armorModel.endbriteArmor;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.CrossbowPosing;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;

import static net.minecraft.client.render.entity.model.BipedEntityModel.ArmPose.SPYGLASS;

@Environment(EnvType.CLIENT)
public class EndbriteArmorModel2<T extends LivingEntity> extends BipedEntityModel<T> {
    private final ModelPart head;
    private final ModelPart hat;
    private final ModelPart antennae;
    private final ModelPart body;
    private final ModelPart left_arm;
    private final ModelPart right_arm;
    private final ModelPart left_leg;
    private final ModelPart right_leg;

    public EndbriteArmorModel2(ModelPart root) {
        super(root);
        this.head = root.getChild("head");
        this.antennae = root.getChild("antennae");
        this.body = root.getChild("body");
        this.left_arm = root.getChild("left_arm");
        this.right_arm = root.getChild("right_arm");

        this.hat = root.getChild("hat");
        this.left_leg = root.getChild("left_leg");
        this.right_leg = root.getChild("right_leg");
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

        ModelPartData left_arm = modelPartData.addChild("left_arm", ModelPartBuilder.create().uv(24, 25).mirrored().cuboid(-1.0f, -2.0f, -2.0f, 4.0f, 12.0f, 4.0f, new Dilation(1.0F)).mirrored(false)
        .uv(32, 10).cuboid(4.5f, -2.0f, 1.0f, 4.0F, 5.0F, 1.0F, new Dilation(1.0F)), ModelTransform.pivot(5.0f, 2.0f, 0.0f));

        ModelPartData right_arm = modelPartData.addChild("right_arm", ModelPartBuilder.create().uv(24, 25).cuboid(-3.0f, -2.0f, -2.0f, 4.0f, 12.0f, 4.0f, new Dilation(1.0F))
        .uv(32, 10).mirrored().cuboid(-7.5f, -2.0f, 1.0f, 3.0F, 4.0F, 1.0F, new Dilation(1.0F)).mirrored(false), ModelTransform.pivot(-5.0f, 2.0f, 0.0f));

        // --

        ModelPartData hat = modelPartData.addChild("hat", ModelPartBuilder.create().uv(0, 0).cuboid(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        ModelPartData left_leg = modelPartData.addChild("left_leg", ModelPartBuilder.create().uv(0, 0).cuboid(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        ModelPartData right_leg = modelPartData.addChild("right_leg", ModelPartBuilder.create().uv(0, 0).cuboid(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        // --

        return TexturedModelData.of(modelData, 32, 24);
    }

    @Override
    public void setAngles(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        if (entity instanceof ArmorStandEntity armorStandEntity){
            setAnglesForArmorStand(armorStandEntity);
            return;
        }

        setAnglesForLivingEntity(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        head.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        //antennae.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        body.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        left_arm.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        right_arm.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
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

        this.left_arm.pitch = (float)Math.PI / 180 * armorStandEntity.getLeftArmRotation().getPitch();
        this.left_arm.yaw = (float)Math.PI / 180 * armorStandEntity.getLeftArmRotation().getYaw();
        this.left_arm.roll = (float)Math.PI / 180 * armorStandEntity.getLeftArmRotation().getRoll();

        this.right_arm.pitch = (float)Math.PI / 180 * armorStandEntity.getRightArmRotation().getPitch();
        this.right_arm.yaw = (float)Math.PI / 180 * armorStandEntity.getRightArmRotation().getYaw();
        this.right_arm.roll = (float)Math.PI / 180 * armorStandEntity.getRightArmRotation().getRoll();
    }

    // * Method for posing the armor model extracted from BipedEntityModel

    public BipedEntityModel.ArmPose leftArmPose = BipedEntityModel.ArmPose.EMPTY;
    public BipedEntityModel.ArmPose rightArmPose = BipedEntityModel.ArmPose.EMPTY;
    public float leaningPitch;

    private void setAnglesForLivingEntity(T livingEntity, float f, float g, float h, float i, float j){
        boolean bl3;
        boolean bl = ((LivingEntity)livingEntity).getRoll() > 4;
        boolean bl2 = ((LivingEntity)livingEntity).isInSwimmingPose();
        this.head.yaw = i * ((float)Math.PI / 180);
        this.head.pitch = bl ? -0.7853982f : (this.leaningPitch > 0.0f ? (bl2 ? this.lerpAngle(this.leaningPitch, this.head.pitch, -0.7853982f) : this.lerpAngle(this.leaningPitch, this.head.pitch, j * ((float)Math.PI / 180))) : j * ((float)Math.PI / 180));
        this.body.yaw = 0.0f;
        this.right_arm.pivotZ = 0.0f;
        this.right_arm.pivotX = -5.0f;
        this.left_arm.pivotZ = 0.0f;
        this.left_arm.pivotX = 5.0f;
        float k = 1.0f;
        if (bl) {
            k = (float)((Entity)livingEntity).getVelocity().lengthSquared();
            k /= 0.2f;
            k *= k * k;
        }
        if (k < 1.0f) {
            k = 1.0f;
        }
        this.right_arm.pitch = MathHelper.cos(f * 0.6662f + (float)Math.PI) * 2.0f * g * 0.5f / k;
        this.left_arm.pitch = MathHelper.cos(f * 0.6662f) * 2.0f * g * 0.5f / k;
        this.right_arm.roll = 0.0f;
        this.left_arm.roll = 0.0f;

        this.right_leg.pitch = MathHelper.cos(f * 0.6662f) * 1.4f * g / k;
        this.left_leg.pitch = MathHelper.cos(f * 0.6662f + (float)Math.PI) * 1.4f * g / k;
        this.right_leg.yaw = 0.0f;
        this.left_leg.yaw = 0.0f;
        this.right_leg.roll = 0.0f;
        this.left_leg.roll = 0.0f;
        if (this.riding) {
            this.rightArm.pitch += -0.62831855f;
            this.leftArm.pitch += -0.62831855f;
            this.right_leg.pitch = -1.4137167f;
            this.right_leg.yaw = 0.31415927f;
            this.right_leg.roll = 0.07853982f;
            this.left_leg.pitch = -1.4137167f;
            this.left_leg.yaw = -0.31415927f;
            this.left_leg.roll = -0.07853982f;
        }

        this.right_arm.yaw = 0.0f;
        this.left_arm.yaw = 0.0f;
        boolean bl4 = bl3 = ((LivingEntity)livingEntity).getMainArm() == Arm.RIGHT;
        if (((LivingEntity)livingEntity).isUsingItem()) {
            boolean bl5 = bl4 = ((LivingEntity)livingEntity).getActiveHand() == Hand.MAIN_HAND;
            if (bl4 == bl3) {
                this.positionRightArm(livingEntity);
            } else {
                this.positionLeftArm((livingEntity));
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
            this.right_arm.pitch += 0.4f;
            this.left_arm.pitch += 0.4f;
            this.right_leg.pivotZ = 4.0f;
            this.left_leg.pivotZ = 4.0f;
            this.right_leg.pivotY = 12.2f;
            this.left_leg.pivotY = 12.2f;
            this.head.pivotY = 4.2f;
            this.body.pivotY = 3.2f;
            this.left_arm.pivotY = 5.2f;
            this.right_arm.pivotY = 5.2f;
        } else {
            this.body.pitch = 0.0f;
            this.right_leg.pivotZ = 0.1f;
            this.left_leg.pivotZ = 0.1f;
            this.right_leg.pivotY = 12.0f;
            this.left_leg.pivotY = 12.0f;
            this.head.pivotY = 0.0f;
            this.body.pivotY = 0.0f;
            this.left_arm.pivotY = 2.0f;
            this.right_arm.pivotY = 2.0f;
        }
        if (this.rightArmPose != SPYGLASS) {
            CrossbowPosing.swingArm(this.right_arm, h, 1.0f);
        }
        if (this.leftArmPose != SPYGLASS) {
            CrossbowPosing.swingArm(this.left_arm, h, -1.0f);
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
                    this.left_arm.pitch = this.lerpAngle(n, this.left_arm.pitch, 0.0f);
                    this.right_arm.pitch = MathHelper.lerp(m, this.right_arm.pitch, 0.0f);
                    this.left_arm.yaw = this.lerpAngle(n, this.left_arm.yaw, (float)Math.PI);
                    this.right_arm.yaw = MathHelper.lerp(m, this.right_arm.yaw, (float)Math.PI);
                    this.left_arm.roll = this.lerpAngle(n, this.left_arm.roll, (float)Math.PI + 1.8707964f * this.method_2807(l) / this.method_2807(14.0f));
                    this.right_arm.roll = MathHelper.lerp(m, this.right_arm.roll, (float)Math.PI - 1.8707964f * this.method_2807(l) / this.method_2807(14.0f));
                } else if (l >= 14.0f && l < 22.0f) {
                    o = (l - 14.0f) / 8.0f;
                    this.left_arm.pitch = this.lerpAngle(n, this.left_arm.pitch, 1.5707964f * o);
                    this.right_arm.pitch = MathHelper.lerp(m, this.right_arm.pitch, 1.5707964f * o);
                    this.left_arm.yaw = this.lerpAngle(n, this.left_arm.yaw, (float)Math.PI);
                    this.right_arm.yaw = MathHelper.lerp(m, this.right_arm.yaw, (float)Math.PI);
                    this.left_arm.roll = this.lerpAngle(n, this.left_arm.roll, 5.012389f - 1.8707964f * o);
                    this.right_arm.roll = MathHelper.lerp(m, this.right_arm.roll, 1.2707963f + 1.8707964f * o);
                } else if (l >= 22.0f && l < 26.0f) {
                    o = (l - 22.0f) / 4.0f;
                    this.left_arm.pitch = this.lerpAngle(n, this.left_arm.pitch, 1.5707964f - 1.5707964f * o);
                    this.right_arm.pitch = MathHelper.lerp(m, this.right_arm.pitch, 1.5707964f - 1.5707964f * o);
                    this.left_arm.yaw = this.lerpAngle(n, this.left_arm.yaw, (float)Math.PI);
                    this.right_arm.yaw = MathHelper.lerp(m, this.right_arm.yaw, (float)Math.PI);
                    this.left_arm.roll = this.lerpAngle(n, this.left_arm.roll, (float)Math.PI);
                    this.right_arm.roll = MathHelper.lerp(m, this.right_arm.roll, (float)Math.PI);
                }
            }
            o = 0.3f;
            float p = 0.33333334f;
            this.left_leg.pitch = MathHelper.lerp(this.leaningPitch, this.left_leg.pitch, 0.3f * MathHelper.cos(f * 0.33333334f + (float)Math.PI));
            this.right_leg.pitch = MathHelper.lerp(this.leaningPitch, this.right_leg.pitch, 0.3f * MathHelper.cos(f * 0.33333334f));
        }
        this.hat.copyTransform(this.head);
    }

    private Arm getPreferredArm(LivingEntity livingEntity) {
        Arm arm = (livingEntity).getMainArm();
        return (livingEntity).preferredHand == Hand.MAIN_HAND ? arm : arm.getOpposite();
    }

    private float method_2807(float f) {
        return -65.0f * f + f * f;
    }

    private void positionRightArm(LivingEntity entity) {
        switch (this.rightArmPose) {
            case EMPTY: {
                this.right_arm.yaw = 0.0f;
                break;
            }
            case BLOCK: {
                this.right_arm.pitch = this.right_arm.pitch * 0.5f - 0.9424779f;
                this.right_arm.yaw = -0.5235988f;
                break;
            }
            case ITEM: {
                this.right_arm.pitch = this.right_arm.pitch * 0.5f - 0.31415927f;
                this.right_arm.yaw = 0.0f;
                break;
            }
            case THROW_SPEAR: {
                this.right_arm.pitch = this.right_arm.pitch * 0.5f - (float)Math.PI;
                this.right_arm.yaw = 0.0f;
                break;
            }
            case BOW_AND_ARROW: {
                this.right_arm.yaw = -0.1f + this.head.yaw;
                this.left_arm.yaw = 0.1f + this.head.yaw + 0.4f;
                this.right_arm.pitch = -1.5707964f + this.head.pitch;
                this.left_arm.pitch = -1.5707964f + this.head.pitch;
                break;
            }
            case CROSSBOW_CHARGE: {
                CrossbowPosing.charge(this.right_arm, this.left_arm, entity, true);
                break;
            }
            case CROSSBOW_HOLD: {
                CrossbowPosing.hold(this.right_arm, this.left_arm, this.head, true);
                break;
            }
            case SPYGLASS: {
                this.right_arm.pitch = MathHelper.clamp(this.head.pitch - 1.9198622f - (((Entity)entity).isInSneakingPose() ? 0.2617994f : 0.0f), -2.4f, 3.3f);
                this.right_arm.yaw = this.head.yaw - 0.2617994f;
                break;
            }
            case TOOT_HORN: {
                this.right_arm.pitch = MathHelper.clamp(this.head.pitch, -1.2f, 1.2f) - 1.4835298f;
                this.right_arm.yaw = this.head.yaw - 0.5235988f;
            }
        }
    }

    private void positionLeftArm(LivingEntity entity) {
        switch (this.leftArmPose) {
            case EMPTY: {
                this.left_arm.yaw = 0.0f;
                break;
            }
            case BLOCK: {
                this.left_arm.pitch = this.left_arm.pitch * 0.5f - 0.9424779f;
                this.left_arm.yaw = 0.5235988f;
                break;
            }
            case ITEM: {
                this.left_arm.pitch = this.left_arm.pitch * 0.5f - 0.31415927f;
                this.left_arm.yaw = 0.0f;
                break;
            }
            case THROW_SPEAR: {
                this.left_arm.pitch = this.left_arm.pitch * 0.5f - (float)Math.PI;
                this.left_arm.yaw = 0.0f;
                break;
            }
            case BOW_AND_ARROW: {
                this.right_arm.yaw = -0.1f + this.head.yaw - 0.4f;
                this.left_arm.yaw = 0.1f + this.head.yaw;
                this.right_arm.pitch = -1.5707964f + this.head.pitch;
                this.left_arm.pitch = -1.5707964f + this.head.pitch;
                break;
            }
            case CROSSBOW_CHARGE: {
                CrossbowPosing.charge(this.right_arm, this.left_arm, entity, false);
                break;
            }
            case CROSSBOW_HOLD: {
                CrossbowPosing.hold(this.right_arm, this.left_arm, this.head, false);
                break;
            }
            case SPYGLASS: {
                this.left_arm.pitch = MathHelper.clamp(this.head.pitch - 1.9198622f - (((Entity)entity).isInSneakingPose() ? 0.2617994f : 0.0f), -2.4f, 3.3f);
                this.left_arm.yaw = this.head.yaw + 0.2617994f;
                break;
            }
            case TOOT_HORN: {
                this.left_arm.pitch = MathHelper.clamp(this.head.pitch, -1.2f, 1.2f) - 1.4835298f;
                this.left_arm.yaw = this.head.yaw + 0.5235988f;
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
