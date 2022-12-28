package com.github.Soulphur0.dimensionalAlloys.client.render.entity.model;

import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;

public class PortalShieldEntityModel extends Model {
    private static final String PLATE = "plate";
    private static final String HANDLE = "handle";
    private static final String PORTAL = "portal";

    private final ModelPart root;
    private final ModelPart plate;
    private final ModelPart handle;
    private final ModelPart portal;

    public PortalShieldEntityModel(ModelPart root) {
        super(RenderLayer::getEntitySolid);
        this.root = root;
        this.plate = root.getChild(PLATE);
        this.handle = root.getChild(HANDLE);
        this.portal = root.getChild(PORTAL);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild(PLATE, ModelPartBuilder.create().uv(0, 0).cuboid(-6.0f, -11.0f, -2.0f, 12.0f, 22.0f, 1.0f), ModelTransform.NONE);
        modelPartData.addChild(HANDLE, ModelPartBuilder.create().uv(26, 0).cuboid(-1.0f, -3.0f, -1.0f, 2.0f, 6.0f, 6.0f), ModelTransform.NONE);
        modelPartData.addChild(PORTAL, ModelPartBuilder.create().uv(0,0).cuboid(-5.0f, -10.0f, -2.01f, 10.0f, 20.0f, 0.0f), ModelTransform.NONE);
        return TexturedModelData.of(modelData, 64, 64);
    }

    public ModelPart getPlate() {
        return this.plate;
    }

    public ModelPart getHandle() {
        return this.handle;
    }

    public ModelPart getPortal() {return this.portal;}

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        this.root.render(matrices, vertices, light, overlay, red, green, blue, alpha);
    }
}
