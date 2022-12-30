package com.github.Soulphur0.dimensionalAlloys;

import net.minecraft.entity.Entity;

import java.util.UUID;

public interface CometClientWorldExtras {

    default Entity getEntityByUUID(UUID uuid) { return null; }

}