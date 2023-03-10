package com.github.Soulphur0.mixin.block;

import com.github.Soulphur0.Comet;
import com.github.Soulphur0.registries.CometBlocks;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


import java.util.Map;

@Mixin(CauldronBehavior.class)
public interface CauldronBehaviorMixin {

    @Inject(method="registerBucketBehavior", at=@At("TAIL"))
    private static void comet_registerBucketBehaviors(Map<Item, CauldronBehavior> behavior, CallbackInfo ci){
        behavior.put(CometBlocks.CONCENTRATED_END_MEDIUM_BUCKET, Comet.FILL_WITH_END_MEDIUM);
        behavior.put(Comet.CONCENTRATED_END_MEDIUM_BOTTLE, Comet.FILL_WITH_END_MEDIUM_BOTTLE);

        behavior.put(Items.BUCKET, Comet.EMPTY_WITH_BUCKET);
        behavior.put(Items.GLASS_BOTTLE, Comet.EMPTY_WITH_BOTTLE);
    }
}
