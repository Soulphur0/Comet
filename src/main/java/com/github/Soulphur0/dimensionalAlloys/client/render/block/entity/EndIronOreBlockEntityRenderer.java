package com.github.Soulphur0.dimensionalAlloys.client.render.block.entity;

import com.github.Soulphur0.dimensionalAlloys.block.entity.EndIronOreBlockEntity;
import com.github.Soulphur0.registries.CometBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;

import java.util.concurrent.atomic.AtomicBoolean;

public class EndIronOreBlockEntityRenderer implements BlockEntityRenderer<EndIronOreBlockEntity> {

    Identifier revealedTexture = new Identifier("comet","textures/block/end_iron_ore_revealed.png");

    public EndIronOreBlockEntityRenderer(BlockEntityRendererFactory.Context ctx){}

    @Override
    public int getRenderDistance() {
        return MinecraftClient.getInstance().options.getClampedViewDistance()*16;
    }

    @Override
    public void render(EndIronOreBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        ClientPlayerEntity playerEntity = MinecraftClient.getInstance().player;
        AtomicBoolean clientHasNightVision = new AtomicBoolean(false);

        if (playerEntity != null){
            playerEntity.getStatusEffects().forEach(statusEffectInstance -> {
                clientHasNightVision.set(statusEffectInstance.getEffectType() == StatusEffects.NIGHT_VISION);
            });
        }

        if (clientHasNightVision.get()){
            matrices.push();
            matrices.scale(1.00625F,1.00625F,1.00625F);
            matrices.translate(-0.0003125F,-0.0003125F,-0.0003125F);
            MinecraftClient.getInstance().getBlockRenderManager().renderBlock(CometBlocks.END_IRON_ORE_REVEALED.getDefaultState(), entity.getPos(), entity.getWorld(), matrices, vertexConsumers.getBuffer(RenderLayer.getTranslucent()), true, Random.create());
            matrices.pop();
        }
    }
}
