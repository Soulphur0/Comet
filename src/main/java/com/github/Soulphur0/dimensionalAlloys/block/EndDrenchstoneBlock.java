package com.github.Soulphur0.dimensionalAlloys.block;

import com.github.Soulphur0.Comet;
import com.github.Soulphur0.registries.CometBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.StateManager;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

public class EndDrenchstoneBlock extends Block {

    public EndDrenchstoneBlock(Settings settings) {
        super(settings);
    }

    // $ Fill with bucket on use.


    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack handItemStack = player.getStackInHand(hand);
        
        // + Replace item.
        if(handItemStack.isOf(Items.WATER_BUCKET) || handItemStack.isOf(Items.LAVA_BUCKET) || handItemStack.isOf(CometBlocks.CONCENTRATED_END_MEDIUM_BUCKET)){
            // - Replace block.
            if (handItemStack.isOf(Items.WATER_BUCKET))
                world.setBlockState(pos, CometBlocks.END_WATER_DRENCHSTONE.getDefaultState());
            else if (handItemStack.isOf(Items.LAVA_BUCKET))
                world.setBlockState(pos, CometBlocks.END_LAVA_DRENCHSTONE.getDefaultState());
            else if (handItemStack.isOf(CometBlocks.CONCENTRATED_END_MEDIUM_BUCKET))
                world.setBlockState(pos, CometBlocks.END_END_MEDIUM_DRENCHSTONE.getDefaultState());

            // - Remove item if the player is not on creative mode.
            if (!player.isCreative())
                player.getMainHandStack().decrement(1);

            // - If the hand is free, add an empty bucket.
            if (handItemStack.isEmpty()){
                player.setStackInHand(hand, new ItemStack(Items.BUCKET));
            // - If it is not free, try to shove it into the inventory, drop it otherwise.
            } else if (!player.getInventory().insertStack(new ItemStack(Items.BUCKET))) {
                player.dropItem(new ItemStack(Items.BUCKET), false);
            }
        }

        return super.onUse(state, world, pos, player, hand, hit);
    }

    // $ Check for sources on placement and on neighbor update.
    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        checkForSources(world, pos, state);
        super.onBlockAdded(state, world, pos, oldState, notify);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        checkForSources(world, pos, state);
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    // $ Fill with surrounding fluids.
    private void checkForSources(WorldAccess world, BlockPos pos, BlockState state){
        Direction[] directions = {Direction.UP, Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST};
        for (int i = 0; i<5;i++){
            // + Check for water.
            if (world.getBlockState(pos.offset(directions[i])).getBlock() == Blocks.WATER){
                // - Only take in source blocks.
                if (world.getBlockState(pos.offset(directions[i])).get(FluidBlock.LEVEL) == 0){
                    // Remove source.
                    if (state.getBlock() == CometBlocks.END_DRENCHSTONE){
                        world.setBlockState(pos.offset(directions[i]), Blocks.AIR.getDefaultState(),2);
                        world.setBlockState(pos, CometBlocks.END_WATER_DRENCHSTONE.getDefaultState(),2);
                    }
                }
            // + Check for lava.
            } else if (world.getBlockState(pos.offset(directions[i])).getBlock() == Blocks.LAVA){
                // - Only take in source blocks.
                if (world.getBlockState(pos.offset(directions[i])).get(FluidBlock.LEVEL) == 0){
                    // Remove source.
                    if (state.getBlock() == CometBlocks.END_DRENCHSTONE){
                        world.setBlockState(pos.offset(directions[i]), Blocks.AIR.getDefaultState(),2);
                        world.setBlockState(pos, CometBlocks.END_LAVA_DRENCHSTONE.getDefaultState(),2);
                    }
                }
            // + Check for concentrated end medium.
            } else if (world.getBlockState(pos.offset(directions[i])).getBlock() == CometBlocks.CONCENTRATED_END_MEDIUM){
                // Remove source.
                if (state.getBlock() == CometBlocks.END_DRENCHSTONE){
                    world.setBlockState(pos.offset(directions[i]), Blocks.AIR.getDefaultState(),2);
                    world.setBlockState(pos, CometBlocks.END_END_MEDIUM_DRENCHSTONE.getDefaultState(),2);
                }
            }
        }
    }
}