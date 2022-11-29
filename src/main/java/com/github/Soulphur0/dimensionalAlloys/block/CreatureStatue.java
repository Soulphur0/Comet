package com.github.Soulphur0.dimensionalAlloys.block;

import com.github.Soulphur0.Comet;
import com.github.Soulphur0.dimensionalAlloys.block.entity.CrystallizedCreatureBlockEntity;
import com.github.Soulphur0.registries.CometBlocks;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Waterloggable;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.PickaxeItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class CreatureStatue extends AbstractCrystallizedCreatureBlock implements BlockEntityProvider, Waterloggable {

    public CreatureStatue(Settings settings) {
        super(settings);
    }

    // ? Render type
    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.INVISIBLE;
    }

    // ? Make it not spawn an entity.
    @Override
    public void releaseMob(World world, BlockPos pos) {}

    // ? Make it able to render different textures.
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        Item mainHandItem = player.getMainHandStack().getItem();
        world.getBlockEntity(pos, CometBlocks.CRYSTALLIZED_CREATURE_BLOCK_ENTITY).ifPresent(blockEntity ->{
            if (mainHandItem instanceof PickaxeItem){
                NbtCompound mobData = blockEntity.getMobData();
                mobData.putString("StatueMaterial","none");
            }

            if (mainHandItem == Comet.FRESH_END_MEDIUM_BOTTLE){
                NbtCompound mobData = blockEntity.getMobData();
                mobData.putString("StatueMaterial","end_medium");
            }

            if (mainHandItem == Items.QUARTZ){
                NbtCompound mobData = blockEntity.getMobData();
                mobData.putString("StatueMaterial","quartz");
            }
        });
        return super.onUse(state, world, pos, player, hand, hit);
    }
}
