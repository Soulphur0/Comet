package com.github.SoulArts.mixin.entityRenderer;

import com.github.SoulArts.CometClient;
import com.github.SoulArts.dimensionalAlloys.client.render.entity.feature.CometArmorFeatureRenderer;
import com.github.SoulArts.dimensionalAlloys.armorModel.endbriteArmor.EndbriteArmorModel;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.SkeletonEntityRenderer;
import net.minecraft.client.render.entity.StrayEntityRenderer;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(StrayEntityRenderer.class)
public class StrayEntityRendererMixin extends SkeletonEntityRenderer {

    // * INHERITED -----------------------------------------------------------------------------------------------------

    private static final Identifier TEXTURE = new Identifier("textures/entity/skeleton/stray.png");

    public StrayEntityRendererMixin(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public Identifier getTexture(AbstractSkeletonEntity abstractSkeletonEntity) {
        return TEXTURE;
    }

    // * INJECTED METHODS ----------------------------------------------------------------------------------------------

    // ? Get renderer context from the armor feature renderer.

    EntityRendererFactory.Context context;

    @ModifyVariable(method="<init>", at = @At(value="TAIL", ordinal = 0), argsOnly = true)
    private EntityRendererFactory.Context getContext(EntityRendererFactory.Context context){
        this.context = context;
        return context;
    }

    // ? Add custom feature renderer to entity using the previously extracted context.
    @Inject(method = "<init>", at = @At(value = "TAIL"))
    public void addCometFeatureRenderer(EntityRendererFactory.Context context, CallbackInfo ci){
        this.addFeature(new CometArmorFeatureRenderer(this,
                new EndbriteArmorModel(context.getPart(CometClient.ENDBRITE_ARMOR_MODEL_LAYER))
        ));
    }
}
