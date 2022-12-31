package com.github.Soulphur0.mixin.client.gui.hud;

import com.github.Soulphur0.dimensionalAlloys.EntityCometBehaviour;
import com.github.Soulphur0.dimensionalAlloys.client.gui.hud.CometHeartType;
import com.llamalad7.mixinextras.injector.WrapWithCondition;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin extends DrawableHelper {

    @Mutable @Final @Shadow private final MinecraftClient client;

    @Final @Shadow private Random random;

    @Shadow protected abstract PlayerEntity getCameraPlayer();

    @Shadow protected abstract void renderOverlay(Identifier texture, float opacity);

    // : Overlay, health bar and food bar render.
    private static final Identifier CRYSTALLIZATION_OUTLINE_1 = new Identifier("comet","textures/misc/crystallization_outline_1.png");
    private static final Identifier CRYSTALLIZATION_OUTLINE_2 = new Identifier("comet","textures/misc/crystallization_outline_2.png");
    private static final Identifier COMET_GUI_ICONS = new Identifier("comet", "textures/gui/icons.png");

    public InGameHudMixin(MinecraftClient client) {
        this.client = client;
    }

    // $ Render crystallization overlay.
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

    // $ Render comet health bar.
    // _ Cancel the render of regular hearts and heart containers if a comet heart shape is being used.
    // % NOTE: in the future, when hearts are not rendered but the container still is, split this injection into TWO.
    @Inject(method ="renderHealthBar", at = @At(value = "HEAD"), cancellable = true)
    private void comet_cancelHeartAndContainerDraw(MatrixStack matrices, PlayerEntity player, int x, int y, int lines, int regeneratingHeartIndex, float maxHealth, int lastHealth, int health, int absorption, boolean blinking, CallbackInfo ci){
        if (getCameraPlayer().isCrystallized() && !getCameraPlayer().isCrystallizedByStatusEffect())
            ci.cancel();
    }

    // _ Renders comet's hearts, and containers if a comet heart shape is being used.
    @Inject(method ="renderStatusBars", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;renderHealthBar(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/entity/player/PlayerEntity;IIIIFIIIZ)V", shift = At.Shift.BY, by = 1), locals = LocalCapture.CAPTURE_FAILHARD)
    private void comet_renderHealthBar(MatrixStack matrices, CallbackInfo ci, PlayerEntity playerEntity, int i, boolean bl, long l, int j, HungerManager hungerManager, int k, int m, int n, int o, float f, int p, int q, int r, int s, int t, int u, int v){
        RenderSystem.setShaderTexture(0, COMET_GUI_ICONS);
        renderCometHealthBar(matrices, playerEntity, m, o, r, v, f, i, j, p, bl);
        RenderSystem.setShaderTexture(0, GUI_ICONS_TEXTURE);
    }

    // ? Draws comet hearts and/or containers over the regular health bar.
    private void renderCometHealthBar(MatrixStack matrices, PlayerEntity player, int screenPosX, int screenPosY, int lines, int regeneratingHeartIndex, float maxHealth, int lastHealth, int health, int absorptionHealthPoints, boolean blinking) {
        CometHeartType heartType = CometHeartType.fromPlayerState(player);
        if (heartType == null) return;

        int textureRow = 9 * (player.world.getLevelProperties().isHardcore() ? 5 : 0);
        int heartCount = MathHelper.ceil((double)maxHealth / 2.0);
        int absorptionHeartCount = MathHelper.ceil((double)absorptionHealthPoints / 2.0);
        int halfHearts = heartCount * 2;

        for (int loopCounter = heartCount + absorptionHeartCount - 1; loopCounter >= 0; --loopCounter) {
            boolean drawingHalfHeart;
            int healthPoints;
            boolean drawFilledHearts;
            int heartRowNumber = loopCounter / 10;
            int heartRowNumberTotal = loopCounter % 10;
            int drawPositionX = screenPosX + heartRowNumberTotal * 8;
            int drawPositionY = screenPosY - heartRowNumber * lines;

            // + Make hearts jump 1-2 pixels.
            // - This happens when health is very low, hearts shiver.
            if (lastHealth + absorptionHealthPoints <= 4) {
                drawPositionY += this.random.nextInt(2);
            }

            // - Place regenerating health hearts downwards, to animate hearts bounce.
            if (loopCounter < heartCount && loopCounter == regeneratingHeartIndex) {
                drawPositionY -= 2;
            }

            // + Draw special heart container.
            // = Only if necessary.
            this.drawCometHeart(matrices, CometHeartType.CRYSTALLIZED_CONTAINER, drawPositionX, drawPositionY, textureRow, blinking, false);

            int halfHeartLoopCounter = loopCounter * 2;

            // + Draw (any)heart blinking.
            if (blinking && halfHeartLoopCounter < health) {
                drawingHalfHeart = halfHeartLoopCounter + 1 == health;
                this.drawCometHeart(matrices, heartType, drawPositionX, drawPositionY, textureRow, true, drawingHalfHeart);
            }

            // * Skip heart draw if health is not enough.
            if (halfHeartLoopCounter >= lastHealth) continue;

            // + Draw regular heart.
            drawingHalfHeart = halfHeartLoopCounter + 1 == lastHealth;
            this.drawCometHeart(matrices, heartType, drawPositionX, drawPositionY, textureRow, false, drawingHalfHeart);
        }
    }

    // ? Calls the drawing process of individual hearts.
    private void drawCometHeart(MatrixStack matrices, CometHeartType type, int x, int y, int v, boolean blinking, boolean halfHeart){
        this.drawTexture(matrices, x, y, type.getTextureU(halfHeart, blinking), v, 9, 9);
    }

    // $ Methods used to render comet food bar
    // + In the vanilla food rendering section:
    // * The first drawTextures call is for the food containers.
    // * The second call is for full food icons.
    // * The third call is for half food icons.

    // _ Cancels the draw of full food icons when necessary.
    @WrapWithCondition(method = "renderStatusBars", at =  @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;drawTexture(Lnet/minecraft/client/util/math/MatrixStack;IIIIII)V", ordinal = 4))
    private boolean comet_cancelFullFoodIconDraw(InGameHud instance, MatrixStack matrices, int x, int y, int u, int v, int width, int height){
        return !this.getCameraPlayer().isCrystallized();
    }

    // _ Cancels the draw of half food icons when necessary.
    @WrapWithCondition(method = "renderStatusBars", at =  @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;drawTexture(Lnet/minecraft/client/util/math/MatrixStack;IIIIII)V", ordinal = 5))
    private boolean comet_cancelHalfFoodIconDraw(InGameHud instance, MatrixStack matrices, int x, int y, int u, int v, int width, int height){
        return !this.getCameraPlayer().isCrystallized();
    }

    // _ Renders Comet's full food icon if necessary.
    @Inject(method = "renderStatusBars", at =  @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;drawTexture(Lnet/minecraft/client/util/math/MatrixStack;IIIIII)V", ordinal = 4, shift = At.Shift.BY, by = 1), locals = LocalCapture.CAPTURE_FAILHARD)
    private void comet_drawFullFoodIcon(MatrixStack matrices, CallbackInfo ci, PlayerEntity playerEntity, int i, boolean bl, long l, int j, HungerManager hungerManager, int k, int m, int n, int o, float f, int p, int q, int r, int s, int t, int u, int v, LivingEntity livingEntity, int x, int y, int z, int aa, int ab, int ac){
        RenderSystem.setShaderTexture(0, COMET_GUI_ICONS);
        PlayerEntity  player = getCameraPlayer();
        if (player.isCrystallized())
            this.drawTexture(matrices, ac, z, 0, 9, 9, 9);
        RenderSystem.setShaderTexture(0, GUI_ICONS_TEXTURE);
    }

    // _ Renders Comet's full food icon if necessary.
    @Inject(method = "renderStatusBars", at =  @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;drawTexture(Lnet/minecraft/client/util/math/MatrixStack;IIIIII)V", ordinal = 5, shift = At.Shift.BY, by = 1), locals = LocalCapture.CAPTURE_FAILHARD)
    private void comet_drawHalfFoodIcon(MatrixStack matrices, CallbackInfo ci, PlayerEntity playerEntity, int i, boolean bl, long l, int j, HungerManager hungerManager, int k, int m, int n, int o, float f, int p, int q, int r, int s, int t, int u, int v, LivingEntity livingEntity, int x, int y, int z, int aa, int ab, int ac){
        RenderSystem.setShaderTexture(0, COMET_GUI_ICONS);
        if (this.getCameraPlayer().isCrystallized())
            this.drawTexture(matrices, ac, z, 9, 9, 9, 9);
        RenderSystem.setShaderTexture(0, GUI_ICONS_TEXTURE);
    }
}