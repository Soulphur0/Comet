package com.github.Soulphur0.mixin.client.render;

import com.github.Soulphur0.dimensionalAlloys.client.render.CometCameraSubmersionType;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {

    // $ Injections
    // _ Do not render sky if the camera is submerged.
    @Inject(method="renderSky(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/util/math/Matrix4f;FLnet/minecraft/client/render/Camera;ZLjava/lang/Runnable;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/Camera;getSubmersionType()Lnet/minecraft/client/render/CameraSubmersionType;", shift = At.Shift.AFTER), cancellable = true)
    private void cancelSkyRender(MatrixStack matrices, Matrix4f projectionMatrix, float tickDelta, Camera camera, boolean bl, Runnable runnable, CallbackInfo ci){
        CometCameraSubmersionType cometCameraSubmersionType = camera.comet_getSubmersionType();
        if (cometCameraSubmersionType == CometCameraSubmersionType.END_MEDIUM)
            ci.cancel();
    }
}
