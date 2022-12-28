package com.github.Soulphur0.dimensionalAlloys.world.gen.feature;

import com.github.Soulphur0.dimensionalAlloys.block.EndIronOre;
import com.github.Soulphur0.registries.CometBlocks;
import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

import java.util.ArrayList;

public class EndIronOreFeature extends Feature<EndIronOreFeatureConfig> {
    public EndIronOreFeature(Codec<EndIronOreFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<EndIronOreFeatureConfig> context) {
        // # World gen data.
        BlockPos origin = context.getOrigin();
        StructureWorldAccess world = context.getWorld();
        Random random = context.getRandom();

        // # Feature gen data.
        int blockAmount = context.getConfig().blockAmount();

        // + Find a point to begin generation.
        // - From the bottom of the world, get the block above until endstone is found, and save that height.
        // - Then keep going up until air is reached again, that would be section A.
        // - Keep doing this and saving world cutouts, sections where there is endstone between two height points.
        // - Then pick a point inside any of these cutouts and use it as a generation point.

        ArrayList<int[]> cutouts = new ArrayList<>();
        int cutoutStartHeight = 0;
        boolean insideCutout = false;

        // ? Scan the world upwards from the origin point.
        for (int i = 0; i<world.getHeight(); i++){
            BlockState scannedBlock = world.getBlockState(origin.up(i));

            // + If the block is of endstone, save the cutout start height.
            if (!insideCutout && scannedBlock.isOf(Blocks.END_STONE)){
                cutoutStartHeight = i;
                insideCutout = true;
            // + If air is found again, create a new cutout with the start and end positions.
            } else if (insideCutout && scannedBlock.isOf(Blocks.AIR)){
                insideCutout = false;
                cutouts.add(new int[]{cutoutStartHeight, i});
            }
        }

        // + If no cutouts were found return false.
        if (cutouts.isEmpty())
            return false;

        // + Once we got the cutouts we can pick one of them, and then a height inside any of them to generate the ore vein.
        int[] chosenCutout = cutouts.get(random.nextInt(cutouts.size()));
        BlockPos veinGenerationPoint = origin.up((int)Math.floor(random.nextFloat() * (chosenCutout[1]-chosenCutout[0]) + chosenCutout[0]));
        boolean succesfulGeneration = generateVein(world, veinGenerationPoint, random, blockAmount);

        return succesfulGeneration;
    }

    private boolean generateVein(StructureWorldAccess world, BlockPos generationPoint, Random random, int blockAmount){
        // + Vein generation will be as follows.
        // - Veins will be generated in a preferred horizontal direction.
        // - Veins will have a preferred vertical direction.
        // - Veins will generate horizontally, but they will randomly move a block up/down at the preferred vertical direction.
        // - Some veins will have a higher chance of vertically move than others.
        // - Once a vein has generated all the blocks of the block amount counter, it will stop generating.

        Direction[] horizontalDirections = {Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST};
        Direction[] verticalDirections = {Direction.DOWN, Direction.UP};

        Direction horizontalDirection = horizontalDirections[random.nextInt(horizontalDirections.length -1)];
        Direction verticalDirection = verticalDirections[random.nextInt(verticalDirections.length -1)];
        float verticalDeviationLikelihood = random.nextFloat();

        int horizontalDirectionShift = 0;
        int verticalDirectionShift = 0;

        // _ Loop for the amount of blocks that there will be generated.
        while(blockAmount > 0){
            // ? Determine the direction where the next piece/ring of the vein will be generated.
            // + The vein will sum +1 to the horizontal direction shift.
            horizontalDirectionShift++;

            // % A chance on vertically-offsetting the generation will be given.
            if (random.nextFloat() <= verticalDeviationLikelihood)
                verticalDirectionShift++;

            // + Using this shift, a sub-generation point will be saved, around this point, ore will be placed in all directions.
            BlockPos veinRingOrigin = generationPoint.offset(horizontalDirection, horizontalDirectionShift).offset(verticalDirection, verticalDirectionShift);

            // ? Generate a first ore layer of the ore vein if possible.

            // + If the sub-generation point is not of end stone, stop the generation.
            if (!world.getBlockState(veinRingOrigin).isOf(Blocks.END_STONE))
                break;

            // + If an ore can in fact be placed at the sub-generation point, blocks will try to be placed around this block.
            // - Try to place ring origin block, around this one other blocks will be placed.
            if (world.setBlockState(veinRingOrigin, CometBlocks.END_IRON_ORE.getDefaultState(),3)){
                for (Direction direction : horizontalDirections){
                    if (world.getBlockState(veinRingOrigin.offset(direction)).isOf(Blocks.END_STONE) && direction != horizontalDirection)
                        world.setBlockState(veinRingOrigin.offset(direction), CometBlocks.END_IRON_ORE.getDefaultState(), 3);
                    // Whether the placement fails or not, a block from the total block count will be subtracted.
                    blockAmount--;
                }
            }

            // ? Generate a second ore layer on top of the previous one if possible.

            // + If the second layer sub-generation point is not of end stone, skip the generation.
            if (!world.getBlockState(veinRingOrigin.up()).isOf(Blocks.END_STONE))
                continue;

            // - Try to generate an equal layer of ores on top of the previous one.
            if (world.setBlockState(veinRingOrigin.up(), CometBlocks.END_IRON_ORE.getDefaultState(),3)){
                for (Direction direction : horizontalDirections){
                    if (world.getBlockState(veinRingOrigin.offset(direction).up()).isOf(Blocks.END_STONE) && direction != horizontalDirection)
                        world.setBlockState(veinRingOrigin.offset(direction).up(), CometBlocks.END_IRON_ORE.getDefaultState(), 3);
                    // Whether the placement fails or not, a block from the total block count will be subtracted.
                    blockAmount--;
                }
            }

        }

        return true;
    }
}
