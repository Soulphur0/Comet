package com.github.Soulphur0.dimensionalAlloys.recipe;

import com.github.Soulphur0.Comet;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

public class EndbriteElytraChestplateItemRecipe extends SpecialCraftingRecipe {


    public EndbriteElytraChestplateItemRecipe(Identifier id) {
        super(id);
    }

    @Override
    public boolean matches(CraftingInventory inventory, World world) {
        // + Look for the chestplated elytra.
        int inventorySlot = 0;
        for (int i = 0; i < inventory.getWidth(); i++){
            for (int j = 0; j < inventory.getHeight(); j++) {
                ItemStack itemStack = inventory.getStack(inventorySlot);
                if (itemStack.getItem() == Comet.ENDBRITE_ELYTRA_CHESTPLATE) {
                    return true;
                }
                inventorySlot++;
            }
        }
        return false;
    }

    @Override
    public ItemStack craft(CraftingInventory inventory) {
        // + Get chestplated elytra in the crafting grid.
        int chestplatedElytraSlot = 0;
        for (int i = 0; i < inventory.getWidth(); i++){
            for (int j = 0; j < inventory.getHeight(); j++) {
                ItemStack itemStack = inventory.getStack(chestplatedElytraSlot);
                if (itemStack.getItem() == Comet.ENDBRITE_ELYTRA_CHESTPLATE) {
                    break;
                }
                chestplatedElytraSlot++;
            }
        }
        // + Get elytra data from the chestplated elytra end put it into the return item.
        NbtCompound elytraChestplateData = inventory.getStack(chestplatedElytraSlot).getOrCreateNbt();
        NbtCompound elytraData = elytraChestplateData.getCompound("elytraData");
        ItemStack elytraItemStack = new ItemStack(Items.ELYTRA);
        elytraItemStack.setNbt(elytraData);

        return elytraItemStack;
    }

    @Override
    public DefaultedList<ItemStack> getRemainder(CraftingInventory inventory) {
        // + Get chestplated elytra in the crafting grid.
        int chestplatedElytraSlot = 0;
        for (int i = 0; i < inventory.getWidth(); i++){
            for (int j = 0; j < inventory.getHeight(); j++) {
                ItemStack itemStack = inventory.getStack(chestplatedElytraSlot);
                if (itemStack.getItem() == Comet.ENDBRITE_ELYTRA_CHESTPLATE) {
                    break;
                }
                chestplatedElytraSlot++;
            }
        }
        // + Turn the combined chestplate of the crafting grid into a regular chestplate.
        NbtCompound elytraChestplateData = inventory.getStack(chestplatedElytraSlot).getOrCreateNbt();
        ItemStack chestplate = new ItemStack(Comet.ENDBRITE_CHESTPLATE);
        chestplate.setNbt(elytraChestplateData);

        // + Create a defaulted list with all air except the slot to replace the item.
        DefaultedList<ItemStack> remainder = DefaultedList.of();
        int slotToReplace = 0;
        for (int i = 0; i < inventory.getWidth(); i++){
            for (int j = 0; j < inventory.getHeight(); j++) {
                ItemStack itemStack = inventory.getStack(slotToReplace);
                if (itemStack.getItem() == Comet.ENDBRITE_ELYTRA_CHESTPLATE) {
                    remainder.add(slotToReplace, chestplate);
                    slotToReplace++;
                    continue;
                }
                remainder.add(slotToReplace, new ItemStack(Items.AIR));
                slotToReplace++;
            }
        }

        return remainder;
    }

    @Override
    public boolean fits(int width, int height) {
        return width*height >= 2;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Comet.ENDBRITE_ELYTRA_CHESTPLATE_RECIPE;
    }
}
