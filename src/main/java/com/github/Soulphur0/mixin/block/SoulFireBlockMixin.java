package com.github.Soulphur0.mixin.block;

import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoulFireBlock;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(SoulFireBlock.class)
public class SoulFireBlockMixin extends AbstractFireBlock {


    public SoulFireBlockMixin(Settings settings, float damage) {
        super(settings, damage);
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (!entity.isFireImmune()) {
            // + Set soul fire ticks to 1 on-contact, in order to not render 1 regular fire hud frame.
            if (entity.getSoulFireTicks() == -20)
                entity.setSoulFireTicks(1);
            else
                entity.setSoulFireTicks(entity.getFireTicks());

            entity.setEndFireTicks(-20);
        }

        super.onEntityCollision(state, world, pos, entity);
    }

    @Override
    public boolean isFlammable(BlockState state) {
        return true;
    }
}
