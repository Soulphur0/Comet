package com.github.Soulphur0.mixin.item;

import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockItem.class)
public class BlockItemMixin {

    // ? Make crystallized players unable to place blocks.
    @Inject(method = "place(Lnet/minecraft/item/ItemPlacementContext;)Lnet/minecraft/util/ActionResult;", at = @At("HEAD"), cancellable = true)
    private void cancelPlacement(ItemPlacementContext context, CallbackInfoReturnable<ActionResult> cir){
        if (context.getPlayer() != null){
            if (context.getPlayer().isCrystallized()){
                cir.setReturnValue(ActionResult.FAIL);
            }
        }

    }
}
