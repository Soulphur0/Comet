package com.github.Soulphur0.mixin.entity.projectile;

import com.github.Soulphur0.Comet;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.mob.GhastEntity;
import net.minecraft.entity.projectile.*;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ProjectileEntity.class)
public class ProjectileEntityMixin {

    @Shadow
    private Entity owner;

    @Inject(method = "onEntityHit", at = @At("HEAD"))
    private void comet_teleportProjectile(EntityHitResult entityHitResult, CallbackInfo ci){
        if (entityHitResult.getEntity() instanceof LivingEntity projectileReflector){
            if (projectileReflector.getActiveItem().isOf(Comet.PORTAL_SHIELD) && projectileReflector.isUsingItem()){
                // ? If the projectile is being deflected by a portal shield, save the projectile entity and react to it.
                ProjectileEntity thisProjectile = ((ProjectileEntity)(Object)this);
                projectileReflector.getActiveItem().damage(1,projectileReflector, p -> p.sendToolBreakStatus(projectileReflector.getActiveHand()));

                // ? If the projectile has an owner, react to it depending on the type of projectile.
                if (owner != null){
                    Random random = Random.create();

                    if (thisProjectile instanceof FireballEntity) {
                        // + Large fireball behaviour.
                        // * Since large fireballs explode on contact, and doesn't seem to synchronize properly, they will get teleported the instant they collide.
                        // * This will make the resultant explosion teleport inside its owner.

                        thisProjectile.setPosition(owner.getPos());
                        playProjectileTeleportationEffects(thisProjectile.getWorld(), thisProjectile.getBlockPos(), projectileReflector, false);
                    } else {
                        // + Generic projectile behaviour.
                        // * Projectiles like arrows will teleport above their target, and around it, hitting them at a raptor's attack angle.

                        // % Generic projectiles discard conditions.
                        // - If the projectile is a small fireball and the owner is fire immune, discard the projectile.
                        if (thisProjectile instanceof SmallFireballEntity && owner.isFireImmune())
                            thisProjectile.discard();

                        // - If the projectile is a dragon fireball and the owner is a dragon, discard the projectile.
                        if (thisProjectile instanceof DragonFireballEntity && owner instanceof EnderDragonEntity)
                            thisProjectile.discard();

                        // - If the projectile is a shulker bullet, discard it.
                        if (thisProjectile instanceof ShulkerBulletEntity)
                            thisProjectile.discard();

                        // - If the projectile is a wither skull and the owner is a wither, discard it.
                        if (thisProjectile instanceof WitherSkullEntity && owner instanceof WitherEntity)
                            thisProjectile.discard();
                        // % -------------------------------------

                        // - Get the position from where the projectile was shot.
                        // Since Biped Entities usually fire arrows, the position will be their feet position + 1.5 blocks.
                        Vec3d ownerPosition = owner.getPos();
                        ownerPosition = ownerPosition.add(0.0D, 1.5D, 0.0D);

                        // - Get a random position above and around the projectile's owner.
                        Vec3d tpPosition = ownerPosition.add(Math.floor(random.nextDouble() * 8.0D - 4.0D), 5.0D, Math.floor(random.nextDouble() * 8.0D - 4.0D));

                        // - Get the vector from the tp position to the owner position and multiply its magnitude by 2.
                        Vec3d newDirection = tpPosition.subtract(ownerPosition).multiply(2.0D);

                        // - Set the projectile at the TP position, play effects and give it a velocity equal to the vector that points to its owner.
                        thisProjectile.setPosition(tpPosition);
                        playProjectileTeleportationEffects(thisProjectile.getWorld(), thisProjectile.getBlockPos(), projectileReflector, false);
                        thisProjectile.setVelocity(newDirection);
                    }
                // ? If the projectile has no owner, discard it.
                } else {
                    playProjectileTeleportationEffects(thisProjectile.getWorld(), thisProjectile.getBlockPos(), projectileReflector, true);
                    thisProjectile.discard();
                }
            }
        }
    }

    // ? Displays particle effects and plays sound when the projectile is teleported.
    private void playProjectileTeleportationEffects(World world, BlockPos pos, LivingEntity reflector, boolean discarded){
        world.playSound(reflector.getX(), reflector.getY(), reflector.getZ(), SoundEvents.ENTITY_ENDERMAN_TELEPORT, SoundCategory.PLAYERS,1.0f,1.0f,true);

        // . Send teleport position to the client world to display teleport particles.
        if (!world.isClient() && !discarded){
            PacketByteBuf posBuf = PacketByteBufs.create().writeBlockPos(pos);
            for (ServerPlayerEntity serverPlayer : PlayerLookup.tracking((ServerWorld) world, pos)) {
                ServerPlayNetworking.send((ServerPlayerEntity) serverPlayer, new Identifier("comet", "projectile_teleportation_effects"), posBuf);
            }
        }
    }
}
