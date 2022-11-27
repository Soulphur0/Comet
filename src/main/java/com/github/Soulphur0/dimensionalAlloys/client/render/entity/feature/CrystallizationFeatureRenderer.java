package com.github.Soulphur0.dimensionalAlloys.client.render.entity.feature;

import com.github.Soulphur0.CometClient;
import com.github.Soulphur0.dimensionalAlloys.CrystallizedEntityMethods;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.util.Identifier;

import static net.minecraft.client.render.RenderPhase.*;
import static net.minecraft.client.render.RenderPhase.ENABLE_OVERLAY_COLOR;

public class CrystallizationFeatureRenderer extends FeatureRenderer {
    private static final Identifier OVERLAY = new Identifier("comet", "textures/overlay3.png");
    private static final Identifier OVERLAY2 = new Identifier("comet", "textures/overlay4.png");

    public CrystallizationFeatureRenderer(FeatureRendererContext context) {
        super(context);
    }

    public static RenderLayer.MultiPhaseParameters parameters;
    public static RenderLayer crystallizationLayer;
    Identifier textureToUse;

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, Entity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {

        textureToUse = OVERLAY;

        parameters = RenderLayer.MultiPhaseParameters.builder()
                .shader(ENTITY_DECAL_SHADER)
                .texture(new RenderPhase.Texture(textureToUse, false, false))
                .depthTest(EQUAL_DEPTH_TEST)
                .cull(DISABLE_CULLING)
                .lightmap(ENABLE_LIGHTMAP)
                .transparency(TRANSLUCENT_TRANSPARENCY)
                .overlay(ENABLE_OVERLAY_COLOR).build(false);

        crystallizationLayer = RenderLayer.of("crystallization", VertexFormats.POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL, VertexFormat.DrawMode.QUADS, 256, parameters);

        VertexConsumer vertexConsumer;
        vertexConsumer = vertexConsumers.getBuffer(crystallizationLayer);

        float scale = ((CrystallizedEntityMethods)entity).getCrystallizationScale();

        if (!entity.isCrystallized())
            this.getContextModel().render(matrices, vertexConsumer, light, 0, 0.5F, 1.0F, 1.0F, scale);
        else
            this.getContextModel().render(matrices, vertexConsumer, light, 0, 0.5F, 1.0F, 1.0F, 1.0F);
    }
}
