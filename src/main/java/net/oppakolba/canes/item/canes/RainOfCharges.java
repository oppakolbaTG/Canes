package net.oppakolba.canes.item.canes;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.oppakolba.canes.entity.projectile.ParticleCharge;
import net.oppakolba.canes.init.ModEntities;
import net.oppakolba.canes.item.misc.CanesCapability;
import net.oppakolba.canes.item.misc.CanesItem;
import org.jetbrains.annotations.NotNull;

public class RainOfCharges extends CanesItem {

    public RainOfCharges(Properties pProperties) {
        super(pProperties, 20);
    }

    @Override
    public void releaseUsing(@NotNull ItemStack stack, Level level, @NotNull LivingEntity entity, int pTimeCharged) {
        if (!level.isClientSide && entity instanceof Player player) {
            int charge = 1000 - pTimeCharged;

            if (charge < 20) {
                return;
            }
            int currentMana = getMana(stack);
            if (getMana(stack) >= 20) {
                setMana(stack, currentMana - 20);
                System.out.println(getMana(stack));
                for (int i = 1; i < 12; i++) {
                    ParticleCharge particleCharge = new ParticleCharge(ModEntities.PARTICLE_CHARGE.get(), level, player, i);
                    level.addFreshEntity(particleCharge);
                }
            }
            }
        }


    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack stack) {
        return UseAnim.BOW;
    }

}
