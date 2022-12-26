package com.github.Soulphur0.dimensionalAlloys.block;

import com.github.Soulphur0.Comet;
import com.github.Soulphur0.registries.CometBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EndEndMediumDrenchstoneBlock extends EndDrenchstoneBlock{
    public EndEndMediumDrenchstoneBlock(Settings settings) {
        super(settings);
    }

    // $ Get fluid from block.
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack handItemStack = player.getStackInHand(hand);
        if(handItemStack.isOf(Items.GLASS_BOTTLE)){
            if (!player.isCreative())
                player.getMainHandStack().decrement(1);
            if (handItemStack.isEmpty()){
                player.setStackInHand(hand, new ItemStack(Comet.CONCENTRATED_END_MEDIUM_BOTTLE));
            } else if (!player.getInventory().insertStack(new ItemStack(Comet.CONCENTRATED_END_MEDIUM_BOTTLE))) {
                player.dropItem(new ItemStack(Comet.CONCENTRATED_END_MEDIUM_BOTTLE), false);
            }
            world.setBlockState(pos, CometBlocks.END_DRENCHSTONE.getDefaultState());
        }
        return ActionResult.PASS;
    }
}
