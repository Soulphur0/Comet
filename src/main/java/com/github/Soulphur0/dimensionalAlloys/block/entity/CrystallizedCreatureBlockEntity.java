package com.github.Soulphur0.dimensionalAlloys.block.entity;

import com.github.Soulphur0.dimensionalAlloys.block.CrystallizedCreature;
import com.github.Soulphur0.registries.CometBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class CrystallizedCreatureBlockEntity extends BlockEntity {

    private NbtCompound mobData;
    private boolean blockStateData;

    public CrystallizedCreatureBlockEntity(BlockPos pos, BlockState state){
        super(CometBlocks.CRYSTALLIZED_CREATURE_BLOCK_ENTITY, pos, state);
    }

    // $ Tick

    public static void tick(World world, BlockPos pos, BlockState state, CrystallizedCreatureBlockEntity be) {
        if (world.getFluidState(pos).getFluid() == Fluids.WATER){
            if (state.getBlock() instanceof CrystallizedCreature cc){
                cc.releaseMob(world, pos);
            }
        }
    }

    // $ Write and retrieve data from NBT

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);

        nbt.putBoolean("blockStateData", blockStateData);

        if (mobData != null)
            nbt.put("mobData", mobData);
    }


    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);

        if (nbt != null){
            this.mobData = nbt.getCompound("mobData");
            this.blockStateData = nbt.getBoolean("blockStateData");
        }
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

    // _ Write data to block entity.

    // ? Write data to block entity.
    // Used on block creation in LivingEntityMixin.
    public void writeData(NbtCompound data){
        this.mobData = data.getCompound("mobData");
        this.blockStateData = data.getBoolean("blockStateData");
    }

    // ? Write block state data to block entity.
    // Used in onUse() method of this block.
    public void writeBlockStateData(boolean blockStateData){
        this.blockStateData = blockStateData;
    }

    // ? Write data from item stack.
    // Used in onPlace() method of this block.
    public void readNbtFromItemStack(ItemStack itemStack){
        NbtCompound nbtCompound;
        nbtCompound = BlockItem.getBlockEntityNbt(itemStack);

        if (nbtCompound != null){
            this.mobData = nbtCompound.getCompound("mobData");
            this.blockStateData = nbtCompound.getBoolean("blockStateData");
        }
    }

    // _ Retrieve data from block entity.

    // ? Get mob data
    public NbtCompound getMobData(){
        return mobData;
    }

    public boolean getBlockStateData(){
        return blockStateData;
    }

    // ? Write NBT data to middle-click picked ItemStack
    public ItemStack getPickStack() {
        ItemStack itemStack = new ItemStack(CometBlocks.CRYSTALLIZED_CREATURE);
        NbtCompound nbtCompound = new NbtCompound();
        NbtCompound nbtCompound1 = mobData;
        nbtCompound.put("mobData", nbtCompound1);

        BlockItem.setBlockEntityNbt(itemStack, this.getType(), nbtCompound);

        return itemStack;
    }
}
