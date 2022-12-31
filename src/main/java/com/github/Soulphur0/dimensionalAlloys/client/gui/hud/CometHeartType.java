package com.github.Soulphur0.dimensionalAlloys.client.gui.hud;

import net.minecraft.entity.player.PlayerEntity;

public enum CometHeartType {
    CRYSTALLIZED_CONTAINER(0, false),
    CRYSTALLIZED(1, false);

    private final int textureIndex;
    private final boolean hasBlinkingTexture;
    private final int heartTextureSize = 9;

    CometHeartType(int heartTextureIndex, boolean blinking){
        this.textureIndex = heartTextureIndex;
        this.hasBlinkingTexture = blinking;
    }

    public int getTextureU(boolean halfHeart, boolean blinking){
        int halfHeartOffset = halfHeart ? 1 : 0;
        int blinkingOffset = this.hasBlinkingTexture && blinking ? 2 : 0;
        int totalTextureOffset = halfHeartOffset + blinkingOffset;

        return (this.textureIndex * 2 + totalTextureOffset) * heartTextureSize;
    }

    public static CometHeartType fromPlayerState(PlayerEntity player) {
        return (player.isCrystallized() && !player.isCrystallizedByStatusEffect()) ? CRYSTALLIZED : null;
    }
}
