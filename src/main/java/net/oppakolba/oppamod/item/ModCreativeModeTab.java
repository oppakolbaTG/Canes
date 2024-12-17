package net.oppakolba.oppamod.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeModeTab {
    public static final CreativeModeTab OPPA_TAB = new CreativeModeTab("oppamodtab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.ORANGE.get());
        }
    };
}
