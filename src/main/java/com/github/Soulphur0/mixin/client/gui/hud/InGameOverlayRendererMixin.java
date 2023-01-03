package com.github.Soulphur0.mixin.client.gui.hud;

import com.github.Soulphur0.Comet;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameOverlayRenderer;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameOverlayRenderer.class)
public class InGameOverlayRendererMixin {

    private static final SpriteIdentifier SOUL_FIRE_1 = Comet.SOUL_FIRE_1;
    private static final SpriteIdentifier END_FIRE_1 = Comet.END_FIRE_1;
    private static Sprite lastSprite = ModelLoader.FIRE_1.getSprite();

    @ModifyVariable(method="renderFireOverlay", at = @At("STORE"), ordinal = 0)
    private static Sprite useCorrespondingFireOverlay(Sprite sprite){
        PlayerEntity playerEntity = MinecraftClient.getInstance().player;

        if (playerEntity != null) {
            if (playerEntity.isOnSoulFire()/*getSoulFireTicks() > -20*/) {
                lastSprite = SOUL_FIRE_1.getSprite();
                return SOUL_FIRE_1.getSprite();
            } else if (playerEntity.isOnEndFire()/*getEndFireTicks() > -20*/){
                lastSprite = END_FIRE_1.getSprite();
                return END_FIRE_1.getSprite();
            } else {
                lastSprite = ModelLoader.FIRE_1.getSprite();
                return ModelLoader.FIRE_1.getSprite();
            }
        }

        return lastSprite;
    }
}
