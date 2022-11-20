package com.github.Soulphur0;

import com.github.Soulphur0.dimensionalAlloys.armorModel.endbriteArmor.EndbriteArmorModel;
import com.github.Soulphur0.dimensionalAlloys.client.render.block.entity.CrystallizedCreatureBlockEntityRenderer;
import com.github.Soulphur0.dimensionalAlloys.client.render.block.entity.EndIronOreBlockEntityRenderer;
import com.github.Soulphur0.registries.CometBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

import static net.minecraft.client.render.RenderPhase.*;

@Environment(EnvType.CLIENT)
public class CometClient implements ClientModInitializer {

    public static final EntityModelLayer ENDBRITE_ARMOR_MODEL_LAYER = new EntityModelLayer(new Identifier("comet", "endbrite_armor"), "endbrite_armor_outer");
    public static RenderLayer.MultiPhaseParameters parameters = RenderLayer.MultiPhaseParameters.builder().shader(ENTITY_DECAL_SHADER).texture(new RenderPhase.Texture(new Identifier("comet", "textures/overlay4.png"), false, false)).depthTest(EQUAL_DEPTH_TEST).cull(DISABLE_CULLING).lightmap(ENABLE_LIGHTMAP).transparency(TRANSLUCENT_TRANSPARENCY).overlay(ENABLE_OVERLAY_COLOR).build(false);
    public static RenderLayer crystallizationLayer = RenderLayer.of("crystallization", VertexFormats.POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL, VertexFormat.DrawMode.QUADS, 256, parameters);

    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(CometBlocks.ENDBRITE_TUBE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(CometBlocks.FRESH_END_MEDIUM, RenderLayer.getTranslucent());

        BlockRenderLayerMap.INSTANCE.putBlock(CometBlocks.CRYSTALLIZED_CREATURE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(CometBlocks.TRIMMED_CRYSTALLIZED_CREATURE, RenderLayer.getCutout());

        EntityModelLayerRegistry.registerModelLayer(ENDBRITE_ARMOR_MODEL_LAYER, EndbriteArmorModel::getTexturedModelData);

        BlockEntityRendererRegistry.register(CometBlocks.CRYSTALLIZED_CREATURE_BLOCK_ENTITY, CrystallizedCreatureBlockEntityRenderer::new);
        BlockEntityRendererRegistry.register(CometBlocks.END_IRON_ORE_BLOCK_ENTITY, EndIronOreBlockEntityRenderer::new);
    }
}