package com.github.Soulphur0.dimensionalAlloys.block;

import com.github.Soulphur0.registries.CometBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.Random;

public class LavaDrenchstoneBlock extends DrenchstoneBlock{

    public LavaDrenchstoneBlock(Settings settings) {
        super(settings);
        setDefaultState(getStateManager().getDefaultState().with(LEVEL, 0).with(SOURCE_DIRECTION, Direction.UP));
    }

    @Override
    public void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        if (!world.isClient()){
            Random random = new Random();
            float breakChance = (float)Math.floor(random.nextFloat()*10);
            if (fallDistance > 1.25f){
                if (breakChance-fallDistance <= 0){
                    world.setBlockState(pos, Blocks.LAVA.getDefaultState().with(FluidBlock.LEVEL, 1));
                    world.playSound(null, pos, SoundEvents.ENTITY_ZOMBIE_BREAK_WOODEN_DOOR, SoundCategory.BLOCKS,0.5f,0.2f);
                }
            }
        }
        super.onLandedUpon(world, state, pos, entity, fallDistance);
    }

    @Override
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        return new ItemStack(CometBlocks.DRENCHSTONE);
    }
}