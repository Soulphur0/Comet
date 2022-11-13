package com.github.SoulArts.mixin.gui;

import com.github.SoulArts.dimensionalAlloys.CrystallizedEntityMethods;
import com.github.SoulArts.mixin.entity.EntityMixin;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin extends DrawableHelper implements CrystallizedEntityMethods {

    private static final Identifier POWDER_SNOW_OUTLINE = new Identifier("textures/misc/powder_snow_outline.png");
    // - Inject
    @Mutable
    @Final
    @Shadow
    private final MinecraftClient client;

    @Shadow protected abstract void renderOverlay(Identifier texture, float opacity);

    public InGameHudMixin(MinecraftClient client) {
        this.client = client;
    }

    @Inject(method = "render", at = @At("TAIL"))
    public void renderOverlay(MatrixStack matrices, float tickDelta, CallbackInfo ci){
        if (this.client.player != null && ((CrystallizedEntityMethods)this.client.player).getCrystallizedTicks() > 0){
            this.renderOverlay(POWDER_SNOW_OUTLINE, ((CrystallizedEntityMethods)this.client.player).getCrystallizationScale());
        }
    }
}
