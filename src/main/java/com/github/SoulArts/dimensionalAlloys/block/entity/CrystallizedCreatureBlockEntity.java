package com.github.SoulArts.dimensionalAlloys.block.entity;

import com.github.SoulArts.registries.CometBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class CrystallizedCreatureBlockEntity extends BlockEntity {

    public NbtCompound mobData;

    public CrystallizedCreatureBlockEntity(BlockPos pos, BlockState state){
        super(CometBlocks.CRYSTALLIZED_CREATURE_BLOCK_ENTITY, pos, state);
    }

    // $ Write and retrieve data from NBT

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);

        if (mobData != null)
            nbt.put("mobData", mobData);
    }


    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);

        if (nbt != null)
            this.mobData = nbt.getCompound("mobData");
    }

    // $ Sync data S2C

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return createNbt();
    }

    // $ Comet methods

    // ? Write data to block entity.
    public void writeDataTest(NbtCompound mobData){
        this.mobData = mobData;
    }

    // ? Retrieve data from block entity.
    public void readNbtFromItemStack(ItemStack itemStack){
        NbtCompound nbtCompound = BlockItem.getBlockEntityNbt(itemStack);
        if (nbtCompound != null)
            this.mobData = nbtCompound.getCompound("mobData");
    }

    // ? Write NBT data to dropped ItemStack
    // TODO make this return mob data nbt in the item form, rn it return dummy data
    public ItemStack getPickStack() {
        ItemStack itemStack = new ItemStack(CometBlocks.CRYSTALLIZED_CREATURE);
        NbtCompound nbtCompound = new NbtCompound();
        nbtCompound.putString("testItemPick", "This is a total test");
        BlockItem.setBlockEntityNbt(itemStack, this.getType(), nbtCompound);

        // ? Implement custom name in the future.
        /*
        if (this.customName != null) {
            itemStack.setCustomName(this.customName);
        }
        */

        return itemStack;
    }
}
