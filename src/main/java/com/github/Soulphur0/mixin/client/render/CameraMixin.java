package com.github.Soulphur0.mixin.client.render;

import com.github.Soulphur0.dimensionalAlloys.CometCameraBehaviour;
import com.github.Soulphur0.dimensionalAlloys.client.render.CometCameraSubmersionType;
import com.github.Soulphur0.registries.CometBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.CameraSubmersionType;
import net.minecraft.fluid.FluidState;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Arrays;
import java.util.List;

@Mixin(Camera.class)
public abstract class CameraMixin implements CometCameraBehaviour {

    @Shadow
    private boolean ready;

    @Shadow
    private Vec3d pos;

    @Shadow
    private BlockView area;

    @Shadow
    public abstract Camera.Projection getProjection();

    // $ Comet methods

    @Override
    public CometCameraSubmersionType comet_getSubmersionType() {
        if (!this.ready) {
            return CometCameraSubmersionType.NONE;
        }

        Camera.Projection projection = this.getProjection();
        List<Vec3d> list = Arrays.asList(projection.center, projection.getBottomRight(), projection.getTopRight(), projection.getBottomLeft(), projection.getTopLeft());
        for (Vec3d vec3d : list) {
            Vec3d vec3d2 = this.pos.add(vec3d);
            BlockPos blockPos = new BlockPos(vec3d2);

            BlockState blockState = this.area.getBlockState(blockPos);
            if (!blockState.isOf(CometBlocks.END_MEDIUM)) continue;
            return CometCameraSubmersionType.END_MEDIUM;
        }
        return CometCameraSubmersionType.NONE;
    }
}
