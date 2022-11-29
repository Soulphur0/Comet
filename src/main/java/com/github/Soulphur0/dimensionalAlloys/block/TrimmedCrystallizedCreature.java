package com.github.Soulphur0.dimensionalAlloys.block;

import com.github.Soulphur0.Comet;
import com.github.Soulphur0.dimensionalAlloys.block.entity.CrystallizedCreatureBlockEntity;
import com.github.Soulphur0.registries.CometBlocks;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.PickaxeItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class TrimmedCrystallizedCreature extends AbstractCrystallizedCreatureBlock implements BlockEntityProvider, Waterloggable {

    public TrimmedCrystallizedCreature(Settings settings) {
        super(settings);
    }

    // ? Ticking
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, CometBlocks.CRYSTALLIZED_CREATURE_BLOCK_ENTITY, CrystallizedCreatureBlockEntity::tick);
    }

    // ? Render type
    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    // ? Player interaction, turn into baseless block
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        Item mainHandItem = player.getMainHandStack().getItem();
        if (mainHandItem == Comet.FRESH_END_MEDIUM_BOTTLE){
            NbtCompound oldBlockNBT = new NbtCompound();
            world.getBlockEntity(pos, CometBlocks.CRYSTALLIZED_CREATURE_BLOCK_ENTITY).ifPresent((blockEntity) ->{
                oldBlockNBT.put("mobData",blockEntity.getMobData());
            });

            world.setBlockState(pos, CometBlocks.CRYSTALLIZED_CREATURE.getDefaultState());

            world.getBlockEntity(pos, CometBlocks.CRYSTALLIZED_CREATURE_BLOCK_ENTITY).ifPresent((blockEntity) -> {
                blockEntity.writeData(oldBlockNBT);
            });
        }
        return super.onUse(state, world, pos, player, hand, hit);
    }
}
