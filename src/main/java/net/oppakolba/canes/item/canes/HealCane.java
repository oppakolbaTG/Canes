package net.oppakolba.canes.item.canes;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.LazyOptional;
import net.oppakolba.canes.init.ModParticles;
import net.oppakolba.canes.item.misc.CanesItem;
import net.oppakolba.canes.mana.CanesMana;
import net.oppakolba.canes.mana.CanesManaProvider;

public class HealCane extends CanesItem {
    public HealCane(Properties pProperties) {
        super(pProperties);
    }



    @Override
    public void onUseTick(Level level, LivingEntity entity, ItemStack stack, int count) {
        if(!level.isClientSide) {
            if (entity instanceof Player player) {
                LazyOptional<CanesMana> manaOptional = player.getCapability(CanesManaProvider.CANES_MANA);
                if (manaOptional.isPresent()) {
                    CanesMana mana = manaOptional.orElseThrow(IllegalAccessError::new);
                    if (mana.getMana() >= 4) {
                        mana.subtractMana(4);
                        player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 40, 1));
                    }
                }
            }
        }
        if (level.isClientSide) {
            if (entity instanceof Player player) {
                LazyOptional<CanesMana> manaOptional = player.getCapability(CanesManaProvider.CANES_MANA);
                if (manaOptional.isPresent()) {
                    CanesMana mana = manaOptional.orElseThrow(IllegalAccessError::new);
                    if (mana.getMana() >= 4) {

                    } else if (mana.getMana() <= 4) {
                        level.addParticle(ParticleTypes.CRIT, player.getX(), player.getY(), player.getZ(), 0, -0.1, 0);
                    }
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
    }