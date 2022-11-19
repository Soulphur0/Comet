package com.github.Soulphur0.dimensionalAlloys.block.entity;

import com.github.Soulphur0.registries.CometBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EndIronOreBlockEntity extends BlockEntity {

    public EndIronOreBlockEntity(BlockPos pos, BlockState state) {
        super(CometBlocks.END_IRON_ORE_BLOCK_ENTITY,pos, state);
    }
}
