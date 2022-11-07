package com.github.SoulArts.mixin.entityRenderer;

import com.github.SoulArts.CometClient;
import com.github.SoulArts.dimensionalAlloys.CometArmorFeatureRenderer;
import com.github.SoulArts.dimensionalAlloys.armorModel.endbriteArmor.EndbriteArmorModel2;
import net.minecraft.client.render.entity.DrownedEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ZombieBaseEntityRenderer;
import net.minecraft.client.render.entity.feature.DrownedOverlayFeatureRenderer;
import net.minecraft.client.render.entity.model.DrownedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.mob.DrownedEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DrownedEntityRenderer.class)
public class DrownedEntityRendererMixin extends ZombieBaseEntityRenderer<DrownedEntity, DrownedEntityModel<DrownedEntity>> {

    // * INHERITED -----------------------------------------------------------------------------------------------------

    private static final Identifier TEXTURE = new Identifier("textures/entity/zombie/drowned.png");

    protected DrownedEntityRendererMixin(EntityRendererFactory.Context ctx, DrownedEntityModel<DrownedEntity> bodyModel, DrownedEntityModel<DrownedEntity> legsArmorModel, DrownedEntityModel<DrownedEntity> bodyArmorModel) {
        super(ctx, bodyModel, legsArmorModel, bodyArmorModel);
    }

    @Override
    public Identifier getTexture(ZombieEntity zombieEntity) {
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
                new EndbriteArmorModel2(context.getPart(CometClient.ENDBRITE_ARMOR_MODEL_LAYER))
        ));
    }
}
