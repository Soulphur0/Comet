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
import net.minecraft.client.render.entity.model.AbstractZombieModel;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.CrossbowPosing;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.mob.*;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;

import static net.minecraft.client.render.entity.model.BipedEntityModel.ArmPose.SPYGLASS;

@Environment(EnvType.CLIENT)
public class EndbriteArmorModel2<T extends LivingEntity> extends BipedEntityModel<T> {

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

    public EndbriteArmorModel2(ModelPart root) {
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

        // _ Vanilla biped entity model parts.

        ModelPartData hat = modelPartData.addChild("hat", ModelPartBuilder.create().uv(0, 0).cuboid(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData head = modelPartData.addChild("head", ModelPartBuilder.create().uv(8, 38).cuboid(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(1.0F))
        .uv(10, 57).cuboid(-4.0F, -10.0F, -4.0F, 8.0F, 1.0F, 1.0F, new Dilation(1.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create().uv(8, 2).cuboid(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new Dilation(1.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData left_arm = modelPartData.addChild("left_arm", ModelPartBuilder.create().uv(24, 25).mirrored().cuboid(-1.0f, -2.0f, -2.0f, 4.0f, 12.0f, 4.0f, new Dilation(1.0F)).mirrored(false)
        .uv(32, 10).cuboid(4.5f, -2.0f, 1.0f, 4.0F, 5.0F, 1.0F, new Dilation(1.0F)), ModelTransform.pivot(5.0f, 2.0f, 0.0f));

        ModelPartData right_arm = modelPartData.addChild("right_arm", ModelPartBuilder.create().uv(24, 25).cuboid(-3.0f, -2.0f, -2.0f, 4.0f, 12.0f, 4.0f, new Dilation(1.0F))
        .uv(32, 10).mirrored().cuboid(-7.5f, -2.0f, 1.0f, 3.0F, 4.0F, 1.0F, new Dilation(1.0F)).mirrored(false), ModelTransform.pivot(-5.0f, 2.0f, 0.0f));

        ModelPartData left_leg = modelPartData.addChild("left_leg", ModelPartBuilder.create().uv(40, 22).mirrored().cuboid(-2.0F, -0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.6F)).mirrored(false)
        .uv(52, 8).cuboid(2.5F, -0.0F, 0.0F, 1.0F, 7.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(1.9F, 12.0F, 0.0F));

        ModelPartData right_leg = modelPartData.addChild("right_leg", ModelPartBuilder.create().uv(40, 22).cuboid(-2.0F, -0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.6F))
        .uv(52, 8).cuboid(-3.5F, -0.0F, 0.0F, 1.0F, 7.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(-1.9F, 12.0F, 0.0F));

        // _ Endbrite armor parts and different vanilla part setup

        ModelPartData inner_body = modelPartData.addChild("inner_body", ModelPartBuilder.create().uv(32, 6).cuboid(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new Dilation(0.6F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData right_foot = modelPartData.addChild("right_foot", ModelPartBuilder.create().uv(8, 22).cuboid(-2.0F, -0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(1.0F)), ModelTransform.pivot(-1.9F, 12.0F, 0.0F));

        ModelPartData left_foot = modelPartData.addChild("left_foot", ModelPartBuilder.create().uv(8, 22).mirrored().cuboid(-2.0F, -0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(1.0F)).mirrored(false), ModelTransform.pivot(1.9F, 12.0F, 0.0F));

        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public void setAngles(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.leaningPitch = entity.getLeaningPitch(ageInTicks);

        // _ Setangles for all common entity elements
        setAnglesForLivingEntity(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

        // _ Setangles for armor stand body parts
        if (entity instanceof ArmorStandEntity armorStandEntity){
            setAnglesForArmorStand(armorStandEntity);
            return;
        }

        // _ Setangles for skeleton arms
        if (entity instanceof SkeletonEntity || entity instanceof WitherSkeletonEntity || entity instanceof StrayEntity){
            setAnglesForSkeleton(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        }

        // _ Setangles for zombie arms
        if (entity instanceof ZombieEntity){
            setAnglesForZombie(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        }

        if (entity instanceof DrownedEntity){
            setAnglesForDrowned(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
            animateDrownedModel(entity, limbSwing, limbSwingAmount, ageInTicks);
        }
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

    // * Extras --------------------------------------------------------------------------------------------------------

    // * Method for posing the armor model extracted from BipedEntityModel, handles common body poses

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
        this.inner_body.yaw = 0.0f;
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
        this.right_foot.pitch = MathHelper.cos(f * 0.6662f) * 1.4f * g / k;
        this.left_leg.pitch = MathHelper.cos(f * 0.6662f + (float)Math.PI) * 1.4f * g / k;
        this.left_foot.pitch = MathHelper.cos(f * 0.6662f + (float)Math.PI) * 1.4f * g / k;
        this.right_leg.yaw = 0.0f;
        this.right_foot.yaw = 0.0f;
        this.left_leg.yaw = 0.0f;
        this.left_foot.yaw = 0.0f;
        this.right_leg.roll = 0.0f;
        this.right_foot.roll = 0.0f;
        this.left_leg.roll = 0.0f;
        this.left_foot.roll = 0.0f;
        if (this.riding) {
            this.right_arm.pitch += -0.62831855f;
            this.left_arm.pitch += -0.62831855f;
            this.right_leg.pitch = -1.4137167f;
            this.right_leg.yaw = 0.31415927f;
            this.right_leg.roll = 0.07853982f;
            this.right_foot.pitch = -1.4137167f;
            this.right_foot.yaw = 0.31415927f;
            this.right_foot.roll = 0.07853982f;
            this.left_leg.pitch = -1.4137167f;
            this.left_leg.yaw = -0.31415927f;
            this.left_leg.roll = -0.07853982f;
            this.left_foot.pitch = -1.4137167f;
            this.left_foot.yaw = -0.31415927f;
            this.left_foot.roll = -0.07853982f;
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
            this.inner_body.pitch = 0.5f;
            this.right_arm.pitch += 0.4f;
            this.left_arm.pitch += 0.4f;
            this.right_leg.pivotZ = 4.0f;
            this.right_foot.pivotZ = 4.0f;
            this.left_leg.pivotZ = 4.0f;
            this.left_foot.pivotZ = 4.0f;
            this.right_leg.pivotY = 12.2f;
            this.right_foot.pivotY = 12.2f;
            this.left_leg.pivotY = 12.2f;
            this.left_foot.pivotY = 12.2f;
            this.head.pivotY = 4.2f;
            this.body.pivotY = 3.2f;
            this.inner_body.pivotY = 3.2f;
            this.left_arm.pivotY = 5.2f;
            this.right_arm.pivotY = 5.2f;
        } else {
            this.body.pitch = 0.0f;
            this.inner_body.pitch = 0.0f;
            this.right_leg.pivotZ = 0.1f;
            this.right_foot.pivotZ = 0.1f;
            this.left_leg.pivotZ = 0.1f;
            this.left_foot.pivotZ = 0.1f;
            this.right_leg.pivotY = 12.0f;
            this.right_foot.pivotY = 12.0f;
            this.left_leg.pivotY = 12.0f;
            this.left_foot.pivotY = 12.0f;
            this.head.pivotY = 0.0f;
            this.body.pivotY = 0.0f;
            this.inner_body.pivotY = 0.0f;
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
            this.left_foot.pitch = MathHelper.lerp(this.leaningPitch, this.left_foot.pitch, 0.3f * MathHelper.cos(f * 0.33333334f + (float)Math.PI));
            this.right_leg.pitch = MathHelper.lerp(this.leaningPitch, this.right_leg.pitch, 0.3f * MathHelper.cos(f * 0.33333334f));
            this.right_foot.pitch = MathHelper.lerp(this.leaningPitch, this.right_foot.pitch, 0.3f * MathHelper.cos(f * 0.33333334f));
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

    // $ Set angles for the different biped mobs
    private void setAnglesForArmorStand(ArmorStandEntity armorStandEntity){
        // - Head related elements.
        this.head.pitch = (float)Math.PI / 180 * armorStandEntity.getHeadRotation().getPitch();
        this.head.yaw = (float)Math.PI / 180 * armorStandEntity.getHeadRotation().getYaw();
        this.head.roll = (float)Math.PI / 180 * armorStandEntity.getHeadRotation().getRoll();

        // - Body related elements.
        this.body.pitch = (float)Math.PI / 180 * armorStandEntity.getBodyRotation().getPitch();
        this.body.yaw = (float)Math.PI / 180 * armorStandEntity.getBodyRotation().getYaw();
        this.body.roll = (float)Math.PI / 180 * armorStandEntity.getBodyRotation().getRoll();

        this.inner_body.pitch = (float)Math.PI / 180 * armorStandEntity.getBodyRotation().getPitch();
        this.inner_body.yaw = (float)Math.PI / 180 * armorStandEntity.getBodyRotation().getYaw();
        this.inner_body.roll = (float)Math.PI / 180 * armorStandEntity.getBodyRotation().getRoll();

        // - Arm related elements
        this.left_arm.pitch = (float)Math.PI / 180 * armorStandEntity.getLeftArmRotation().getPitch();
        this.left_arm.yaw = (float)Math.PI / 180 * armorStandEntity.getLeftArmRotation().getYaw();
        this.left_arm.roll = (float)Math.PI / 180 * armorStandEntity.getLeftArmRotation().getRoll();

        this.right_arm.pitch = (float)Math.PI / 180 * armorStandEntity.getRightArmRotation().getPitch();
        this.right_arm.yaw = (float)Math.PI / 180 * armorStandEntity.getRightArmRotation().getYaw();
        this.right_arm.roll = (float)Math.PI / 180 * armorStandEntity.getRightArmRotation().getRoll();

        // - Leg related elements.
        this.left_leg.pitch = (float)Math.PI / 180 * armorStandEntity.getLeftLegRotation().getPitch();
        this.left_leg.yaw = (float)Math.PI / 180 * armorStandEntity.getLeftLegRotation().getYaw();
        this.left_leg.roll = (float)Math.PI / 180 * armorStandEntity.getLeftLegRotation().getRoll();

        this.right_leg.pitch = (float)Math.PI / 180 * armorStandEntity.getRightLegRotation().getPitch();
        this.right_leg.yaw = (float)Math.PI / 180 * armorStandEntity.getRightLegRotation().getYaw();
        this.right_leg.roll = (float)Math.PI / 180 * armorStandEntity.getRightLegRotation().getRoll();

        this.left_foot.pitch = (float)Math.PI / 180 * armorStandEntity.getLeftLegRotation().getPitch();
        this.left_foot.yaw = (float)Math.PI / 180 * armorStandEntity.getLeftLegRotation().getYaw();
        this.left_foot.roll = (float)Math.PI / 180 * armorStandEntity.getLeftLegRotation().getRoll();

        this.right_foot.pitch = (float)Math.PI / 180 * armorStandEntity.getRightLegRotation().getPitch();
        this.right_foot.yaw = (float)Math.PI / 180 * armorStandEntity.getRightLegRotation().getYaw();
        this.right_foot.roll = (float)Math.PI / 180 * armorStandEntity.getRightLegRotation().getRoll();
    }

    private void setAnglesForSkeleton(T mobEntity, float f, float g, float h, float i, float j){
        super.setAngles(mobEntity, f, g, h, i, j);
        ItemStack itemStack = ((LivingEntity)mobEntity).getMainHandStack();
        if (((MobEntity)mobEntity).isAttacking() && (itemStack.isEmpty() || !itemStack.isOf(Items.BOW))) {
            float k = MathHelper.sin(this.handSwingProgress * (float)Math.PI);
            float l = MathHelper.sin((1.0f - (1.0f - this.handSwingProgress) * (1.0f - this.handSwingProgress)) * (float)Math.PI);
            this.rightArm.roll = 0.0f;
            this.leftArm.roll = 0.0f;
            this.rightArm.yaw = -(0.1f - k * 0.6f);
            this.leftArm.yaw = 0.1f - k * 0.6f;
            this.rightArm.pitch = -1.5707964f;
            this.leftArm.pitch = -1.5707964f;
            this.rightArm.pitch -= k * 1.2f - l * 0.4f;
            this.leftArm.pitch -= k * 1.2f - l * 0.4f;
            CrossbowPosing.swingArms(this.rightArm, this.leftArm, h);
        }
    }

    private void setAnglesForZombie(T mobEntity, float f, float g, float h, float i, float j){
        super.setAngles(mobEntity, f, g, h, i, j);
        CrossbowPosing.meleeAttack(this.leftArm, this.rightArm, ((ZombieEntity)mobEntity).isAttacking(), this.handSwingProgress, h);
    }

    private void setAnglesForDrowned(T mobEntity, float f, float g, float h, float i, float j){
        super.setAngles(mobEntity, f, g, h, i, j);
        if (this.leftArmPose == BipedEntityModel.ArmPose.THROW_SPEAR) {
            this.left_arm.pitch = this.left_arm.pitch * 0.5f - (float)Math.PI;
            this.left_arm.yaw = 0.0f;
        }
        if (this.rightArmPose == BipedEntityModel.ArmPose.THROW_SPEAR) {
            this.right_arm.pitch = this.right_arm.pitch * 0.5f - (float)Math.PI;
            this.right_arm.yaw = 0.0f;
        }

        if (this.leaningPitch > 0.0f) {
            this.right_arm.pitch = this.lerpAngle(this.leaningPitch, this.right_arm.pitch, -2.5132742f) + this.leaningPitch * 0.35f * MathHelper.sin(0.1f * h);
            this.left_arm.pitch = this.lerpAngle(this.leaningPitch, this.left_arm.pitch, -2.5132742f) - this.leaningPitch * 0.35f * MathHelper.sin(0.1f * h);
            this.right_arm.roll = this.lerpAngle(this.leaningPitch, this.right_arm.roll, -0.15f);
            this.left_arm.roll = this.lerpAngle(this.leaningPitch, this.left_arm.roll, 0.15f);
            this.leftLeg.pitch -= this.leaningPitch * 0.55f * MathHelper.sin(0.1f * h);
            this.rightLeg.pitch += this.leaningPitch * 0.55f * MathHelper.sin(0.1f * h);
            this.head.pitch = 0.0f;
        }
    }

    public void animateDrownedModel(T zombieEntity, float f, float g, float h) {
        this.rightArmPose = BipedEntityModel.ArmPose.EMPTY;
        this.leftArmPose = BipedEntityModel.ArmPose.EMPTY;
        ItemStack itemStack = ((LivingEntity)zombieEntity).getStackInHand(Hand.MAIN_HAND);
        if (itemStack.isOf(Items.TRIDENT) && ((MobEntity)zombieEntity).isAttacking()) {
            if (((MobEntity)zombieEntity).getMainArm() == Arm.RIGHT) {
                this.rightArmPose = BipedEntityModel.ArmPose.THROW_SPEAR;
            } else {
                this.leftArmPose = BipedEntityModel.ArmPose.THROW_SPEAR;
            }
        }
        super.animateModel(zombieEntity, f, g, h);
    }
}
