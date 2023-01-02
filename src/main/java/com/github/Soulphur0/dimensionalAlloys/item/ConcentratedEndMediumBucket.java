package com.github.Soulphur0.dimensionalAlloys.item;

import com.github.Soulphur0.Comet;
import com.github.Soulphur0.dimensionalAlloys.sound.CometSoundUtilities;
import com.github.Soulphur0.registries.CometBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeveledCauldronBlock;
import net.minecraft.client.render.CameraSubmersionType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/** @see net.minecraft.item.PowderSnowBucketItem */
public class ConcentratedEndMediumBucket extends BlockItem {

    public ConcentratedEndMediumBucket(Block block, Settings settings) {
        super(block, settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if (context.getWorld().getBlockState(context.getBlockPos()).getBlock() == CometBlocks.END_END_MEDIUM_DRENCHSTONE){
            context.getWorld().playSound(context.getBlockPos().getX(), context.getBlockPos().getY(), context.getBlockPos().getZ(), Comet.CONCENTRATED_END_MEDIUM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0f, 1.0f, true);
            return ActionResult.SUCCESS;
        }

        ActionResult actionResult = super.useOnBlock(context);
        PlayerEntity playerEntity = context.getPlayer();
        if (actionResult.isAccepted() && playerEntity != null && !playerEntity.isCreative()) {
            Hand hand = context.getHand();
            playerEntity.setStackInHand(hand, Items.BUCKET.getDefaultStack());
        }

        context.getWorld().playSound(context.getBlockPos().getX(), context.getBlockPos().getY(), context.getBlockPos().getZ(), Comet.CONCENTRATED_END_MEDIUM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0f, 1.0f, true);
        return actionResult;
    }
}
