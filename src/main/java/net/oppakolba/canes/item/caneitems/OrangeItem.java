package net.oppakolba.canes.item.caneitems;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class OrangeItem extends Item {
    public OrangeItem(Properties properties) {
        super(properties);
    }


    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag tooltipFlag) {
        if(Screen.hasShiftDown()){
            components.add(Component.literal("\n\nМне кажется странным, что он падает с кровавого алтаря, и непонятно, " +
                    "почему к нему так хорошо притягиваются золотые слитки.").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.YELLOW));
        }
        else {
            components.add(Component.literal("Выглядит съедобно").withStyle(ChatFormatting.WHITE));
        }
        super.appendHoverText(itemStack, level, components, tooltipFlag);
    }
}
