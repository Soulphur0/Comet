/*
package com.github.SoulArts.dimensionalAlloys.entity;

import com.github.SoulArts.Comet;
import com.github.SoulArts.CometClient;
import com.github.SoulArts.entityModel.NebuwhaleEntityModel;
import com.github.SoulArts.entityRenderer.NebuwhaleEntityRenderer;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.impl.screenhandler.Networking;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.util.ChatMessages;
import net.minecraft.client.util.NetworkUtils;
import net.minecraft.command.argument.EntityAnchorArgumentType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.FlyingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.AxisTransformation;
import net.minecraft.util.math.DirectionTransformation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import org.lwjgl.system.CallbackI;

import java.util.EnumSet;

public class NebuwhaleEntity extends FlyingEntity {
    public boolean turning = false;

    public NebuwhaleEntity(EntityType<? extends FlyingEntity> entityType, World world) {
        super(entityType, world);
        this.moveControl = new NebuwhaleEntity.NebuwhaleMoveControl(this);
    }

    protected void initGoals(){
        this.goalSelector.add(1,new NebuwhaleEntity.TravelGoal());
    }

    public void tick(){
        super.tick();
    }

    public class TravelGoal extends Goal {
        private double xDestination;
        private double zDestination;
        private double travelSpeed = 0.0001D;

        public TravelGoal () { this.setControls(EnumSet.of(Control.MOVE)); }

        @Override
        public boolean canStart() {
            return true;
        }

        @Override
        public void start(){
            this.xDestination = NebuwhaleEntity.this.random.nextDouble() * (double)NebuwhaleEntity.this.random.nextInt(1000) * (NebuwhaleEntity.this.random.nextBoolean() ? 1.0D : -1.0D);
            this.zDestination = NebuwhaleEntity.this.random.nextDouble() * (double)NebuwhaleEntity.this.random.nextInt(1000) * (NebuwhaleEntity.this.random.nextBoolean() ? 1.0D : -1.0D);
        }

        @Override
        public void tick(){
            NebuwhaleEntity.this.move(MovementType.SELF,
                    new Vec3d(Math.abs(xDestination) * travelSpeed,
                            0,
                            Math.abs(zDestination) * travelSpeed));

            if (world.isClient){
                ServerPlayNetworking.send((ServerPlayerEntity) NebuwhaleEntity.this.getEntityWorld().getClosestPlayer(NebuwhaleEntity.this,200),
                        new Identifier("nebuwhale_render"),
                        PacketByteBufs.empty());
            }
        }
    }

    public class NebuwhaleMoveControl extends MoveControl {

        public NebuwhaleMoveControl(MobEntity entity) {
            super(entity);
        }
    }
}
*/
