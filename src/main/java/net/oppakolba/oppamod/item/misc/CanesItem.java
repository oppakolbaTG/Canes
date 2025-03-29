package net.oppakolba.oppamod.item.misc;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class CanesItem extends Item implements ICanseItem{

    public CanesItem(Properties pProperties) {
        super(pProperties);

    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        player.startUsingItem(hand);
        return InteractionResultHolder.consume(stack);
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 1000;
    }


    public void randomSpawnParticles(ParticleOptions particle, Level level, Player player, int pX,int pY ,int pZ) {

        final double randomX = player.getRandomX(3);
        final double randomY = player.getRandomY();
        final double randomZ = player.getRandomZ(3);


        level.addParticle(particle, randomX, randomY, randomZ, pX, pY, pZ);
    }

}

