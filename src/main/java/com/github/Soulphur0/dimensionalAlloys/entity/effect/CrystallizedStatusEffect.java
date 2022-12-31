package com.github.Soulphur0.dimensionalAlloys.entity.effect;

import io.netty.buffer.ByteBuf;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class CrystallizedStatusEffect extends StatusEffect {

    public CrystallizedStatusEffect(){
        super(StatusEffectCategory.NEUTRAL, 0x7330a9);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        entity.setCrystallizedByStatusEffect(true);
        entity.setCrystallizedTicks(140);
        super.applyUpdateEffect(entity, amplifier);
    }

    @Override
    public void onRemoved(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        entity.setCrystallizedByStatusEffect(false);

        // . Notify the client player that it is no longer crystallized by an effect.
        World world = entity.getWorld();
        if (world != null){
            PacketByteBuf effectEndNotification = PacketByteBufs.create().writeString(entity.getUuidAsString());
            for (ServerPlayerEntity serverPlayer : PlayerLookup.tracking((ServerWorld) world, entity.getBlockPos())) {
                ServerPlayNetworking.send((ServerPlayerEntity) serverPlayer, new Identifier("comet", "crystallization_effect_ended"), effectEndNotification);
            }
        }

        if (!entity.isInFreshEndMedium())
            entity.setCrystallizedTicks(0);
    }
}
