package com.github.SoulArts.mixin;

import com.github.SoulArts.Comet;
import net.minecraft.block.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
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
    public void canPlaceAtReturnZero(BlockView world, BlockPos pos, CallbackInfoReturnable<BlockState> cir) {
        BlockState blockState = world.getBlockState(pos.down());
        BlockState blockState2 = world.getBlockState(pos.up());
        BlockState blockState3 = world.getBlockState(pos.north());
        BlockState blockState4 = world.getBlockState(pos.east());
        BlockState blockState5 = world.getBlockState(pos.south());
        BlockState blockState6 = world.getBlockState(pos.west());
        cir.setReturnValue((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.getDefaultState().
                with(DOWN, blockState.isOf(this) || blockState.isOf(Blocks.CHORUS_FLOWER) || blockState.isOf(Blocks.END_STONE) || blockState.isOf(Comet.CHORUS_HUMUS) || blockState.isOf(Comet.CHORUS_HUMUS2))).
                with(UP, blockState2.isOf(this) || blockState2.isOf(Blocks.CHORUS_FLOWER))).
                with(NORTH, blockState3.isOf(this) || blockState3.isOf(Blocks.CHORUS_FLOWER))).
                with(EAST, blockState4.isOf(this) || blockState4.isOf(Blocks.CHORUS_FLOWER))).
                with(SOUTH, blockState5.isOf(this) || blockState5.isOf(Blocks.CHORUS_FLOWER))).
                with(WEST, blockState6.isOf(this) || blockState6.isOf(Blocks.CHORUS_FLOWER)));
    }

    @Inject(cancellable = true, at = @At(value = "HEAD"), method = "canPlaceAt")
    public void canPlaceAtInject(BlockState state, WorldView world, BlockPos pos, CallbackInfoReturnable<Boolean> cir){
        BlockState blockState = world.getBlockState(pos.down());
        if (blockState.isOf(Blocks.END_STONE) || blockState.isOf(Comet.CHORUS_HUMUS) || blockState.isOf(Comet.CHORUS_HUMUS2))
            cir.setReturnValue(true);
    }
}