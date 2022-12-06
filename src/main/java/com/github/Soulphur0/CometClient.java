package com.github.Soulphur0;

import com.github.Soulphur0.dimensionalAlloys.armorModel.endbriteArmor.EndbriteArmorModel;
import com.github.Soulphur0.dimensionalAlloys.client.render.block.entity.CrystallizedCreatureBlockEntityRenderer;
import com.github.Soulphur0.dimensionalAlloys.client.render.block.entity.EndIronOreBlockEntityRenderer;
import com.github.Soulphur0.registries.CometBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.mixin.renderer.client.SpriteAtlasTextureMixin;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class CometClient implements ClientModInitializer {

    public static final EntityModelLayer ENDBRITE_ARMOR_MODEL_LAYER = new EntityModelLayer(new Identifier("comet", "endbrite_armor"), "endbrite_armor_outer");

    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(CometBlocks.ENDBRITE_TUBE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(CometBlocks.FRESH_END_MEDIUM, RenderLayer.getTranslucent());

        BlockRenderLayerMap.INSTANCE.putBlock(CometBlocks.CRYSTALLIZED_CREATURE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(CometBlocks.TRIMMED_CRYSTALLIZED_CREATURE, RenderLayer.getCutout());

        EntityModelLayerRegistry.registerModelLayer(ENDBRITE_ARMOR_MODEL_LAYER, EndbriteArmorModel::getTexturedModelData);


        BlockEntityRendererRegistry.register(CometBlocks.CRYSTALLIZED_CREATURE_BLOCK_ENTITY, CrystallizedCreatureBlockEntityRenderer::new);
        BlockEntityRendererRegistry.register(CometBlocks.END_IRON_ORE_BLOCK_ENTITY, EndIronOreBlockEntityRenderer::new);

        BlockRenderLayerMap.INSTANCE.putBlock(CometBlocks.END_FIRE, RenderLayer.getCutout());

        ClientPlayNetworking.registerGlobalReceiver(new Identifier("comet","soul_fire_ticks"), (client, handler, buf, responseSender) -> {
            if (MinecraftClient.getInstance().player != null){
                MinecraftClient.getInstance().player.setSoulFireTicks(buf.getInt(0));
            }
        });

        ClientPlayNetworking.registerGlobalReceiver(new Identifier("comet","end_fire_ticks"), (client, handler, buf, responseSender) -> {
            if (MinecraftClient.getInstance().player != null){
                MinecraftClient.getInstance().player.setEndFireTicks(buf.getInt(0));
            }
        });
    }
}