package com.github.SoulArts.entityRenderer;

import com.github.SoulArts.dimensionalAlloys.entity.NebuwhaleEntity;
import com.github.SoulArts.entityModel.NebuwhaleEntityModel;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.MagmaCubeEntityModel;
import net.minecraft.util.Identifier;

public class NebuwhaleEntityRenderer extends MobEntityRenderer<NebuwhaleEntity, NebuwhaleEntityModel> {

    public NebuwhaleEntityRenderer (EntityRenderDispatcher entityRenderDispatcher){
        super(entityRenderDispatcher, new NebuwhaleEntityModel(), 5.0f);
    }

    @Override
    public Identifier getTexture(NebuwhaleEntity entity){
        return new Identifier("comet", "textures/entity/nebuwhale/nebuwhale.png");
    }
}
