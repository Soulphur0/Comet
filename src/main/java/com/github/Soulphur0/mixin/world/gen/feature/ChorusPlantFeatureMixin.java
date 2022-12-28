package com.github.Soulphur0.mixin.world.gen.feature;

import com.github.Soulphur0.registries.CometBlocks;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.world.gen.feature.ChorusPlantFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

@Mixin(ChorusPlantFeature.class)
public class ChorusPlantFeatureMixin {

    @WrapOperation(method = "generate", at = @At(value = "INVOKE", target ="Lnet/minecraft/block/BlockState;isOf(Lnet/minecraft/block/Block;)Z"))
    private boolean comet_overrideFeaturePlacementBlockCheck(BlockState state, Block block, Operation<Boolean> original){
        return state.isOf(CometBlocks.CHORUS_HUMUS) || state.isOf(CometBlocks.FRESH_CHORUS_HUMUS);
    }
}
