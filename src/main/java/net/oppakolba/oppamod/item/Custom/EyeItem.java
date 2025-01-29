package net.oppakolba.oppamod.item.Custom;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.awt.event.ActionListener;
import java.util.List;
import java.util.Timer;

public class EyeItem extends ItemtoSummonBoss {
    public EyeItem(EntityType<? extends Mob> defaultType, int backgroundColor, int highlightColor, Properties properties) {
        super(defaultType, backgroundColor, highlightColor, properties);

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

