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
import net.oppakolba.canes.mana.PlayerManaProvider;
import net.oppakolba.canes.networking.ModMessage;
import net.oppakolba.canes.networking.packet.ManaDataSyncS2CPacket;

public class ManaCrystal extends Item {
    public ManaCrystal(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        ItemStack itemStack = context.getItemInHand();
        Level level = context.getLevel();
        Player player = context.getPlayer();
        if (player != null && !level.isClientSide()) {
            player.getCapability(PlayerManaProvider.PLAYER_MANA).ifPresent(mana -> {

                int maxMana = mana.getMAX_MANA();
                int currentMana = mana.getMana();
                if(mana.getMana() != maxMana) {
                    mana.addMana(20);
                    if (mana.getMana() > maxMana) {
                        int excessMana = mana.getMana() - maxMana;
                        mana.subMana(excessMana);
                    }
                }
                if (mana.getMana() != currentMana) {
                    if (player instanceof ServerPlayer serverPlayer) {
                        ModMessage.sendToPlayer(new ManaDataSyncS2CPacket(mana.getMana()), serverPlayer);
                    }
                }
                level.playSound(null, player.getOnPos(), ModSounds.MANA_USE.get(), SoundSource.PLAYERS, 0.5f, level.random.nextFloat() * 0.1f + 0.9f);
                itemStack.shrink(1);
                player.getCooldowns().addCooldown(this, 40);
            });
        }
        return InteractionResult.CONSUME;
    }
}
