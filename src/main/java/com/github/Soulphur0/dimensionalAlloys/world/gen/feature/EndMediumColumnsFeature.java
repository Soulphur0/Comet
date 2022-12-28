package com.github.Soulphur0.dimensionalAlloys.world.gen.feature;

import com.github.Soulphur0.registries.CometBlocks;
import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import jdk.swing.interop.SwingInterOpUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import org.jetbrains.annotations.Nullable;

public class EndMediumColumnsFeature extends Feature<EndMediumColumnsFeatureConfig> {
    private static final ImmutableList<Block> CANNOT_REPLACE_BLOCKS = ImmutableList.of(Blocks.OBSIDIAN, Blocks.PURPUR_BLOCK, Blocks.PURPUR_PILLAR, Blocks.PURPUR_STAIRS, Blocks.PURPUR_SLAB, Blocks.END_STONE_BRICKS, Blocks.BEDROCK, Blocks.CHEST, Blocks.ENDER_CHEST);
    private static final int field_31495 = 5;
    private static final int field_31496 = 50;
    private static final int field_31497 = 8;
    private static final int field_31498 = 15;

    public EndMediumColumnsFeature(Codec<EndMediumColumnsFeatureConfig> codec) {
        super(codec);
    }

    // _ Generate end medium columns based on the vanilla basalt generation.
    // ? How does this work:
    // ?    Basalt columns are large blobs made out of smaller blobs.
    // ?    Whenever we give a reach to the feature, it determines the radius of the smaller blobs.
    // ?    Whenever we give a height to the feature, it will determine the height of the columns.
    // +        Height is calculated in a way, so the further away it is from the generation point, the lower  it will be.
    @Override
    public boolean generate(FeatureContext<EndMediumColumnsFeatureConfig> context) {
        // # World gen data.
        int seaLevel = 120;
        BlockPos origin = context.getOrigin();
        StructureWorldAccess world = context.getWorld();
        Random random = context.getRandom();

        // # Configured feature data.
        EndMediumColumnsFeatureConfig endMediumColumnsFeatureConfig = context.getConfig();

        // ! Find surface, move to external method.
        boolean validPosition = false;
        for (int y = 0; y < world.getHeight(); y++) {
            origin = origin.up();

            // the tag name is dirt, but includes grass, mud, podzol, etc.
            if (world.getBlockState(origin).isOf(Blocks.END_STONE) || world.getBlockState(origin).isOf(CometBlocks.CHORUS_HUMUS) || world.getBlockState(origin).isOf(CometBlocks.FRESH_CHORUS_HUMUS)) {
                if (world.getBlockState(origin.up()).isOf(Blocks.AIR)) {
                    validPosition = true;
                    break;
                }
            }
        }
        // ! -----

        // ! Lessen the chance of generation
        float generateChance = random.nextFloat();
        boolean willGenerate = generateChance < 0.01;

        // + Check if the feature can be placed.
        // EndMediumColumnsFeature.canPlaceAt(world, seaLevel, origin.mutableCopy())
        if (!validPosition || !willGenerate) {
            return false;
        }

        // + Get average column height of this whole feature.
        // - This value will later be subtracted in each smaller feature generation iteration.
        // - It will get subtracted the distance to the origin of the generation.
        // - This way columns generate shorter the further we get from the generation point.
        int columnHeight = endMediumColumnsFeatureConfig.height();
        
        // + Generate boolean ; 'true' 9 out of 10 times.
        // - This will determine the radius of the feature.
        // - Most of the time it will be a feature of smaller radius.
        boolean isLargeBlob = random.nextFloat() < 0.9f;
        
        // + Get the radius of the feature.
        // - The radius of the feature determines an area of all the poins a smaller blob will be able to generate.
        // - It is based of the previous boolean result, and most of the time the radius will be small/medium.
        int adjustedColumnHeight = Math.min(columnHeight, isLargeBlob ? 5 : 8);
        
        // + Determine number of smaller blob placement tries based on radius.
        // - Small radius features will contain a lot more of small blob placement tries.
        int placementTries = isLargeBlob ? 50 : 15;
        
        boolean susccessfulGeneration = false;
        // + Place columns in a radius.
        // - The radius will be in an area of [(2 * ajustedColumnHeight) * (2 * adjustedColumnHeight)]
        for (BlockPos blobGenerationPoint : BlockPos.iterateRandomly(random, placementTries, origin.getX() - adjustedColumnHeight, origin.getY(), origin.getZ() - adjustedColumnHeight, origin.getX() + adjustedColumnHeight, origin.getY(), origin.getZ() + adjustedColumnHeight)) {

            // + Get hypothetical distance to the center of the feature generation point if the column were to be placed horizontally.
            // - Get the manhattan distance (sum of x, y, z) between the origin and the iterated generation point, then subtract it from the columnHeight.  
            int horizontalDistanceToTheOrigin = columnHeight - blobGenerationPoint.getManhattanDistance(origin);

            // + If the column, on its side, can't reach the center of generation, skip its placement.
            if (horizontalDistanceToTheOrigin < 0) continue;

            // + Try to place a small blob, if susccessful, bl will be 'true', false otherwise.
            susccessfulGeneration |= this.placeBasaltColumn(world, seaLevel, blobGenerationPoint, horizontalDistanceToTheOrigin, endMediumColumnsFeatureConfig.reach());
        }
        return susccessfulGeneration;
    }

