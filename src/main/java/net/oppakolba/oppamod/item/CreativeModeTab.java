package net.oppakolba.oppamod.item;

import net.minecraft.world.item.ItemStack;
import net.oppakolba.oppamod.init.ModItems;

public class CreativeModeTab {
    public static final net.minecraft.world.item.CreativeModeTab CANES_TAB = new net.minecraft.world.item.CreativeModeTab("canestab"){
        @Override
        public ItemStack makeIcon() {return new ItemStack(ModItems.SAMPLE_CANE.get());}
    };
    public static final net.minecraft.world.item.CreativeModeTab OPPA_TAB = new net.minecraft.world.item.CreativeModeTab("oppamodtab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.ORANGE.get());
        }
    };
}
