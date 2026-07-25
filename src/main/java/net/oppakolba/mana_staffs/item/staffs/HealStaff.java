package net.oppakolba.mana_staffs.item.staffs;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.oppakolba.mana_staffs.init.ModParticles;
import net.oppakolba.mana_staffs.item.misc.StaffsItem;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class HealStaff extends StaffsItem {
    public HealStaff(Properties pProperties) {
        super(pProperties, 20);
    }



    @Override
    public void onUseTick(Level level, @NotNull LivingEntity entity, @NotNull ItemStack stack, int count) {
        int heal = getHeal(stack);
        float radius = 6 + getRadius(stack) * 1.5f;
        super.onUseTick(level, entity, stack, count);
        if(!level.isClientSide) {
            if (entity instanceof Player player) {
                int currentMana = getMana(stack);
                    if (getMana(stack) >= 4) {
                        setMana(stack, currentMana - 4);

                        List<Player> allPlayers = (List<Player>) level.players();

                        for (Player targetPlayer : allPlayers) {
                            if (targetPlayer.distanceTo(player) <= radius) {
                                targetPlayer.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 40, heal));
                            }
                        }
                        player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 40, heal));
                    }
            }
        }
        if (level.isClientSide) {
            if (entity instanceof Player player) {
                if (getMana(stack) >= 4) {
                    level.addParticle(ParticleTypes.CRIT, player.getX(), player.getY(), player.getZ(), 0, -0.1, 0);
                }


                    for (int i = 0; i < 8; i++) {
                        randomSpawnParticles(ParticleTypes.HEART, level, player, 100, -1, 100);
                    }
                    for (int i = 0; i < 10; i++) {
                        double x = player.getX() + Math.random() * 10 - 5;
                        double y = player.getY() + 10;
                        double z = player.getZ() + Math.random() * 10 - 5;

                        level.addParticle(ModParticles.HEAL_LINE_PARTICLE.get(), x, y, z, 0, -0.2, 0);
                    }
                }

            }


        }

    @Override
    public void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity, int pTimeCharged) {
        if(pLivingEntity instanceof Player player)
        pLevel.playSound(player, pLivingEntity.blockPosition(), SoundEvents.BEACON_DEACTIVATE, SoundSource.MASTER, 0.7f, 1.99f);
        super.releaseUsing(pStack, pLevel, pLivingEntity, pTimeCharged);
    }
}