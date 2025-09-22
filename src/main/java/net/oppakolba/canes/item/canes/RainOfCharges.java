package net.oppakolba.canes.item.canes;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.LazyOptional;
import net.oppakolba.canes.entity.projectile.ParticleCharge;
import net.oppakolba.canes.init.ModEntities;
import net.oppakolba.canes.item.misc.CanesItem;
import net.oppakolba.canes.mana.CanesMana;
import net.oppakolba.canes.mana.CanesManaProvider;

public class RainOfCharges extends CanesItem {

    public RainOfCharges(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entity, int pTimeCharged) {
        if (!level.isClientSide && entity instanceof Player player) {
            int charge = 1000 - pTimeCharged;

            if (charge < 20) {
                return;
            }

            LazyOptional<CanesMana> manaOptional = player.getCapability(CanesManaProvider.CANES_MANA);
            if (manaOptional.isPresent()) {
                CanesMana mana = manaOptional.orElseThrow(IllegalAccessError::new);
                if (mana.getMana() >= 15) {
                    mana.subtractMana(15);
                    for (int i = 1; i < 12; i++) {
                        ParticleCharge particleCharge = new ParticleCharge(ModEntities.PARTICLE_CHARGE.get(), level, player, i);
                        level.addFreshEntity(particleCharge);
                    }
                }
            }
        }
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }

}
