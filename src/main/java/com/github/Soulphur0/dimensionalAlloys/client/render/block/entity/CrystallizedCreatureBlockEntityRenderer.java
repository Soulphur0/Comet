package com.github.Soulphur0.dimensionalAlloys.client.render.block.entity;

import com.github.Soulphur0.dimensionalAlloys.block.entity.CrystallizedCreatureBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.math.Vec3f;

public class CrystallizedCreatureBlockEntityRenderer implements BlockEntityRenderer<CrystallizedCreatureBlockEntity> {

    public CrystallizedCreatureBlockEntityRenderer(BlockEntityRendererFactory.Context ctx){

    }

    @Override
    public void render(CrystallizedCreatureBlockEntity entityBlock, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        // * Get mob data from entity block.
        NbtCompound entityBlockData = entityBlock.getMobData();
        NbtCompound mobData = new NbtCompound();

        // * If the entity block has mob data, get mob rotation, else use zero.
        // ! Mob rotation is the rotation of the entity, not the model, so, idle model rotation won't apply.
        float rotation = 0f;
        if (entityBlockData != null) {
            mobData.put("mobData", entityBlockData);
            NbtList rotationInfo = entityBlockData.getList("Rotation",5);
            rotation = rotationInfo.getFloat(0);
        }

        Entity entity = EntityType.loadEntityWithPassengers(mobData.getCompound("mobData"), entityBlock.getWorld(), (loadedEntity) -> loadedEntity);

        if (entity != null){
            entity.setCrystallizedTicks(140);
            matrices.push();
            matrices.translate(0.5D, 0.0D,0.5D);
            matrices.multiply(Vec3f.NEGATIVE_Y.getDegreesQuaternion(rotation));
            MinecraftClient.getInstance().getEntityRenderDispatcher().getRenderer(entity).render(entity,entity.getYaw(),0,matrices,vertexConsumers,light);
            matrices.pop();
        }

    }
}
