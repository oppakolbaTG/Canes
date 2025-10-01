package net.oppakolba.canes.item.canes;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.oppakolba.canes.init.ModParticles;
import net.oppakolba.canes.item.misc.CanesCapability;
import net.oppakolba.canes.item.misc.CanesItem;
import org.jetbrains.annotations.NotNull;

public class HealCane extends CanesItem {
    public HealCane(Properties pProperties) {
        super(pProperties, 20);
    }



    @Override
    public void onUseTick(Level level, @NotNull LivingEntity entity, @NotNull ItemStack stack, int count) {
        super.onUseTick(level, entity, stack, count);
        if(!level.isClientSide) {
            if (entity instanceof Player player) {
                int currentMana = getMana(stack);
                    if (getMana(stack) >= 4) {
                        setMana(stack, currentMana - 4);
                        System.out.println(getMana(stack));
                        player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 40, 1));
                    }
            }
        }
        if (level.isClientSide) {
            if (entity instanceof Player player) {
                stack.getCapability(CanesCapability.MANA_CAPABILITY).ifPresent(cap -> {

                if (cap.getMana(stack) >= 4) {

                    level.addParticle(ParticleTypes.CRIT, player.getX(), player.getY(), player.getZ(), 0, -0.1, 0);
                }
            });


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
    }