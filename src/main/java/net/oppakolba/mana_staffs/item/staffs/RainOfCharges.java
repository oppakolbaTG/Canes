package net.oppakolba.mana_staffs.item.staffs;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.oppakolba.mana_staffs.entity.projectile.ParticleCharge;
import net.oppakolba.mana_staffs.init.ModEntities;
import net.oppakolba.mana_staffs.item.misc.StaffsItem;
import org.jetbrains.annotations.NotNull;

public class RainOfCharges extends StaffsItem {

    //Улучшить визуальную составляющую

    public RainOfCharges(Properties pProperties) {
        super(pProperties, 20);
    }

    @Override
    public void releaseUsing(@NotNull ItemStack stack, Level level, @NotNull LivingEntity entity, int pTimeCharged) {
        int amt = 2 + getAmt(stack) * 2;
        int power = 2 + getPower(stack) * 2;
        if (!level.isClientSide && entity instanceof Player player) {
          //  int charge = 1000 - pTimeCharged;

           // if (charge < 20) {
          //      return;
          //  }
            int currentMana = getMana(stack);
            if (getMana(stack) >= 20) {
                setMana(stack, currentMana - 20);
                for (int i = 1; i < amt; i++) {
                    ParticleCharge particleCharge = new ParticleCharge(ModEntities.PARTICLE_CHARGE.get(), level, player, i, power);
                    level.addFreshEntity(particleCharge);
                    level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.BUBBLE_COLUMN_BUBBLE_POP, SoundSource.MASTER, 0.5f, 1.80f);
                }
            }
        }
    }
}