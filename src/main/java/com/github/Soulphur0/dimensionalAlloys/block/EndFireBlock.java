package com.github.Soulphur0.dimensionalAlloys.block;

import com.github.Soulphur0.registries.CometBlocks;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public class EndFireBlock extends AbstractFireBlock {
    public EndFireBlock(Settings settings) {
        super(settings, 1.0f);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (this.canPlaceAt(state, world, pos)) {
            return this.getDefaultState();
        }
        return Blocks.AIR.getDefaultState();
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (!entity.isFireImmune()) {
            // + Set end fire ticks to 1 on-contact, in order to not render 1 regular fire hud frame.
            if (entity.getEndFireTicks() == -20)
                entity.setEndFireTicks(1);
            else
                entity.setEndFireTicks(entity.getFireTicks());

            entity.setSoulFireTicks(-20);
        }

        super.onEntityCollision(state, world, pos, entity);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return world.getBlockState(pos.down()) == CometBlocks.END_MEDIUM.getDefaultState();
    }

    @Override
    protected boolean isFlammable(BlockState state) {
        return true;
    }
}
