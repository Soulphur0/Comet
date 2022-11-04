package com.github.SoulArts.mixin;

import com.github.SoulArts.dimensionalAlloys.CometArmorFeatureRenderer;
import net.minecraft.client.render.entity.ArmorStandEntityRenderer;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.ArmorStandArmorEntityModel;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ArmorStandEntityRenderer.class)
public class ArmorStandEntityRendererMixin extends LivingEntityRenderer<ArmorStandEntity, ArmorStandArmorEntityModel> {

    public ArmorStandEntityRendererMixin(EntityRenderDispatcher dispatcher, ArmorStandArmorEntityModel model, float shadowRadius) {
        super(dispatcher, model, shadowRadius);
    }

    @Override
    public Identifier getTexture(ArmorStandEntity entity) {
        return new Identifier("textures/entity/armorstand/wood.png");
    }

    // INJECTS

    // ENDBRITE HELMET
    @Inject(at = @At(value = "TAIL", target = "Lnet/minecraft/client/render/entity/ArmorStandEntityRenderer;<init>(Lnet/minecraft/client/render/entity/EntityRenderDispatcher;)V"), method = "<init>(Lnet/minecraft/client/render/entity/EntityRenderDispatcher;)V")
    public void addCometFeatureRenderer(EntityRenderDispatcher dispatcher, CallbackInfo ci){
        this.addFeature(new CometArmorFeatureRenderer(this));
    }
}
