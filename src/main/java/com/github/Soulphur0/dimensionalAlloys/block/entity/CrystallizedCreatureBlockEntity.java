package com.github.Soulphur0.dimensionalAlloys.block.entity;

import com.github.Soulphur0.dimensionalAlloys.block.AbstractCrystallizedCreatureBlock;
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

    public CrystallizedCreatureBlockEntity(BlockPos pos, BlockState state){
        super(CometBlocks.CRYSTALLIZED_CREATURE_BLOCK_ENTITY, pos, state);
    }

    // $ Tick
    public static void tick(World world, BlockPos pos, BlockState state, CrystallizedCreatureBlockEntity be) {
        if (world.getFluidState(pos).getFluid() == Fluids.WATER){
            Block block = state.getBlock();
            if (block instanceof AbstractCrystallizedCreatureBlock accb){
                accb.releaseMob(world, pos);
            }
        }
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

        if (nbt != null){
            this.mobData = nbt.getCompound("mobData");
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

    // _ Write data to block entity. [SETTERS]

    // ? Write data to block entity.
    // Used on block creation in LivingEntityMixin.
    public void writeData(NbtCompound data){
        this.mobData = data.getCompound("mobData");
    }

    // ? Write data from item stack.
    // Used in onPlace() method of this block.
    public void readNbtFromItemStack(ItemStack itemStack){
        NbtCompound nbtCompound;
        nbtCompound = BlockItem.getBlockEntityNbt(itemStack);

        if (nbtCompound != null){
            this.mobData = nbtCompound.getCompound("mobData");
        }
    }

    // _ Retrieve data from block entity. [GETTERS]

    // ? Get mob data
    public NbtCompound getMobData(){
        return mobData;
    }

    // ? Write NBT data to middle-click picked ItemStack
    public ItemStack getPickStack(BlockState state) {
        ItemStack itemStack;

        if (state.isOf(CometBlocks.CRYSTALLIZED_CREATURE))
            itemStack = new ItemStack(CometBlocks.CRYSTALLIZED_CREATURE_BLOCK_ITEM);
        else if (state.isOf(CometBlocks.TRIMMED_CRYSTALLIZED_CREATURE))
            itemStack = new ItemStack(CometBlocks.TRIMMED_CRYSTALLIZED_CREATURE_BLOCK_ITEM);
        else
            itemStack = new ItemStack(CometBlocks.CREATURE_STATUE_BLOCK_ITEM);

        NbtCompound nbtCompound = new NbtCompound();
        NbtCompound nbtCompound1 = mobData;
        nbtCompound.put("mobData", nbtCompound1);

        BlockItem.setBlockEntityNbt(itemStack, this.getType(), nbtCompound);

        return itemStack;
    }
}
