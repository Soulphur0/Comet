package com.github.Soulphur0.mixin.entityRenderer;

import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(LivingEntityRenderer.class)
public class LivingEntityRendererMixin<T extends Entity, M extends EntityModel<T>> {

    // ? Cancel animations if creature is crystallized.
    @Redirect(method="render(Lnet/minecraft/entity/LivingEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", at = @At(value ="INVOKE", target ="Lnet/minecraft/client/render/entity/model/EntityModel;animateModel(Lnet/minecraft/entity/Entity;FFF)V"))
    private void cancelModelAnimations(M instance, T entity, float limbAngle, float limbDistance, float tickDelta){
        if (!entity.isCrystallized())
            instance.animateModel(entity, limbAngle, limbDistance, tickDelta);
    }

    // ? Cancel model rotation if creature is crystallized.
    @ModifyArgs(method="render(Lnet/minecraft/entity/LivingEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", at = @At(value ="INVOKE", target ="Lnet/minecraft/client/render/entity/model/EntityModel;setAngles(Lnet/minecraft/entity/Entity;FFFFF)V"))
    private void freezeModelSetAngles(Args args){
        LivingEntity livingEntity = args.get(0);
        if (livingEntity.isCrystallized()){
            args.set(1, 0f);
            args.set(2, 0f);
            args.set(3, 0f);
            args.set(4, 0f);
            args.set(5, 0f);
        }
    }

    // ? Freeze model yaw rotation in place if entity is crystallized.
    @ModifyArgs(method="render(Lnet/minecraft/entity/LivingEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", at = @At(value ="INVOKE", target ="Lnet/minecraft/client/render/entity/LivingEntityRenderer;setupTransforms(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/client/util/math/MatrixStack;FFF)V"))
    private void freezeTransforms(Args args){
        LivingEntity livingEntity = args.get(0);
        if (livingEntity.isCrystallized()){
            args.set(2, 0f);
            args.set(3, 0f);
            args.set(4, 0f);
        }
    }
}
