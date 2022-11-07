package com.github.SoulArts.mixin.entityRenderer;

import com.github.SoulArts.CometClient;
import com.github.SoulArts.dimensionalAlloys.CometArmorFeatureRenderer;
import com.github.SoulArts.dimensionalAlloys.armorModel.endbriteArmor.EndbriteArmorModel2;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ZombieBaseEntityRenderer;
import net.minecraft.client.render.entity.ZombieEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.ZombieEntityModel;
import net.minecraft.entity.mob.ZombieEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ZombieEntityRenderer.class)
public class ZombieEntityRendererMixin extends ZombieBaseEntityRenderer<ZombieEntity, ZombieEntityModel<ZombieEntity>> {

    // * INHERITED -----------------------------------------------------------------------------------------------------

    protected ZombieEntityRendererMixin(EntityRendererFactory.Context ctx, ZombieEntityModel<ZombieEntity> bodyModel, ZombieEntityModel<ZombieEntity> legsArmorModel, ZombieEntityModel<ZombieEntity> bodyArmorModel) {
        super(ctx, bodyModel, legsArmorModel, bodyArmorModel);
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
    public void addCometFeatureRenderer(EntityRendererFactory.Context ctx, EntityModelLayer layer, EntityModelLayer legsArmorLayer, EntityModelLayer bodyArmorLayer, CallbackInfo ci){
        this.addFeature(new CometArmorFeatureRenderer(this,
                new EndbriteArmorModel2(context.getPart(CometClient.ENDBRITE_ARMOR_MODEL_LAYER))
        ));
    }
}
