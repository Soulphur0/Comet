package com.github.Soulphur0.mixin.entityModel;

import com.github.Soulphur0.dimensionalAlloys.EntityCometBehaviour;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntityModel.class)
public class PlayerEntityModelMixin<T extends LivingEntity>  extends BipedEntityModel<T> {

    public PlayerEntityModelMixin(ModelPart root) {
        super(root);
    }

    // $ Injected ------------------------------------------------------------------------------------------------------

    // ? Cancels animations if the player is crystallized, but it does it before the LivingEntityRendererMixin cancel so limb angles are respected and the player is frozen in action.
    @Inject(method = "setAngles(Lnet/minecraft/entity/LivingEntity;FFFFF)V", at = @At("HEAD"), cancellable = true)
    public void freezeAnimations(T livingEntity, float f, float g, float h, float i, float j, CallbackInfo ci){
        if (((EntityCometBehaviour)livingEntity).isCrystallized()){
            ci.cancel();
        }
    }
}
