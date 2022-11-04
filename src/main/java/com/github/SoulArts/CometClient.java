package com.github.SoulArts;

import com.github.SoulArts.dimensionalAlloys.armorModel.endbriteArmor.EndbriteHelmetModel;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class CometClient implements ClientModInitializer {

    public static final EntityModelLayer ENDBRITE_HELMET_MODEL_LAYER = new EntityModelLayer(new Identifier("comet", "endbrite_helmet"), "endbrite_helmet");

    @Override
    public void onInitializeClient(){
        BlockRenderLayerMap.INSTANCE.putBlock(Comet.ENDBRITE_TUBE, RenderLayer.getCutout());
        EntityModelLayerRegistry.registerModelLayer(ENDBRITE_HELMET_MODEL_LAYER, EndbriteHelmetModel::getTexturedModelData);
    }
}
