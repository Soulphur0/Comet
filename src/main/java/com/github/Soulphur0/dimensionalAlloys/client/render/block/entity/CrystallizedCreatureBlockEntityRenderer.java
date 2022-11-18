package com.github.Soulphur0.dimensionalAlloys.client.render.block.entity;

import com.github.Soulphur0.dimensionalAlloys.CrystallizedEntityMethods;
import com.github.Soulphur0.dimensionalAlloys.block.entity.CrystallizedCreatureBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;

public class CrystallizedCreatureBlockEntityRenderer implements BlockEntityRenderer<CrystallizedCreatureBlockEntity> {

    MobEntity mobEntity;
    private static final ItemStack stack = new ItemStack(Items.JUKEBOX, 1);

    public CrystallizedCreatureBlockEntityRenderer(BlockEntityRendererFactory.Context ctx){}

    @Override
    public void render(CrystallizedCreatureBlockEntity entityBlock, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        NbtCompound nbtCompound = new NbtCompound();
        NbtCompound nbtCompound1 = entityBlock.mobData;
        nbtCompound.put("mobData", nbtCompound1);

        Entity entity = EntityType.loadEntityWithPassengers(nbtCompound.getCompound("mobData"), entityBlock.getWorld(), (loadedEntity) -> loadedEntity);

        if (entity != null){
            entity.setCrystallizedTicks(140);
            matrices.push();
            matrices.translate(0.5D, 0.0D,0.5D);
            MinecraftClient.getInstance().getEntityRenderDispatcher().getRenderer(entity).render(entity,entity.getYaw(),0,matrices,vertexConsumers,light);
            matrices.pop();
        }


    }
}
