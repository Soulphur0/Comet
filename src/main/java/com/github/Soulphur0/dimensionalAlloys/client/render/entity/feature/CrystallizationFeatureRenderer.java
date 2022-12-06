package com.github.Soulphur0.dimensionalAlloys.client.render.entity.feature;

import com.github.Soulphur0.dimensionalAlloys.EntityCometBehaviour;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;

import java.util.Objects;

import static net.minecraft.client.render.RenderPhase.*;

public class CrystallizationFeatureRenderer extends FeatureRenderer {

    public CrystallizationFeatureRenderer(FeatureRendererContext context) {
        super(context);
    }

    public static RenderLayer.MultiPhaseParameters parameters;
    public static RenderLayer crystallizationLayer;

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, Entity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        // * Get crystallization scale for not crystallized/statue entities.
        float scale = ((EntityCometBehaviour)entity).getCrystallizationScale();

        // * Get material from NBT and use it as texture identifier in the render layer parameters.
        String material = (((EntityCometBehaviour)entity).getStatueMaterial() == null || Objects.equals(((EntityCometBehaviour) entity).getStatueMaterial(), "")) ? "end_medium" : ((EntityCometBehaviour)entity).getStatueMaterial();

        if (Objects.equals(material, "none")) return;

        parameters = RenderLayer.MultiPhaseParameters.builder()
                .shader(ENTITY_DECAL_SHADER)
                .texture(new RenderPhase.Texture( new Identifier("comet", "textures/entity/feature/" + material + ".png"), false, false))
                .depthTest(EQUAL_DEPTH_TEST)
                .cull(DISABLE_CULLING)
                .lightmap(ENABLE_LIGHTMAP)
                .transparency(TRANSLUCENT_TRANSPARENCY).build(false);

        crystallizationLayer = RenderLayer.of("crystallization", VertexFormats.POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL, VertexFormat.DrawMode.QUADS, 256, parameters);

        // * Add render layer to the vertex consumer.
        VertexConsumer vertexConsumer;
        vertexConsumer = vertexConsumers.getBuffer(crystallizationLayer);

        // * Render feature either it is crystallized or in process.
        if (!entity.isCrystallized())
            this.getContextModel().render(matrices, vertexConsumer, light, 0, 1.0F, 1.0F, 1.0F, scale);
        else
            this.getContextModel().render(matrices, vertexConsumer, light, 0, 1.0F, 1.0F, 1.0F, 1.0F);
    }
}
