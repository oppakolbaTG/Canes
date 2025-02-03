package net.oppakolba.oppamod.item.Custom;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EyeItem extends BossSummoner {
    public EyeItem(EntityType<? extends Mob> defaultType, Properties properties) {
        super(defaultType, properties);

    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag tooltipFlag) {
        if (Screen.hasShiftDown()) {
            components.add(Component.literal("\n\nАртефакт, что пробуждает ужас лишь ночью. Днём он остаётся бесполезным, будто ждёт своего часа. Говорят, его нельзя обмануть: если вызвать не вовремя или зря потревожить, " +
                    "он просто наблюдает... и ждёт.").withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.ITALIC));
        } else {
            components.add(Component.literal("Призывает Глаз Ктулху").withStyle(ChatFormatting.WHITE));
        }

        super.appendHoverText(itemStack, level, components, tooltipFlag);
    }
}

