package com.github.Soulphur0.dimensionalAlloys.recipe;

import com.github.Soulphur0.Comet;
import com.github.Soulphur0.CometClient;
import com.github.Soulphur0.dimensionalAlloys.block.CreatureStatue;
import com.github.Soulphur0.dimensionalAlloys.block.CrystallizedCreature;
import com.github.Soulphur0.registries.CometBlocks;
import net.minecraft.block.Block;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.Arrays;

public class CreatureStatueRecipe extends SpecialCraftingRecipe {

    public CreatureStatueRecipe(Identifier id) {
        super(id);
        System.out.println("I'VE BEEN CREATED!!"); // ! DEBUG
    }

    int[][] recipe = {{0,1,0}, {1,2,1}, {0,1,0}};

    @Override
    public boolean matches(CraftingInventory craftingInventory, World world) {
        int[][] input = new int[3][3];
        System.out.println("Matches been called!"); // ! DEBUG
        // * Print the crafting placement into array.
        int inventorySlot = 0;
        for (int i = 0; i < craftingInventory.getWidth(); i++){
            for (int j = 0; j < craftingInventory.getHeight(); j++) {
                ItemStack itemStack = craftingInventory.getStack(inventorySlot);
                if (itemStack.getItem() == Items.AMETHYST_SHARD) {
                    input[i][j] = 1;
                } else if (Block.getBlockFromItem(itemStack.getItem()) instanceof CrystallizedCreature) {
                    input[i][j] = 2;
                }
                inventorySlot++;
            }
        }

        // * Check crafting placement with recipe.
        for (int i = 0; i < recipe.length; i++){
            for (int j = 0; j < recipe[0].length; j++){
                System.out.println(Arrays.deepToString(input));
                if (recipe[i][j] != input[i][j])
                    return false;
            }
        }
        System.out.println("Has returned true.");
        return true;
    }

    @Override
    public ItemStack craft(CraftingInventory craftingInventory) {
        System.out.println("Craft has been called!"); // ! DEBUG
        ItemStack crystallizedCreatureHolder = ItemStack.EMPTY;
        int amethystShardCounter = 0;

        // * Check that there is, in fact, a crystallized creature and four amethyst shards.
        for(int i = 0; i < craftingInventory.size(); i++){
            ItemStack sample = craftingInventory.getStack(i);
            if (sample.isEmpty()) continue;
            Item item = sample.getItem();
            if (Block.getBlockFromItem(item) instanceof CrystallizedCreature){
                crystallizedCreatureHolder = sample;
                continue;
            }
            if (item == Items.AMETHYST_SHARD) amethystShardCounter++;
        }

        // * Create item and copy nbt to it.
        ItemStack creatureStatue = new ItemStack(CometBlocks.CREATURE_STATUE);
        if (crystallizedCreatureHolder.hasNbt())
            creatureStatue.setNbt(crystallizedCreatureHolder.getNbt().copy());

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
