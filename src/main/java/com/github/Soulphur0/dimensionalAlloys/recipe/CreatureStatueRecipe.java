package com.github.Soulphur0.dimensionalAlloys.recipe;

import com.github.Soulphur0.Comet;
import com.github.Soulphur0.dimensionalAlloys.block.CrystallizedCreature;
import com.github.Soulphur0.dimensionalAlloys.block.TrimmedCrystallizedCreature;
import com.github.Soulphur0.registries.CometBlocks;
import net.minecraft.block.Block;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class CreatureStatueRecipe extends SpecialCraftingRecipe {

    public CreatureStatueRecipe(Identifier id) {
        super(id);
    }

    int[][] recipe = {{0,1,0}, {1,2,1}, {0,1,0}};

    @Override
    public boolean matches(CraftingInventory craftingInventory, World world) {
        int[][] input = new int[3][3];
        // * Print the crafting placement into array.
        int inventorySlot = 0;
        for (int i = 0; i < craftingInventory.getWidth(); i++){
            for (int j = 0; j < craftingInventory.getHeight(); j++) {
                ItemStack itemStack = craftingInventory.getStack(inventorySlot);
                if (itemStack.getItem() == Items.AMETHYST_SHARD) {
                    input[i][j] = 1;
                } else if (Block.getBlockFromItem(itemStack.getItem()) instanceof CrystallizedCreature || Block.getBlockFromItem(itemStack.getItem()) instanceof TrimmedCrystallizedCreature) {
                    input[i][j] = 2;
                }
                inventorySlot++;
            }
        }

        // * Check crafting placement with recipe.
        for (int i = 0; i < recipe.length; i++){
            for (int j = 0; j < recipe[0].length; j++){
                if (recipe[i][j] != input[i][j])
                    return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack craft(CraftingInventory craftingInventory) {
        ItemStack crystallizedCreatureItem = ItemStack.EMPTY;

        // * Check that there is, in fact, a crystallized creature and four amethyst shards.
        for(int i = 0; i < craftingInventory.size(); i++){
            ItemStack sample = craftingInventory.getStack(i);
            if (sample.isEmpty()) continue;
            Item item = sample.getItem();
            if (Block.getBlockFromItem(item) instanceof CrystallizedCreature || Block.getBlockFromItem(item) instanceof TrimmedCrystallizedCreature){
                crystallizedCreatureItem = sample;
            }
        }

        // * Create item and copy nbt to it, adding statue tag to mobData for later render.
        ItemStack creatureStatue = new ItemStack(CometBlocks.CREATURE_STATUE);
        if (crystallizedCreatureItem.hasNbt()){
            NbtCompound crystallizedCreatureNBT = crystallizedCreatureItem.getNbt().copy();
            NbtCompound blockEntityTag = crystallizedCreatureNBT.getCompound("BlockEntityTag");
            NbtCompound mobData = blockEntityTag.getCompound("mobData");

            mobData.putString("StatueMaterial", "none");

            blockEntityTag.put("mobData", mobData);
            crystallizedCreatureNBT.put("BlockEntityTag", blockEntityTag);
            creatureStatue.setNbt(crystallizedCreatureNBT);
        }

        return creatureStatue;
    }

    @Override
    public boolean fits(int width, int height) {
        return width*height >= 9;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Comet.CREATURE_STATUE;
    }
}
