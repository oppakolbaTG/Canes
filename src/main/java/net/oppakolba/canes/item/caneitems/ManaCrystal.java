package net.oppakolba.canes.item.caneitems;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.oppakolba.canes.entity.orbs.ManaOrb;
import net.oppakolba.canes.init.ModSounds;

import java.util.Random;


public class ManaCrystal extends Item {
    Random random = new Random();
    private final int value = random.nextInt(3,6);
    public ManaCrystal(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        ItemStack itemStack = context.getItemInHand();
        Level level = context.getLevel();
        Player player = context.getPlayer();
        if(!level.isClientSide) {
            if (player != null) {
                for (int i = 0; i < value; i++) {
                    ManaOrb manaOrb = ManaOrb.spawnOrbWithPop(level, player.getX(), player.getY(), player.getZ());
                    level.addFreshEntity(manaOrb);

                }
            }
            level.playSound(null, player.getOnPos(), SoundEvents.GLASS_BREAK, SoundSource.PLAYERS, 1.0f, 1.30f);
            itemStack.shrink(1);
        }

        return InteractionResult.CONSUME;
    }
}
