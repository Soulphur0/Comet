package com.github.SoulArts.mixin;

import com.github.SoulArts.Comet;
import net.minecraft.client.resource.metadata.AnimationResourceMetadata;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.TextureStitcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PersistentProjectileEntity.class)
public abstract class PersistentProjectileEntityMixin extends ProjectileEntity {

    public PersistentProjectileEntityMixin(EntityType<? extends ProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/projectile/PersistentProjectileEntity;setVelocity(Lnet/minecraft/util/math/Vec3d;)V", shift = At.Shift.AFTER), method = "onEntityHit(Lnet/minecraft/util/hit/EntityHitResult;)V")
    private void modifyVelocity(EntityHitResult entityHitResult, CallbackInfo ci) {
        Entity entityMixin = entityHitResult.getEntity();
        ItemStack item = ((LivingEntity) entityMixin).getActiveItem();

        if (entityMixin instanceof LivingEntity){
            if (((LivingEntity) entityMixin).isBlocking() && item.toString().contains("mirror_shield")){
                if (item.getDamage() < item.getMaxDamage()){
                    item.setDamage(item.getDamage()+3);
                } else if (item.getDamage() >= item.getMaxDamage()) {
                    ((PlayerEntity) entityMixin).sendToolBreakStatus(((PlayerEntity)entityMixin).getActiveHand());
                    item.setCount(0);
                }
                this.setVelocity(this.getVelocity().multiply(10.0D));
           }
        }
    }
}

// Entity línea 2742
