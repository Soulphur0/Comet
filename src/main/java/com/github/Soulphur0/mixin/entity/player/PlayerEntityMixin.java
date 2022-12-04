package com.github.Soulphur0.mixin.entity.player;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {

    // $ Injected ------------------------------------------------------------------------------------------------------
    @Inject(method ="travel", at=@At("HEAD"), cancellable = true)
    private void cancelInputMovement(Vec3d movementInput, CallbackInfo ci){
        if (((PlayerEntity)(Object)this).isCrystallized() && ((PlayerEntity)(Object)this).isCrystallizedByStatusEffect())
            ci.cancel();
    }
}
