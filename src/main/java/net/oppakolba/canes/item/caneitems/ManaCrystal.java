package net.oppakolba.canes.item.caneitems;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.oppakolba.canes.init.ModSounds;
import net.oppakolba.canes.mana.CanesManaProvider;
import net.oppakolba.canes.networking.ModMessage;


public class ManaCrystal extends Item {
    public ManaCrystal(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        ItemStack itemStack = context.getItemInHand();
        Level level = context.getLevel();
        Player player = context.getPlayer();
                level.playSound(null, player.getOnPos(), ModSounds.MANA_USE.get(), SoundSource.PLAYERS, 0.5f, level.random.nextFloat() * 0.1f + 0.9f);
                itemStack.shrink(1);
                player.getCooldowns().addCooldown(this, 40);

        return InteractionResult.CONSUME;
    }
}
