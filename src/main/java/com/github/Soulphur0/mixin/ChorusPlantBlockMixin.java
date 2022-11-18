package com.github.Soulphur0.mixin;

import com.github.Soulphur0.registries.CometBlocks;
import net.minecraft.block.*;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChorusPlantBlock.class)
public class ChorusPlantBlockMixin extends ConnectingBlock {

    public ChorusPlantBlockMixin(float radius, Settings settings) {
        super(radius, settings);
    }

    // * Injects

    @Inject(cancellable = true, at = @At(value = "RETURN", ordinal = 0), method = "withConnectionProperties")
    public void withConnectionPropertiesInject(BlockView world, BlockPos pos, CallbackInfoReturnable<BlockState> cir) {
        BlockState blockState = world.getBlockState(pos.down());
        BlockState blockState2 = world.getBlockState(pos.up());
        BlockState blockState3 = world.getBlockState(pos.north());
        BlockState blockState4 = world.getBlockState(pos.east());
        BlockState blockState5 = world.getBlockState(pos.south());
        BlockState blockState6 = world.getBlockState(pos.west());
        cir.setReturnValue((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.getDefaultState().
                with(DOWN, blockState.isOf(this) || blockState.isOf(Blocks.CHORUS_FLOWER) || blockState.isOf(Blocks.END_STONE) || blockState.isOf(CometBlocks.CHORUS_HUMUS) || blockState.isOf(CometBlocks.FRESH_CHORUS_HUMUS))).
                with(UP, blockState2.isOf(this) || blockState2.isOf(Blocks.CHORUS_FLOWER))).
                with(NORTH, blockState3.isOf(this) || blockState3.isOf(Blocks.CHORUS_FLOWER))).
                with(EAST, blockState4.isOf(this) || blockState4.isOf(Blocks.CHORUS_FLOWER))).
                with(SOUTH, blockState5.isOf(this) || blockState5.isOf(Blocks.CHORUS_FLOWER))).
                with(WEST, blockState6.isOf(this) || blockState6.isOf(Blocks.CHORUS_FLOWER)));
    }

    @Inject(cancellable = true, at = @At(value = "HEAD"), method="getStateForNeighborUpdate")
    private void getStateForNeightborUpdateInject(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos, CallbackInfoReturnable<BlockState> cir){
        if (!state.canPlaceAt(world, pos)) {
            world.createAndScheduleBlockTick(pos, this, 1);
            cir.setReturnValue(super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos));
        }
        boolean bl = neighborState.isOf(this) || neighborState.isOf(Blocks.CHORUS_FLOWER) || (direction == Direction.DOWN && (neighborState.isOf(Blocks.END_STONE) || neighborState.isOf(CometBlocks.CHORUS_HUMUS) || neighborState.isOf(CometBlocks.FRESH_CHORUS_HUMUS)));
        cir.setReturnValue((BlockState)state.with((Property)FACING_PROPERTIES.get(direction), bl));
    }

    @Inject(cancellable = true, at = @At(value = "HEAD"), method = "canPlaceAt")
    public void canPlaceAtInject(BlockState state, WorldView world, BlockPos pos, CallbackInfoReturnable<Boolean> cir){
        BlockState blockState = world.getBlockState(pos.down());
        if (blockState.isOf(Blocks.END_STONE) || blockState.isOf(CometBlocks.CHORUS_HUMUS) || blockState.isOf(CometBlocks.FRESH_CHORUS_HUMUS))
            cir.setReturnValue(true);
    }
}