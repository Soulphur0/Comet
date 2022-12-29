package com.github.Soulphur0.dimensionalAlloys.block;

import com.github.Soulphur0.Comet;
import com.github.Soulphur0.registries.CometBlocks;
import com.google.common.collect.ImmutableSet;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Waterloggable;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.PickaxeItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public class CreatureStatue extends AbstractCrystallizedCreatureBlock implements BlockEntityProvider, Waterloggable {

    private static final ImmutableSet<Item> statueMaterials = ImmutableSet.of(Comet.CONCENTRATED_END_MEDIUM_BOTTLE, Items.QUARTZ);

    public CreatureStatue(Settings settings) {
        super(settings);
    }

    // ? Render type
    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.INVISIBLE;
    }

    // ? Make it not spawn an entity.
    @Override
    public void releaseMob(World world, BlockPos pos) {}

    // ? Make it able to render different textures.
    // . Sends a packet to each client to be synchronized with the server.
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient()) return super.onUse(state, world, pos, player, hand, hit);

        Item mainHandItem = player.getMainHandStack().getItem();
        if (mainHandItem instanceof PickaxeItem || statueMaterials.stream().anyMatch(item -> item.equals(mainHandItem))){
            PacketByteBuf posPacket = PacketByteBufs.create().writeBlockPos(pos).writeVarInt(Item.getRawId(mainHandItem.asItem()));
            for (ServerPlayerEntity serverPlayer : PlayerLookup.tracking((ServerWorld) world, pos)) {
                ServerPlayNetworking.send((ServerPlayerEntity) serverPlayer, new Identifier("comet", "update_statue_texture"), posPacket);
            }
        }
        return super.onUse(state, world, pos, player, hand, hit);
    }

    public static void updateNBT(MinecraftClient client, BlockPos posToUpdate, @Nullable Item material) {
        World world = client.world;
        if (world == null) return;

        PlayerEntity player = client.player;
        if (player == null) return;

        if (material == null) return;

        world.getBlockEntity(posToUpdate, CometBlocks.CRYSTALLIZED_CREATURE_BLOCK_ENTITY).ifPresent(blockEntity ->{
            if (material instanceof PickaxeItem){
                NbtCompound mobData = blockEntity.getMobData();
                mobData.putString("StatueMaterial","none");
            }

            if (material == Comet.CONCENTRATED_END_MEDIUM_BOTTLE){
                NbtCompound mobData = blockEntity.getMobData();
                mobData.putString("StatueMaterial","end_medium");
            }

            if (material == Items.QUARTZ){
                NbtCompound mobData = blockEntity.getMobData();
                mobData.putString("StatueMaterial","quartz");
            }
        });
    }
}
