package com.github.Soulphur0;

import com.github.Soulphur0.dimensionalAlloys.CometClientWorldExtras;
import com.github.Soulphur0.dimensionalAlloys.armorModel.endbriteArmor.EndbriteArmorModel;
import com.github.Soulphur0.dimensionalAlloys.block.CreatureStatue;
import com.github.Soulphur0.dimensionalAlloys.client.render.block.entity.CrystallizedCreatureBlockEntityRenderer;
import com.github.Soulphur0.dimensionalAlloys.client.render.block.entity.EndIronOreBlockEntityRenderer;
import com.github.Soulphur0.dimensionalAlloys.client.render.entity.model.PortalShieldEntityModel;
import com.github.Soulphur0.dimensionalAlloys.client.sound.CrystallizationGrowsSoundInstance;
import com.github.Soulphur0.mixin.client.world.ClientWorldMixin;
import com.github.Soulphur0.registries.CometBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.telemetry.TelemetrySender;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

import java.util.Objects;
import java.util.UUID;

@Environment(EnvType.CLIENT)
public class CometClient implements ClientModInitializer {

    public static final EntityModelLayer ENDBRITE_ARMOR_MODEL_LAYER = new EntityModelLayer(new Identifier("comet", "endbrite_armor"), "endbrite_armor_outer");
    public static final EntityModelLayer PORTAL_SHIELD_MODEL_LAYER = new EntityModelLayer(new Identifier("comet", "portal_shield"), "portal_shield");

    @Override
    public void onInitializeClient() {
        // : Rendering -------------------------------------------------------------------------------------------------
        // $ Block render layer maps
        // _ Endbrite tube
        BlockRenderLayerMap.INSTANCE.putBlock(CometBlocks.ENDBRITE_TUBE, RenderLayer.getCutout());

        // _ End medium
        BlockRenderLayerMap.INSTANCE.putBlock(CometBlocks.END_MEDIUM_LAYER, RenderLayer.getTranslucent());

        // _ Crystallized creature
        BlockRenderLayerMap.INSTANCE.putBlock(CometBlocks.CRYSTALLIZED_CREATURE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(CometBlocks.TRIMMED_CRYSTALLIZED_CREATURE, RenderLayer.getCutout());

        // _ End fire
        BlockRenderLayerMap.INSTANCE.putBlock(CometBlocks.END_FIRE, RenderLayer.getCutout());

        // _ Thorned roots
        BlockRenderLayerMap.INSTANCE.putBlock(CometBlocks.THORNED_ROOTS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(CometBlocks.THORNED_ROOTS_PLANT, RenderLayer.getCutout());

        // $ Entity model layers
        // _ Endbrite armor layer
        EntityModelLayerRegistry.registerModelLayer(ENDBRITE_ARMOR_MODEL_LAYER, EndbriteArmorModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(PORTAL_SHIELD_MODEL_LAYER, PortalShieldEntityModel::getTexturedModelData);

        // $ Block entity renderers
        // _ Crystallized creature
        BlockEntityRendererRegistry.register(CometBlocks.CRYSTALLIZED_CREATURE_BLOCK_ENTITY, CrystallizedCreatureBlockEntityRenderer::new);

        // _ End iron ore
        BlockEntityRendererRegistry.register(CometBlocks.END_IRON_ORE_BLOCK_ENTITY, EndIronOreBlockEntityRenderer::new);

        //. Networking -------------------------------------------------------------------------------------------------
        // $ Additional fire behaviour.
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

        // $ Crystallization.
        // _ Interrupt crystallization.
        ServerPlayNetworking.registerGlobalReceiver(new Identifier("comet", "decrystallize_client"), (server, player, handler, buf, responseSender) ->{
            if (Objects.equals(player.getUuidAsString(), buf.readString()) && !player.isCrystallizedByStatusEffect())
                player.setCrystallizedTicks(0);
        });

        // _ Play decrystallization particle effects.
        ClientPlayNetworking.registerGlobalReceiver(new Identifier("comet", "decrystallization_effects"), (client, handler, buf, responseSender) ->  {
            ClientWorld clientWorld = client.world;
            BlockPos pos = buf.readBlockPos();

            client.execute(() -> {
                if (clientWorld != null){
                    for(int i = 0; i<4; i++){
                        if (i % 2 == 0)
                            clientWorld.addBlockBreakParticles(pos, CometBlocks.CONCENTRATED_END_MEDIUM.getDefaultState());
                        else
                            clientWorld.addBlockBreakParticles(pos.up(), CometBlocks.CONCENTRATED_END_MEDIUM.getDefaultState());
                    }
                }
            });
        });

        // _ Play crystallization growing sound.
        ClientPlayNetworking.registerGlobalReceiver(new Identifier("comet", "crystallization_grows"), (client, handler, buf, responseSender) -> {
            String entityUUID = buf.readString();

            client.execute(()->{
                if (client.world != null){
                    Entity source = ((CometClientWorldExtras)client.world).getEntityByUUID(UUID.fromString(entityUUID));

                    if (source instanceof LivingEntity livingEntity && livingEntity.getCrystallizedTicks() == 20){
                        if (livingEntity instanceof HostileEntity)
                            client.getSoundManager().play(new CrystallizationGrowsSoundInstance(livingEntity, SoundCategory.HOSTILE));
                        else if (livingEntity instanceof PlayerEntity)
                            client.getSoundManager().play(new CrystallizationGrowsSoundInstance(livingEntity, SoundCategory.PLAYERS));
                        else
                            client.getSoundManager().play(new CrystallizationGrowsSoundInstance(livingEntity, SoundCategory.NEUTRAL));
                    }
                }
            });
        });

        // $ Creature statue.
        ClientPlayNetworking.registerGlobalReceiver(new Identifier("comet", "update_statue_texture"), (client, handler, buf, responseSender) -> {
            BlockPos posToUpdate = buf.readBlockPos();
            Item material = Item.byRawId(buf.readVarInt());

            client.execute(() -> {
                CreatureStatue.updateNBT(client, posToUpdate, material);
            });
        });
    }
}