package com.github.Soulphur0.mixin.block;

import com.github.Soulphur0.registries.CometBlocks;
import net.minecraft.block.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChorusFlowerBlock.class)
public abstract class ChorusFlowerBlockMixin extends Block {

    public ChorusFlowerBlockMixin(Settings settings) {
        super(settings);
    }

    // * Injects

    @Final
    @Shadow
    private ChorusPlantBlock plantBlock;

    @Shadow public abstract boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos);

    // ? Capture the local variables that are used to check the blocks below the flower block in randomTick() & canPlaceAt().
    BlockState randomTick_blockState;
    BlockState canPlaceAt_blockState;

    @ModifyVariable(method = "randomTick", at = @At("STORE"), ordinal = 1)
    private BlockState captureBlockState(BlockState state){
        randomTick_blockState = state;
        return state;
    }

    @ModifyVariable(method = "canPlaceAt", at = @At("STORE"), ordinal = 1)
    private BlockState captureBlockStateCanPlaceAt(BlockState state){
        canPlaceAt_blockState = state;
        return state;
    }

    @Inject(cancellable = true, at=@At(value ="HEAD"),  method="canPlaceAt")
    public void canPlaceAtInject(BlockState state, WorldView world, BlockPos pos, CallbackInfoReturnable<Boolean> cir){
        BlockState blockState = world.getBlockState(pos.down());
        if (blockState.isOf(this.plantBlock) || blockState.isOf(Blocks.END_STONE) || blockState.isOf(CometBlocks.CHORUS_HUMUS) || blockState.isOf(CometBlocks.FRESH_CHORUS_HUMUS)) {
            cir.setReturnValue(true);
        }
    }
}