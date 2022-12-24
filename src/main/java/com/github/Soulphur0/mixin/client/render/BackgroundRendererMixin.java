package com.github.Soulphur0.mixin.client.render;

import com.github.Soulphur0.dimensionalAlloys.client.render.CometCameraSubmersionType;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.CameraSubmersionType;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(BackgroundRenderer.class)
public class BackgroundRendererMixin {

    @Shadow
    private static float red;
    @Shadow
    private static float green;
    @Shadow
    private static float blue;
    @Shadow
    private static long lastWaterFogColorUpdateTime;

    // $ Injections

    // _ Determines the color of the fog.
    @Inject(method="render", at = @At(value ="INVOKE", target="Lnet/minecraft/client/render/Camera;getPos()Lnet/minecraft/util/math/Vec3d;", ordinal = 3))
    private static void addCometFogRender(Camera camera, float tickDelta, ClientWorld world, int viewDistance, float skyDarkness, CallbackInfo ci){
        CometCameraSubmersionType cometCameraSubmersionType = camera.comet_getSubmersionType();
        if (cometCameraSubmersionType == CometCameraSubmersionType.END_MEDIUM) {
            red = 0.56f;
            green = 0.0f;
            blue = 1.76f;
            lastWaterFogColorUpdateTime = -1L;
            RenderSystem.clearColor(red, green, blue, 0.0f);
        }
    }

    // _ Determines the radius of the fog.
    @Inject(method="applyFog", at = @At(value = "TAIL"), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    private static void getFogDataToApplyCometFog(Camera camera, BackgroundRenderer.FogType fogType, float viewDistance, boolean thickFog, float tickDelta, CallbackInfo ci, CameraSubmersionType cameraSubmersionType, Entity entity, BackgroundRenderer.FogData fogData){
        CometCameraSubmersionType cometCameraSubmersionType = camera.comet_getSubmersionType();
        if (cometCameraSubmersionType == CometCameraSubmersionType.END_MEDIUM){
            if (entity.isSpectator()) {
                fogData.fogStart = -8.0f;
                fogData.fogEnd = viewDistance * 0.5f;
            } else {
                fogData.fogStart = 0.0f;
                fogData.fogEnd = 2.0f;
            }
            RenderSystem.setShaderFogStart(fogData.fogStart);
            RenderSystem.setShaderFogEnd(fogData.fogEnd);
            RenderSystem.setShaderFogShape(fogData.fogShape);
            ci.cancel();
        }
    }
}
