package com.github.SoulArts.mixin;

import com.github.SoulArts.CometClient;
import com.github.SoulArts.dimensionalAlloys.CometArmorFeatureRenderer;
import com.github.SoulArts.dimensionalAlloys.armorModel.endbriteArmor.EndbriteHelmetModel;
import net.fabricmc.fabric.mixin.client.rendering.EntityModelLayersAccessor;
import net.minecraft.client.render.entity.ArmorStandEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.ArmorStandArmorEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Iterator;

@Mixin(ArmorStandEntityRenderer.class)
public class ArmorStandEntityRendererMixin extends LivingEntityRenderer<ArmorStandEntity, ArmorStandArmorEntityModel> {

    // * INHERITED METHODS ---------------------------------------------------------------------------------------------
    public ArmorStandEntityRendererMixin(EntityRendererFactory.Context ctx, ArmorStandArmorEntityModel model, float shadowRadius) {
        super(ctx, model, shadowRadius);
    }

    // ! This needs to return a texture, auto generated method does not do this by default.
    @Override
    public Identifier getTexture(ArmorStandEntity entity) {
        return new Identifier("textures/entity/armorstand/wood.png");
    }

    // * INJECTED METHODS ----------------------------------------------------------------------------------------------
    // ? Get renderer context from the armor feature renderer.
    // ? Add custom feature renderer to armor stand using the previously extracted context.

    EntityRendererFactory.Context context;

    @ModifyVariable(method = "<init>(Lnet/minecraft/client/render/entity/EntityRendererFactory$Context;)V", at = @At(value = "TAIL", ordinal = 0), argsOnly = true)
    private EntityRendererFactory.Context getContext(EntityRendererFactory.Context context){
        this.context = context;
        return context;
    }

    // ENDBRITE HELMET
    @Inject(method = "<init>(Lnet/minecraft/client/render/entity/EntityRendererFactory$Context;)V", at = @At(value = "TAIL", target = "Lnet/minecraft/client/render/entity/ArmorStandEntityRenderer;<init>(Lnet/minecraft/client/render/entity/EntityRendererFactory$Context;)V"))
    public void addCometFeatureRenderer(CallbackInfo ci){
        this.addFeature(new CometArmorFeatureRenderer(this, new EndbriteHelmetModel(context.getPart(CometClient.ENDBRITE_HELMET_MODEL_LAYER))));
    }
}