    private boolean placeBasaltColumn(WorldAccess world, int seaLevel, BlockPos blobGenerationPoint, int height, int reach) {
        boolean susccessfulGeneration = false;

        // + In an area of reach*2, place columns.
        block0: for (BlockPos columnGenerationPoint : BlockPos.iterate(blobGenerationPoint.getX() - reach, blobGenerationPoint.getY(), blobGenerationPoint.getZ() - reach, blobGenerationPoint.getX() + reach, blobGenerationPoint.getY(), blobGenerationPoint.getZ() + reach)) {

            // - Get manhattan distance to the center of generation, to later use to find a placement position upwards or downwards.
            int horizontalDistanceToBlobOrigin = columnGenerationPoint.getManhattanDistance(blobGenerationPoint);

            // - If the placement position is air, and below generation level (sea level in this case), look for a block downwards; if the block is not air and below generation level, look for a block upwards.
            BlockPos validColumnPlacementPoint =
                    EndMediumColumnsFeature.isAir(world, seaLevel, columnGenerationPoint) ?
                            EndMediumColumnsFeature.moveDownToGround(world, seaLevel, columnGenerationPoint.mutableCopy(), horizontalDistanceToBlobOrigin) :
                            EndMediumColumnsFeature.moveUpToAir(world, columnGenerationPoint.mutableCopy(), horizontalDistanceToBlobOrigin);
            if (validColumnPlacementPoint == null) continue;

            // - Obtain mutable position of the placement point.
            BlockPos.Mutable mutable = validColumnPlacementPoint.mutableCopy();

            // - Build column.
            for (int j = height - horizontalDistanceToBlobOrigin / 2; j >= 0; --j) {
                if (EndMediumColumnsFeature.isAir(world, seaLevel, mutable)) {
                    this.setBlockState(world, mutable, CometBlocks.END_MEDIUM.getDefaultState());
                    mutable.move(Direction.UP);
                    susccessfulGeneration = true;
                    continue;
                }
                if (!world.getBlockState(mutable).isOf(CometBlocks.END_MEDIUM)) continue block0;
                mutable.move(Direction.UP);
            }
        }
        return susccessfulGeneration;
    }

    @Nullable
    private static BlockPos moveDownToGround(WorldAccess world, int seaLevel, BlockPos.Mutable mutablePos, int distance) {
        while (mutablePos.getY() > world.getBottomY() + 1 && distance > 0) {
            --distance;
            if (EndMediumColumnsFeature.canPlaceAt(world, seaLevel, mutablePos)) {
                return mutablePos;
            }
            mutablePos.move(Direction.DOWN);
        }
        return null;
    }

    private static boolean canPlaceAt(WorldAccess world, int seaLevel, BlockPos.Mutable mutablePos) {
        if (EndMediumColumnsFeature.isAir(world, seaLevel, mutablePos)) {
            BlockState blockState = world.getBlockState(mutablePos.move(Direction.DOWN));
            mutablePos.move(Direction.UP);
            return !blockState.isAir() && !CANNOT_REPLACE_BLOCKS.contains(blockState.getBlock());
        }
        return false;
    }

    @Nullable
    private static BlockPos moveUpToAir(WorldAccess world, BlockPos.Mutable mutablePos, int distance) {
        while (mutablePos.getY() < world.getTopY() && distance > 0) {
            --distance;
            BlockState blockState = world.getBlockState(mutablePos);
            if (CANNOT_REPLACE_BLOCKS.contains(blockState.getBlock())) {
                return null;
            }
            if (blockState.isAir()) {
                return mutablePos;
            }
            mutablePos.move(Direction.UP);
        }
        return null;
    }

    private static boolean isAir(WorldAccess world, int seaLevel, BlockPos pos) {
        BlockState blockState = world.getBlockState(pos);
        return blockState.isAir() && pos.getY() <= seaLevel;
    }
}
