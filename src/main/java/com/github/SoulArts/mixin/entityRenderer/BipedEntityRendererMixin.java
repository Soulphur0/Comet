package com.github.SoulArts.mixin.entityRenderer;

import com.github.SoulArts.CometClient;
import com.github.SoulArts.dimensionalAlloys.CometArmorFeatureRenderer;
import com.github.SoulArts.dimensionalAlloys.armorModel.endbriteArmor.EndbriteChestplateModel;
import com.github.SoulArts.dimensionalAlloys.armorModel.endbriteArmor.EndbriteHelmetModel;
import net.minecraft.client.render.entity.BipedEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.PiglinEntityModel;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BipedEntityRenderer.class)
public class BipedEntityRendererMixin<T extends MobEntity, M extends BipedEntityModel<T>> extends MobEntityRenderer<T, M> {

    // * INHERITED -----------------------------------------------------------------------------------------------------
    private static final Identifier TEXTURE = new Identifier("textures/entity/steve.png");

    public BipedEntityRendererMixin(EntityRendererFactory.Context context, M entityModel, float f) {
        super(context, entityModel, f);
    }

    @Override
    public Identifier getTexture(T entity) {
        return TEXTURE;
    }

    // * INJECTED METHODS ----------------------------------------------------------------------------------------------

    // ? Get renderer context from the armor feature renderer.

    EntityRendererFactory.Context context;

    @ModifyVariable(method="<init>(Lnet/minecraft/client/render/entity/EntityRendererFactory$Context;Lnet/minecraft/client/render/entity/model/BipedEntityModel;F)V", at = @At(value="TAIL", ordinal = 0), argsOnly = true)
    private EntityRendererFactory.Context getContext(EntityRendererFactory.Context context){
        this.context = context;
        return context;
    }

    // ? Add custom feature renderer to entity using the previously extracted context.
    @Inject(method = "<init>(Lnet/minecraft/client/render/entity/EntityRendererFactory$Context;Lnet/minecraft/client/render/entity/model/BipedEntityModel;FFFF)V", at = @At(value="TAIL", target="<init>(Lnet/minecraft/client/render/entity/EntityRendererFactory$Context;Lnet/minecraft/client/render/entity/model/BipedEntityModel;F)V"))
    public void addCometFeatureRenderer(EntityRendererFactory.Context ctx, BipedEntityModel<net.minecraft.entity.LivingEntity> model, float shadowRadius, float scaleX, float scaleY, float scaleZ, CallbackInfo ci){
        this.addFeature(new CometArmorFeatureRenderer(this,
                new EndbriteHelmetModel(context.getPart(CometClient.ENDBRITE_HELMET_MODEL_LAYER)),
                new EndbriteChestplateModel(context.getPart(CometClient.ENDBRITE_CHESTPLATE_MODEL_LAYER))
        ));
    }
}
