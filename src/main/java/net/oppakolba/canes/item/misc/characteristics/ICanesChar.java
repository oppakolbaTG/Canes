package net.oppakolba.canes.item.misc.characteristics;

import net.minecraft.world.item.ItemStack;

public interface ICanesChar {
    public int getPower(ItemStack stack);
    public int getRadius(ItemStack stack);
    public int getHeal(ItemStack stack);
    public int getAmt(ItemStack stack);

    public default void addPower(ItemStack stack, int value){}
    public default void addRadius(ItemStack stack, int value){}
    public default void addHeal(ItemStack stack, int value){}
    public default void addAmt(ItemStack stack, int value){}

}
