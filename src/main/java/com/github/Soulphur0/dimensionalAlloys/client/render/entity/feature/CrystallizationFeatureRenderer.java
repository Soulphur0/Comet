package com.github.Soulphur0.dimensionalAlloys.client.render.entity.feature;

import com.github.Soulphur0.CometClient;
import com.github.Soulphur0.dimensionalAlloys.CrystallizedEntityMethods;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;

public class CrystallizationFeatureRenderer extends FeatureRenderer {
    private static final Identifier OVERLAY = new Identifier("comet", "textures/overlay3.png");

    public CrystallizationFeatureRenderer(FeatureRendererContext context) {
        super(context);
    }

    // TODO manage to render entity decal with transparency.
    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, Entity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        VertexConsumer vertexConsumer;
        vertexConsumer = vertexConsumers.getBuffer(CometClient.crystallizationLayer);

        float scale = ((CrystallizedEntityMethods)entity).getCrystallizationScale();

        this.getContextModel().render(matrices, vertexConsumer, light, 0, 0.5F, 0.5F, 0.5F, 0.95F);
    }
}
