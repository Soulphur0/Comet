package com.github.Soulphur0.mixin.client.network;

import com.github.Soulphur0.Comet;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.input.Input;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin {

    @Shadow
    public Input input;

    // _ Crystallization injects.
    // . If the player gives an input, send a packet to the server for it to de-crystallize.
    @Inject(method = "tickMovement", at = @At("HEAD"))
    private void comet_interruptCrystallization(CallbackInfo ci){
        if (input.pressingForward || input.pressingBack || input.pressingLeft || input.pressingRight || input.jumping){
            PacketByteBuf decrystallizationRequest = PacketByteBufs.create().writeString(((ClientPlayerEntity)(Object)this).getUuidAsString());
            ClientPlayNetworking.send(new Identifier("comet","decrystallize_client"), decrystallizationRequest);
        }
    }

    // _ Elytra item injects.
    // ? When checking for an equipped elytra, return true if a flight-compatible item is equipped.
    @WrapOperation(method = "tickMovement", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z"))
    private boolean comet_checkForElytraItem(ItemStack instance, Item item, Operation<Boolean> original){
        if (instance.isOf(Comet.ENDBRITE_ELYTRA_CHESTPLATE)){
            return true;
        } else {
            return original.call(instance, item);
        }
    }
}
