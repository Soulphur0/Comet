package com.github.Soulphur0.dimensionalAlloys.block;

import com.github.Soulphur0.registries.CometBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

public class FreshRootedEndstoneBlock extends Block {
    public FreshRootedEndstoneBlock(Settings settings) {
        super(settings);
    }

    // $ Update block state on placement or neighbor update.
    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        if (hasSourceAround(ctx.getWorld(), ctx.getBlockPos()))
            ctx.getWorld().setBlockState(ctx.getBlockPos(), CometBlocks.FRESH_ROOTED_ENDSTONE.getDefaultState(),3);
        return ctx.getWorld().getBlockState(ctx.getBlockPos());
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (!hasSourceAround(world, pos))
            world.setBlockState(pos, CometBlocks.DRY_ROOTED_ENDSTONE.getDefaultState(),3);
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    private boolean hasSourceAround(WorldAccess world, BlockPos pos){
        for (Direction direction : Direction.values()){
            if (world.getBlockState(pos.offset(direction)).isOf(CometBlocks.END_END_MEDIUM_DRENCHSTONE) || world.getBlockState(pos.offset(direction)).isOf(CometBlocks.FRESH_ROOTED_ENDSTONE))
                return true;
        }
        return false;
    }

    @Override
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        return new ItemStack(CometBlocks.DRY_ROOTED_ENDSTONE_BLOCK_ITEM);
    }
}
