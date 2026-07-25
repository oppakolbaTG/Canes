package net.oppakolba.mana_staffs.item;

import net.minecraft.world.item.ItemStack;
import net.oppakolba.mana_staffs.init.ModItems;

public class CreativeModeTab {
    public static final net.minecraft.world.item.CreativeModeTab MANASTAFFS_TAB = new net.minecraft.world.item.CreativeModeTab("manastaffstab"){
        @Override
        public ItemStack makeIcon() {return new ItemStack(ModItems.SAMPLE_STAFF.get());}
    };
    public static final net.minecraft.world.item.CreativeModeTab OPPA_TAB = new net.minecraft.world.item.CreativeModeTab("manastaffstab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.PLATINUM_BAR.get());
        }
    };
}
