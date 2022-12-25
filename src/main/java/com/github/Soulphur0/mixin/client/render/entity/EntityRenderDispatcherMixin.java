package com.github.Soulphur0.mixin.client.render.entity;

import com.github.Soulphur0.Comet;
import com.llamalad7.mixinextras.injector.WrapWithCondition;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.PiglinEntity;
import net.minecraft.entity.passive.HorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderDispatcher.class)
public class EntityRenderDispatcherMixin<E extends Entity> {

    @WrapWithCondition(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/EntityRenderDispatcher;renderFire(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/entity/Entity;)V"))
    private boolean isEntityOnRegularFire(EntityRenderDispatcher instance, MatrixStack matrices, VertexConsumerProvider vertexConsumers, Entity entity){
        return !entity.isOnSoulFire() && !entity.isOnEndFire();
    }

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/math/MatrixStack;translate(DDD)V"))
    private void comet_renderCometFire(E entity, double x, double y, double z, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci){
        renderCometFire(matrices, vertexConsumers, entity);
    }

    private void renderCometFire(MatrixStack matrices, VertexConsumerProvider vertexConsumers, Entity entity){
        Sprite sprite;
        Sprite sprite2;
        if (entity.isOnSoulFire()){
            sprite = Comet.SOUL_FIRE_0.getSprite();
            sprite2 = Comet.SOUL_FIRE_1.getSprite();
        } else if (entity.isOnEndFire()){
            sprite = Comet.END_FIRE_0.getSprite();
            sprite2 = Comet.END_FIRE_1.getSprite();
        } else {
            return;
        }

        matrices.push();
        float f = entity.getWidth() * 1.4f;
        matrices.scale(f, f, f);
        float g = 0.5f;
        float h = 0.0f;
        float i = entity.getHeight() / f;
        float j = 0.0f;
        matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(-((EntityRenderDispatcher)(Object)this).camera.getYaw()));
        matrices.translate(0.0, 0.0, -0.3f + (float)((int)i) * 0.02f);
        float k = 0.0f;
        int l = 0;
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(TexturedRenderLayers.getEntityCutout());
        MatrixStack.Entry entry = matrices.peek();
        while (i > 0.0f) {
            Sprite sprite3 = l % 2 == 0 ? sprite : sprite2;
            float m = sprite3.getMinU();
            float n = sprite3.getMinV();
            float o = sprite3.getMaxU();
            float p = sprite3.getMaxV();
            if (l / 2 % 2 == 0) {
                float q = o;
                o = m;
                m = q;
            }
            drawFireVertex(entry, vertexConsumer, g - 0.0f, 0.0f - j, k, o, p);
            drawFireVertex(entry, vertexConsumer, -g - 0.0f, 0.0f - j, k, m, p);
            drawFireVertex(entry, vertexConsumer, -g - 0.0f, 1.4f - j, k, m, n);
            drawFireVertex(entry, vertexConsumer, g - 0.0f, 1.4f - j, k, o, n);
            i -= 0.45f;
            j -= 0.45f;
            g *= 0.9f;
            k += 0.03f;
            ++l;
        }
        matrices.pop();
    }

    private static void drawFireVertex(MatrixStack.Entry entry, VertexConsumer vertices, float x, float y, float z, float u, float v) {
        vertices.vertex(entry.getPositionMatrix(), x, y, z).color(255, 255, 255, 255).texture(u, v).overlay(0, 10).light(LightmapTextureManager.MAX_BLOCK_LIGHT_COORDINATE).normal(entry.getNormalMatrix(), 0.0f, 1.0f, 0.0f).next();
    }
}
