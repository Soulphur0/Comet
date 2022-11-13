package com.github.SoulArts.dimensionalAlloys.block;

import com.github.SoulArts.dimensionalAlloys.CrystallizedEntityMethods;
import com.github.SoulArts.dimensionalAlloys.block.entity.CrystallizedCreatureBlockEntity;
import com.github.SoulArts.registries.CometBlocks;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class CrystallizedCreature extends Block implements BlockEntityProvider, Waterloggable {
    public CrystallizedCreature(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.empty();
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new CrystallizedCreatureBlockEntity(pos, state);
    }

    // todo remove this debug method
    // ! Debug, the nbt data should be written by the living entity mixin
    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof CrystallizedCreatureBlockEntity){
            NbtCompound nbtCompound = new NbtCompound();
            entity.saveNbt(nbtCompound);
            ((CrystallizedCreatureBlockEntity) blockEntity).writeDataTest(nbtCompound);
        }

    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        if (world.isClient){
            world.getBlockEntity(pos, CometBlocks.CRYSTALLIZED_CREATURE_BLOCK_ENTITY).ifPresent((blockEntity) -> {
                blockEntity.readNbtFromItemStack(itemStack);
            });
        }
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!world.isClient && world.getGameRules().getBoolean(GameRules.DO_TILE_DROPS)){
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof CrystallizedCreatureBlockEntity crystallizedCreatureBlockEntity) {
                ItemStack itemStack = new ItemStack(this);

                // ? Store nbt data to the item if the block entity has nbt data
                // Blocks without nbt data are not dropped to creative players.
                if(crystallizedCreatureBlockEntity.mobData != null){
                    NbtCompound nbtCompound = new NbtCompound();
                    nbtCompound.put("mobData", crystallizedCreatureBlockEntity.mobData);
                    BlockItem.setBlockEntityNbt(itemStack, CometBlocks.CRYSTALLIZED_CREATURE_BLOCK_ENTITY,nbtCompound);
                } else if (player.isCreative()){
                    return;
                }

                ItemEntity itemEntity = new ItemEntity(world, (double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), itemStack);
                itemEntity.setToDefaultPickupDelay();
                world.spawnEntity(itemEntity);
            }
        }
        super.onBreak(world, pos, state, player);
    }

    // Todo revise later to add functionality
    @Override
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        System.out.println("getPickStack()");
        BlockEntity blockEntity = world.getBlockEntity(pos);
        return blockEntity instanceof CrystallizedCreatureBlockEntity ? ((CrystallizedCreatureBlockEntity)blockEntity).getPickStack() : super.getPickStack(world, pos, state);
    }
}
