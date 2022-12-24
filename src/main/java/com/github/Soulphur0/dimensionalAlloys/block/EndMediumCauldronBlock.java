package com.github.Soulphur0.dimensionalAlloys.block;

import com.github.Soulphur0.Comet;
import com.github.Soulphur0.registries.CometBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeveledCauldronBlock;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import java.util.Map;
import java.util.function.Predicate;

public class EndMediumCauldronBlock extends LeveledCauldronBlock {

    public EndMediumCauldronBlock(Settings settings, Predicate<Biome.Precipitation> precipitationPredicate, Map<Item, CauldronBehavior> behaviorMap) {
        super(settings, precipitationPredicate, behaviorMap);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack mainHandItemStack = player.getStackInHand(hand);
        // ? Fill the bottle.
        if (mainHandItemStack.isOf(Items.GLASS_BOTTLE)){
            if (!player.isCreative())
                player.getMainHandStack().decrement(1);
            if (mainHandItemStack.isEmpty()){
                player.setStackInHand(hand, new ItemStack(Comet.CONCENTRATED_END_MEDIUM_BOTTLE));
            } else if (!player.getInventory().insertStack(new ItemStack(Comet.CONCENTRATED_END_MEDIUM_BOTTLE))) {
                player.dropItem(new ItemStack(Comet.CONCENTRATED_END_MEDIUM_BOTTLE), false);
            }
            // + Decrement cauldron level.
            LeveledCauldronBlock.decrementFluidLevel(state, world, pos);
        // ? Empty the bottle.
        } else if (mainHandItemStack.isOf(Comet.CONCENTRATED_END_MEDIUM_BOTTLE)){
            if (!player.isCreative())
                player.getMainHandStack().decrement(1);
            if (mainHandItemStack.isEmpty()){
                player.setStackInHand(hand, new ItemStack(Items.GLASS_BOTTLE));
            } else if (!player.getInventory().insertStack(new ItemStack(Items.GLASS_BOTTLE))) {
                player.dropItem(new ItemStack(Items.GLASS_BOTTLE), false);
            }
            // + Increment cauldron level.
            if (state.get(LEVEL) < 3)
                world.setBlockState(pos, CometBlocks.END_MEDIUM_CAULDRON.getDefaultState().with(LEVEL, state.get(LEVEL) + 1));
        } else if (mainHandItemStack.isOf(Items.BUCKET)){
            if (state.get(LEVEL) == 3){
                if (!player.isCreative())
                    player.getMainHandStack().decrement(1);
                if (mainHandItemStack.isEmpty()){
                    player.setStackInHand(hand, new ItemStack(CometBlocks.CONCENTRATED_END_MEDIUM_BUCKET));
                } else if (!player.getInventory().insertStack(new ItemStack(CometBlocks.CONCENTRATED_END_MEDIUM_BUCKET))) {
                    player.dropItem(new ItemStack(CometBlocks.CONCENTRATED_END_MEDIUM_BUCKET), false);
                }
                // + Empty cauldron.
                world.setBlockState(pos, Blocks.CAULDRON.getDefaultState());
            }
        }
        return super.onUse(state, world, pos, player, hand, hit);
    }
}
