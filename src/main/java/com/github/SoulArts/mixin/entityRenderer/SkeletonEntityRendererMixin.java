package com.github.SoulArts.mixin.entityRenderer;

import com.github.SoulArts.CometClient;
import com.github.SoulArts.dimensionalAlloys.CometArmorFeatureRenderer;
import com.github.SoulArts.dimensionalAlloys.armorModel.endbriteArmor.EndbriteArmorModel2;
import net.minecraft.client.render.entity.BipedEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.SkeletonEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.SkeletonEntityModel;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SkeletonEntityRenderer.class)
public class SkeletonEntityRendererMixin extends BipedEntityRenderer<AbstractSkeletonEntity, SkeletonEntityModel<AbstractSkeletonEntity>> {

    // * INHERITED -----------------------------------------------------------------------------------------------------

    private static final Identifier TEXTURE = new Identifier("textures/entity/skeleton/skeleton.png");

    public SkeletonEntityRendererMixin(EntityRendererFactory.Context ctx, SkeletonEntityModel<AbstractSkeletonEntity> model, float shadowRadius) {
        super(ctx, model, shadowRadius);
    }

    @Override
    public Identifier getTexture(AbstractSkeletonEntity abstractSkeletonEntity) {
        return TEXTURE;
    }

    // * INJECTED METHODS ----------------------------------------------------------------------------------------------

    // ? Get renderer context from the armor feature renderer.

    EntityRendererFactory.Context context;

    @ModifyVariable(method="<init>(Lnet/minecraft/client/render/entity/EntityRendererFactory$Context;Lnet/minecraft/client/render/entity/model/EntityModelLayer;Lnet/minecraft/client/render/entity/model/EntityModelLayer;Lnet/minecraft/client/render/entity/model/EntityModelLayer;)V", at = @At(value="TAIL", ordinal = 0), argsOnly = true)
    private EntityRendererFactory.Context getContext(EntityRendererFactory.Context context){
        this.context = context;
        return context;
    }

    // ? Add custom feature renderer to entity using the previously extracted context.
    @Inject(method = "<init>(Lnet/minecraft/client/render/entity/EntityRendererFactory$Context;Lnet/minecraft/client/render/entity/model/EntityModelLayer;Lnet/minecraft/client/render/entity/model/EntityModelLayer;Lnet/minecraft/client/render/entity/model/EntityModelLayer;)V", at = @At(value = "TAIL"))
    public void addCometFeatureRenderer(EntityRendererFactory.Context ctx, EntityModelLayer layer, EntityModelLayer legArmorLayer, EntityModelLayer bodyArmorLayer, CallbackInfo ci){
        this.addFeature(new CometArmorFeatureRenderer(this,
                new EndbriteArmorModel2(context.getPart(CometClient.ENDBRITE_ARMOR_MODEL_LAYER))
        ));
    }
}
