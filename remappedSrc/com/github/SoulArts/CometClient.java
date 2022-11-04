package com.github.SoulArts;

import com.github.SoulArts.Comet;
import com.github.SoulArts.dimensionalAlloys.armorModel.endbriteArmor.EndbriteHelmetModel;
import com.github.SoulArts.entityRenderer.NebuwhaleEntityRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderingRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class CometClient implements ClientModInitializer {
    @Override
    public void onInitializeClient(){
        BlockRenderLayerMap.INSTANCE.putBlock(Comet.ENDBRITE_TUBE, RenderLayer.getCutout());
        EntityRendererRegistry.INSTANCE.register(Comet.NEBUWHALE, (dispatcher, context) -> {
            return new NebuwhaleEntityRenderer(dispatcher);
        });
    }
}
