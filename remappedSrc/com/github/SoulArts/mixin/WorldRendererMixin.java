package com.github.SoulArts.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.CloudRenderMode;
import net.minecraft.client.render.*;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.data.client.model.Texture;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.SynchronousResourceReloader;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;


@Mixin(WorldRenderer.class)
public abstract class WorldRendererMixin implements SynchronousResourceReloader, AutoCloseable {

    @Override
    public void close() throws Exception {

    }

    @Shadow
    private CloudRenderMode lastCloudsRenderMode;

    @ModifyArgs(method = "renderClouds(Lnet/minecraft/client/util/math/MatrixStack;FDDD)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/WorldRenderer;renderClouds(Lnet/minecraft/client/render/BufferBuilder;DDDLnet/minecraft/util/math/Vec3d;)V"))
    private void getRenderCloudsInfo(Args args) {
        renderCloudsBufferBuilder = args.get(0);
        renderCloudsX = args.get(1);
        renderCloudsY = args.get(2);
        renderCloudsZ = args.get(3);
        color = args.get(4);
    }

    BufferBuilder renderCloudsBufferBuilder;
    double renderCloudsX;
    double renderCloudsY;
    double renderCloudsZ;
    Vec3d color;

    @Inject(method = "renderClouds(Lnet/minecraft/client/render/BufferBuilder;DDDLnet/minecraft/util/math/Vec3d;)V", at = @At(value = "TAIL", target = "Lnet/minecraft/client/render/BufferBuilder;begin(Lnet/minecraft/client/render/VertexFormat$DrawMode;Lnet/minecraft/client/render/VertexFormat;)V", ordinal = 1))
    private void additionalRenders(CallbackInfo ci){
        renderCloudLayer(100.0F,120.0F);
        //renderCloudLayer(200.0F,370.0F);
        //renderCloudLayer(300.0F,870.0F);
    }

    private void renderCloudLayer(float horizontalDisplacement, float verticalDisplacement){
        // horizontalDisplacement --> How displaced are clouds horizontally from the default cloud layer, (this avoids both layers to have the same cloud pattern)
        // verticalDisplacement --> Added altitude from the default cloud height

        float k = (float)MathHelper.floor(renderCloudsX) * 0.00390625F;
        float l = (float)MathHelper.floor(renderCloudsZ) * 0.00390625F;
        float m = (float)color.x;
        float n = (float)color.y;
        float o = (float)color.z;
        float p = m * 0.9F;
        float q = n * 0.9F;
        float r = o * 0.9F;
        float s = m * 0.7F;
        float t = n * 0.7F;
        float u = o * 0.7F;
        float v = m * 0.8F;
        float w = n * 0.8F;
        float aa = o * 0.8F;

        float playerRelativeDistanceFromCloudLayer = (float)Math.floor(renderCloudsY / 4.0D) * 4.0F;
        playerRelativeDistanceFromCloudLayer += verticalDisplacement;

        if (this.lastCloudsRenderMode == CloudRenderMode.FANCY && playerRelativeDistanceFromCloudLayer < verticalDisplacement/2 + 60){ // * Render cloud layer only if previous layer is reached (this option may be CUSTOMIZABLE)
            for(int ac = MathHelper.floor(-0.135*horizontalDisplacement-3); ac <= MathHelper.floor(-0.125*horizontalDisplacement+4); ++ac) {
                for(int ad = -3; ad <= 4; ++ad) {
                    float ae = (float)(ac * 8);
                    float af = (float)(ad * 8);

                    // This renders the bottom face of clouds.
                    // Vertical displacement divided by two is added to the if statement to properly cull the bottom face of clouds.
                    // Additionally, this value is added extra distance (60 blocks) equal to the altitude the player must be in order to start rendering these clouds.
                    if (playerRelativeDistanceFromCloudLayer > -6.0F) {
                        renderCloudsBufferBuilder.vertex((double)(ae + 0.0F + horizontalDisplacement), (double)(playerRelativeDistanceFromCloudLayer + 0.0F), (double)(af + 8.0F)).texture((ae + 0.0F) * 0.00390625F + k, (af + 8.0F) * 0.00390625F + l).color(s, t, u, 0.8F).normal(0.0F, -1.0F, 0.0F).next();
                        renderCloudsBufferBuilder.vertex((double)(ae + 8.0F + horizontalDisplacement), (double)(playerRelativeDistanceFromCloudLayer + 0.0F), (double)(af + 8.0F)).texture((ae + 8.0F) * 0.00390625F + k, (af + 8.0F) * 0.00390625F + l).color(s, t, u, 0.8F).normal(0.0F, -1.0F, 0.0F).next();
                        renderCloudsBufferBuilder.vertex((double)(ae + 8.0F + horizontalDisplacement), (double)(playerRelativeDistanceFromCloudLayer + 0.0F), (double)(af + 0.0F)).texture((ae + 8.0F) * 0.00390625F + k, (af + 0.0F) * 0.00390625F + l).color(s, t, u, 0.8F).normal(0.0F, -1.0F, 0.0F).next();
                        renderCloudsBufferBuilder.vertex((double)(ae + 0.0F + horizontalDisplacement), (double)(playerRelativeDistanceFromCloudLayer + 0.0F), (double)(af + 0.0F)).texture((ae + 0.0F) * 0.00390625F + k, (af + 0.0F) * 0.00390625F + l).color(s, t, u, 0.8F).normal(0.0F, -1.0F, 0.0F).next();
                    }

                    // This renders the top face of clouds.
                    if (playerRelativeDistanceFromCloudLayer <= 5.0F) {
                        renderCloudsBufferBuilder.vertex((double)(ae + 0.0F + horizontalDisplacement), (double)(playerRelativeDistanceFromCloudLayer + 4.0F - 9.765625E-4F), (double)(af + 8.0F)).texture((ae + 0.0F) * 0.00390625F + k, (af + 8.0F) * 0.00390625F + l).color(m, n, o, 0.8F).normal(0.0F, 1.0F, 0.0F).next();
                        renderCloudsBufferBuilder.vertex((double)(ae + 8.0F + horizontalDisplacement), (double)(playerRelativeDistanceFromCloudLayer + 4.0F - 9.765625E-4F), (double)(af + 8.0F)).texture((ae + 8.0F) * 0.00390625F + k, (af + 8.0F) * 0.00390625F + l).color(m, n, o, 0.8F).normal(0.0F, 1.0F, 0.0F).next();
                        renderCloudsBufferBuilder.vertex((double)(ae + 8.0F + horizontalDisplacement), (double)(playerRelativeDistanceFromCloudLayer + 4.0F - 9.765625E-4F), (double)(af + 0.0F)).texture((ae + 8.0F) * 0.00390625F + k, (af + 0.0F) * 0.00390625F + l).color(m, n, o, 0.8F).normal(0.0F, 1.0F, 0.0F).next();
                        renderCloudsBufferBuilder.vertex((double)(ae + 0.0F + horizontalDisplacement), (double)(playerRelativeDistanceFromCloudLayer + 4.0F - 9.765625E-4F), (double)(af + 0.0F)).texture((ae + 0.0F) * 0.00390625F + k, (af + 0.0F) * 0.00390625F + l).color(m, n, o, 0.8F).normal(0.0F, 1.0F, 0.0F).next();
                    }

                    int aj;
                    // This renders the left face of clouds.
                    // Horizontal displacement is added to the if statement to properly cull the west face of clouds.
                    if (ac > -1 - horizontalDisplacement) {
                        for(aj = 0; aj < 8; ++aj) {
                            renderCloudsBufferBuilder.vertex((double)(ae + (float)aj + 0.0F + horizontalDisplacement), (double)(playerRelativeDistanceFromCloudLayer + 0.0F), (double)(af + 8.0F)).texture((ae + (float)aj + 0.5F) * 0.00390625F + k, (af + 8.0F) * 0.00390625F + l).color(p, q, r, 0.8F).normal(-1.0F, 0.0F, 0.0F).next();
                            renderCloudsBufferBuilder.vertex((double)(ae + (float)aj + 0.0F + horizontalDisplacement), (double)(playerRelativeDistanceFromCloudLayer + 4.0F), (double)(af + 8.0F)).texture((ae + (float)aj + 0.5F) * 0.00390625F + k, (af + 8.0F) * 0.00390625F + l).color(p, q, r, 0.8F).normal(-1.0F, 0.0F, 0.0F).next();
                            renderCloudsBufferBuilder.vertex((double)(ae + (float)aj + 0.0F + horizontalDisplacement), (double)(playerRelativeDistanceFromCloudLayer + 4.0F), (double)(af + 0.0F)).texture((ae + (float)aj + 0.5F) * 0.00390625F + k, (af + 0.0F) * 0.00390625F + l).color(p, q, r, 0.8F).normal(-1.0F, 0.0F, 0.0F).next();
                            renderCloudsBufferBuilder.vertex((double)(ae + (float)aj + 0.0F + horizontalDisplacement), (double)(playerRelativeDistanceFromCloudLayer + 0.0F), (double)(af + 0.0F)).texture((ae + (float)aj + 0.5F) * 0.00390625F + k, (af + 0.0F) * 0.00390625F + l).color(p, q, r, 0.8F).normal(-1.0F, 0.0F, 0.0F).next();
                        }
                    }

                    if (ac <= 1) {
                        // This renders the right face of clouds.
                        for(aj = 0; aj < 8; ++aj) {
                            renderCloudsBufferBuilder.vertex((double)(ae + (float)aj + 1.0F - 9.765625E-4F + horizontalDisplacement), (double)(playerRelativeDistanceFromCloudLayer + 0.0F), (double)(af + 8.0F)).texture((ae + (float)aj + 0.5F) * 0.00390625F + k, (af + 8.0F) * 0.00390625F + l).color(p, q, r, 0.8F).normal(1.0F, 0.0F, 0.0F).next();
                            renderCloudsBufferBuilder.vertex((double)(ae + (float)aj + 1.0F - 9.765625E-4F + horizontalDisplacement), (double)(playerRelativeDistanceFromCloudLayer + 4.0F), (double)(af + 8.0F)).texture((ae + (float)aj + 0.5F) * 0.00390625F + k, (af + 8.0F) * 0.00390625F + l).color(p, q, r, 0.8F).normal(1.0F, 0.0F, 0.0F).next();
                            renderCloudsBufferBuilder.vertex((double)(ae + (float)aj + 1.0F - 9.765625E-4F + horizontalDisplacement), (double)(playerRelativeDistanceFromCloudLayer + 4.0F), (double)(af + 0.0F)).texture((ae + (float)aj + 0.5F) * 0.00390625F + k, (af + 0.0F) * 0.00390625F + l).color(p, q, r, 0.8F).normal(1.0F, 0.0F, 0.0F).next();
                            renderCloudsBufferBuilder.vertex((double)(ae + (float)aj + 1.0F - 9.765625E-4F + horizontalDisplacement), (double)(playerRelativeDistanceFromCloudLayer + 0.0F), (double)(af + 0.0F)).texture((ae + (float)aj + 0.5F) * 0.00390625F + k, (af + 0.0F) * 0.00390625F + l).color(p, q, r, 0.8F).normal(1.0F, 0.0F, 0.0F).next();
                        }
                    }
                    // This renders the front(north) face of clouds.
                    if (ad > -1) {
                        for(aj = 0; aj < 8; ++aj) {
                            renderCloudsBufferBuilder.vertex((double)(ae + 0.0F + horizontalDisplacement), (double)(playerRelativeDistanceFromCloudLayer + 4.0F), (double)(af + (float)aj + 0.0F)).texture((ae + 0.0F) * 0.00390625F + k, (af + (float)aj + 0.5F) * 0.00390625F + l).color(v, w, aa, 0.8F).normal(0.0F, 0.0F, -1.0F).next();
                            renderCloudsBufferBuilder.vertex((double)(ae + 8.0F + horizontalDisplacement), (double)(playerRelativeDistanceFromCloudLayer + 4.0F), (double)(af + (float)aj + 0.0F)).texture((ae + 8.0F) * 0.00390625F + k, (af + (float)aj + 0.5F) * 0.00390625F + l).color(v, w, aa, 0.8F).normal(0.0F, 0.0F, -1.0F).next();
                            renderCloudsBufferBuilder.vertex((double)(ae + 8.0F + horizontalDisplacement), (double)(playerRelativeDistanceFromCloudLayer + 0.0F), (double)(af + (float)aj + 0.0F)).texture((ae + 8.0F) * 0.00390625F + k, (af + (float)aj + 0.5F) * 0.00390625F + l).color(v, w, aa, 0.8F).normal(0.0F, 0.0F, -1.0F).next();
                            renderCloudsBufferBuilder.vertex((double)(ae + 0.0F + horizontalDisplacement), (double)(playerRelativeDistanceFromCloudLayer + 0.0F), (double)(af + (float)aj + 0.0F)).texture((ae + 0.0F) * 0.00390625F + k, (af + (float)aj + 0.5F) * 0.00390625F + l).color(v, w, aa, 0.8F).normal(0.0F, 0.0F, -1.0F).next();
                        }
                    }
                    // This renders the back(south) face of clouds.
                    if (ad <= 1) {
                        for(aj = 0; aj < 8; ++aj) {
                            renderCloudsBufferBuilder.vertex((double)(ae + 0.0F + horizontalDisplacement), (double)(playerRelativeDistanceFromCloudLayer + 4.0F), (double)(af + (float)aj + 1.0F - 9.765625E-4F)).texture((ae + 0.0F) * 0.00390625F + k, (af + (float)aj + 0.5F) * 0.00390625F + l).color(v, w, aa, 0.8F).normal(0.0F, 0.0F, 1.0F).next();
                            renderCloudsBufferBuilder.vertex((double)(ae + 8.0F + horizontalDisplacement), (double)(playerRelativeDistanceFromCloudLayer + 4.0F), (double)(af + (float)aj + 1.0F - 9.765625E-4F)).texture((ae + 8.0F) * 0.00390625F + k, (af + (float)aj + 0.5F) * 0.00390625F + l).color(v, w, aa, 0.8F).normal(0.0F, 0.0F, 1.0F).next();
                            renderCloudsBufferBuilder.vertex((double)(ae + 8.0F + horizontalDisplacement), (double)(playerRelativeDistanceFromCloudLayer + 0.0F), (double)(af + (float)aj + 1.0F - 9.765625E-4F)).texture((ae + 8.0F) * 0.00390625F + k, (af + (float)aj + 0.5F) * 0.00390625F + l).color(v, w, aa, 0.8F).normal(0.0F, 0.0F, 1.0F).next();
                            renderCloudsBufferBuilder.vertex((double)(ae + 0.0F + horizontalDisplacement), (double)(playerRelativeDistanceFromCloudLayer + 0.0F), (double)(af + (float)aj + 1.0F - 9.765625E-4F)).texture((ae + 0.0F) * 0.00390625F + k, (af + (float)aj + 0.5F) * 0.00390625F + l).color(v, w, aa, 0.8F).normal(0.0F, 0.0F, 1.0F).next();
                        }
                    }
                }
            }
        } else { // * IMPORTANTE, AQUÍ SE DEBE IGNORAR LA ALTURA DE RENDERIZADO SI, Y SOLO SI, SE DESEA USAR LODs EN LAS NUBES (Renderizando todas a la vez); o alternativamente, usar LODs cuando solo se está renderizando una capa de nubes y la siguiente.
            for(int am = MathHelper.floor(-0.96*horizontalDisplacement-32); am < -64; am += 32) {
                for(int an = -32; an < 32; an += 32) {
                    renderCloudsBufferBuilder.vertex((double)(am + 0 + horizontalDisplacement), (double)playerRelativeDistanceFromCloudLayer, (double)(an + 32)).texture((float)(am + 0) * 0.00390625F + k, (float)(an + 32) * 0.00390625F + l).color(m, n, o, 0.8F).normal(0.0F, -1.0F, 0.0F).next();
                    renderCloudsBufferBuilder.vertex((double)(am + 32 + horizontalDisplacement), (double)playerRelativeDistanceFromCloudLayer, (double)(an + 32)).texture((float)(am + 32) * 0.00390625F + k, (float)(an + 32) * 0.00390625F + l).color(m, n, o, 0.8F).normal(0.0F, -1.0F, 0.0F).next();
                    renderCloudsBufferBuilder.vertex((double)(am + 32 + horizontalDisplacement), (double)playerRelativeDistanceFromCloudLayer, (double)(an + 0)).texture((float)(am + 32) * 0.00390625F + k, (float)(an + 0) * 0.00390625F + l).color(m, n, o, 0.8F).normal(0.0F, -1.0F, 0.0F).next();
                    renderCloudsBufferBuilder.vertex((double)(am + 0 + horizontalDisplacement), (double)playerRelativeDistanceFromCloudLayer, (double)(an + 0)).texture((float)(am + 0) * 0.00390625F + k, (float)(an + 0) * 0.00390625F + l).color(m, n, o, 0.8F).normal(0.0F, -1.0F, 0.0F).next();
                }
            }
        }
    }
}
