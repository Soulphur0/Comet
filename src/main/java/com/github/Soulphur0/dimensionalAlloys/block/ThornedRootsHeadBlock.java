package com.github.Soulphur0.dimensionalAlloys.block;

import com.github.Soulphur0.Comet;
import com.github.Soulphur0.dimensionalAlloys.sound.CometSoundUtilities;
import com.github.Soulphur0.registries.CometBlocks;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.OptionalInt;

public class ThornedRootsHeadBlock extends AbstractPlantStemBlock implements Fertilizable, EndRoots {
    public ThornedRootsHeadBlock(Settings settings) {
        super(settings, Direction.DOWN, SHAPE, false, 0.1);
        this.setDefaultState((BlockState)((BlockState)((BlockState)this.stateManager.getDefaultState()).with(AGE, 0)).with(LOADED, false));
    }

    // $ Append properties
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(LOADED);
    }

    // $ Block properties.
    @Override
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        return new ItemStack(CometBlocks.THORNED_ROOTS_BLOCK_ITEM);
    }

    // _ Random tick to grow back thorns.
    // Grows back thorns in a random position of the plant.
    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        random.nextFloat();
        if (random.nextFloat() <= 0.02){
            // + Check the block above this block, counting plant blocks until it reaches the top.
            BlockState plantPart = state;
            int plantLength = 0;
            while (plantPart.isOf(CometBlocks.THORNED_ROOTS) || plantPart.isOf(CometBlocks.THORNED_ROOTS_PLANT)){
                plantPart = world.getBlockState(pos.up(plantLength));
                plantLength++;
            }

            // + Pick a random block of the plant based on the obtained plant length.
            BlockPos pickedPlantPartPos = pos.up((int)Math.floor(Math.random() * plantLength));
            BlockState pikcedPlantPart = world.getBlockState(pickedPlantPartPos);

            // + Make sure the picked block is of this and load it if it is not loaded.
            if (pikcedPlantPart.isOf(CometBlocks.THORNED_ROOTS) || pikcedPlantPart.isOf(CometBlocks.THORNED_ROOTS_PLANT)){
                if (!pikcedPlantPart.get(LOADED))
                    world.setBlockState(pickedPlantPartPos, pikcedPlantPart.with(LOADED, true), Block.REDRAW_ON_MAIN_THREAD | Block.NOTIFY_ALL);
            }

        }
        super.randomTick(state, world, pos, random);
    }

    // _ Self-Defense mechanism when loaded.
    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (state.get(LOADED) && entity instanceof LivingEntity livingEntity){
            if (!world.isClient()){
                livingEntity.addStatusEffect(pickRandomStatusEffect());
                livingEntity.damage(DamageSource.CACTUS, 1);
                world.setBlockState(pos, state.with(LOADED, false));
            }
            world.playSound(pos.getX(), pos.getY(), pos.getZ(), Comet.THORNED_ROOTS_BREAK, SoundCategory.BLOCKS, 1.0f, 1.0f, true);
        }
        super.onEntityCollision(state, world, pos, entity);
    }

    private StatusEffectInstance pickRandomStatusEffect(){
        int[] weightedList = {5, 3, 1, 1};
        StatusEffectInstance[] effectList = {
                new StatusEffectInstance(StatusEffects.POISON, 20*(int)Math.floor(Math.random()*15 + 10)),
                new StatusEffectInstance(StatusEffects.WITHER, 20*(int)Math.floor(Math.random()*5 + 10)),
                new StatusEffectInstance(StatusEffects.BLINDNESS, 20*(int)Math.floor(Math.random()*15 + 10)),
                new StatusEffectInstance(StatusEffects.HUNGER, 20*(int)Math.floor(Math.random()*60 + 60))
        };

        OptionalInt weightedListSum = Arrays.stream(weightedList).reduce(Integer::sum);
        int weightedDiceRoll = (int)Math.floor(Math.random() * weightedListSum.getAsInt() + 1);
        for(int i = 0; i<weightedList.length; i++){
            weightedDiceRoll -= weightedList[i];
            if (weightedDiceRoll <= 0)
                return effectList[i];
        }

        return new StatusEffectInstance(StatusEffects.POISON, 20*(int)Math.floor(Math.random()*15 + 10));
    }

    // $ Plant properties.
    @Override
    protected int getGrowthLength(Random random) {
        return 1;
    }

    @Override
    protected boolean chooseStemState(BlockState state) {
        return state.isAir();
    }

    @Override
    protected Block getPlant() {
        return CometBlocks.THORNED_ROOTS_PLANT;
    }

    // $ Methods required for plant actions.
    // _ Whenever another plant block like this one is placed below this one it will transform to a body block, copy state just in case it was loaded.
    @Override
    protected BlockState copyState(BlockState from, BlockState to) {
        return (BlockState)to.with(LOADED, from.get(LOADED));
    }

    // _ Have a chance to age every time it grows, the higher the chance, the less it totally grows.
    @Override
    protected BlockState age(BlockState state, Random random) {
        return (BlockState)super.age(state, random).with(LOADED, random.nextFloat() < 0.22f);
    }

    // $ Bonemeal behaviour.
    // _ If the root vine is loaded, it can't be fertilised.
    @Override
    public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) {
        return !state.get(LOADED);
    }

    // _ This is to tell bonemeal this block can grow.
    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    // _ Whenever the root vine grows via bonemeal, load it.
    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        world.setBlockState(pos, (BlockState)state.with(LOADED, true), Block.NOTIFY_LISTENERS);
    }

}
