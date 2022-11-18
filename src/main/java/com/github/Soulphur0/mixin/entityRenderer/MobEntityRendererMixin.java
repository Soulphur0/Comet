package com.github.Soulphur0.mixin.entityRenderer;

import com.github.Soulphur0.dimensionalAlloys.client.render.entity.feature.CrystallizationFeatureRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MobEntityRenderer.class)
public class MobEntityRendererMixin<T extends MobEntity, M extends EntityModel<T>> extends LivingEntityRenderer<T, M> {


    public MobEntityRendererMixin(EntityRendererFactory.Context ctx, M model, float shadowRadius) {
        super(ctx, model, shadowRadius);
    }

    @Override
    public Identifier getTexture(T entity) {
        return null;
    }

    @Inject(method="<init>", at = @At(value = "TAIL"))
    public void addCometFeatureRenderer(EntityRendererFactory.Context context, M entityModel, float f, CallbackInfo ci){
        this.addFeature(new CrystallizationFeatureRenderer(this));
    }
}
