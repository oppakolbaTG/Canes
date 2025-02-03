package net.oppakolba.oppamod.item.Custom;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ManaCrystal extends Item {
    public ManaCrystal(Properties p_41383_) {
        super(p_41383_);
    }


    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag tooltipFlag) {
        if(Screen.hasShiftDown()){
            components.add(Component.literal("\n\n Навсегда увеличивает максимальный запас маны на 20"
            ).withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.ITALIC));
        }
        super.appendHoverText(itemStack, level, components, tooltipFlag);
    }
}
