package net.oppakolba.canes.item.misc.mana;

import net.minecraft.world.item.ItemStack;

public interface ICanesMana {
    int getMana(ItemStack itemStack);
    int getMaxMana(ItemStack itemStack);
    void setMana(int mana, ItemStack itemStack);
    void setMaxMana(int maxMana, ItemStack itemStack);

    default void addMana(int amount, ItemStack itemStack) {}

    default void addManaAll(int amount) {}

    default void subtractMana(int amount, ItemStack itemStack) {}

    default boolean hasEnoughMana(int amount, ItemStack itemStack) {return false;}

    default boolean isFull(ItemStack itemStack){return false;}

}
