package com.github.Soulphur0.mixin.client.world;

import com.github.Soulphur0.dimensionalAlloys.CometClientWorldExtras;
import net.minecraft.client.world.ClientEntityManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.UUID;

@Mixin(ClientWorld.class)
public class ClientWorldMixin implements CometClientWorldExtras {

    @Shadow
    @Final
    private ClientEntityManager<Entity> entityManager;

    @Override
    public Entity getEntityByUUID(UUID uuid) {
        return this.entityManager.getLookup().get(uuid);
    }
}
