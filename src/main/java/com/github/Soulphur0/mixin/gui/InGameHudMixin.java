package com.github.Soulphur0.mixin.gui;

import com.github.Soulphur0.dimensionalAlloys.EntityCometBehaviour;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin extends DrawableHelper implements EntityCometBehaviour {

    private static final Identifier CRYSTALLIZATION_OUTLINE_1 = new Identifier("comet","textures/misc/crystallization_outline_1.png");
    private static final Identifier CRYSTALLIZATION_OUTLINE_2 = new Identifier("comet","textures/misc/crystallization_outline_2.png");


    // * Inject
    @Mutable
    @Final
    @Shadow
    private final MinecraftClient client;

    @Shadow protected abstract void renderOverlay(Identifier texture, float opacity);

    public InGameHudMixin(MinecraftClient client) {
        this.client = client;
    }

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/MinecraftClient;getLastFrameDuration()F"))
    public void renderOverlay(MatrixStack matrices, float tickDelta, CallbackInfo ci){
        if (this.client.player != null){
            int crystallizedTicks = ((EntityCometBehaviour)this.client.player).getCrystallizedTicks();
            float crystallizationScale = ((EntityCometBehaviour)this.client.player).getCrystallizationScale();

            if (crystallizedTicks > 0){
                this.renderOverlay(CRYSTALLIZATION_OUTLINE_2, crystallizationScale);
                this.renderOverlay(CRYSTALLIZATION_OUTLINE_1, crystallizationScale);
            }
        }
    }
}