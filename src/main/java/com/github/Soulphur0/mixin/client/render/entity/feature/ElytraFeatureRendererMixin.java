package com.github.Soulphur0.mixin.client.render.entity.feature;

import com.github.Soulphur0.Comet;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.render.entity.feature.ElytraFeatureRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

@Mixin(ElytraFeatureRenderer.class)
public class ElytraFeatureRendererMixin {

    // _ Elytra item injects.
    // ? When checking for an equipped elytra, return true if a flight-compatible item is equipped.
    @WrapOperation(method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/entity/LivingEntity;FFFFFF)V", at = @At(value ="INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z"))
    private boolean comet_checkForElytraItem(ItemStack instance, Item item, Operation<Boolean> original){
        if (instance.isOf(Comet.ENDBRITE_ELYTRA_CHESTPLATE))
            return true;
        else
            return original.call(instance, item);
    }
}
