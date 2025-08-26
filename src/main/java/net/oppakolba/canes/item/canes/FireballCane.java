package net.oppakolba.canes.item.canes;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.LazyOptional;
import net.oppakolba.canes.init.ModEntities;
import net.oppakolba.canes.entity.projectile.FireballEntity;
import net.oppakolba.canes.item.misc.CanesItem;
import net.oppakolba.canes.mana.PlayerMana;
import net.oppakolba.canes.mana.PlayerManaProvider;
import net.oppakolba.canes.networking.ModMessage;
import net.oppakolba.canes.networking.packet.ManaDataSyncS2CPacket;

public class  FireballCane extends CanesItem {
    public FireballCane(Properties properties){
        super(properties);
    }




    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entity, int timeLeft) {
        if (!level.isClientSide && entity instanceof Player player) {
            int charge = 1000 - timeLeft;

            if (charge < 20) { System.out.println("Зарядка слишком короткая!");
                return;
            }

            LazyOptional<PlayerMana> manaOptional = player.getCapability(PlayerManaProvider.PLAYER_MANA);
            if (manaOptional.isPresent()) {
                PlayerMana mana = manaOptional.orElseThrow(IllegalAccessError::new);
                if (mana.getMana() >= 10) {
                    mana.subMana(10);

                    FireballEntity customFireball = new FireballEntity(ModEntities.CUSTOM_FIREBALL.get(), level, player,
                            player.getLookAngle().x, player.getLookAngle().y - 0.1f, player.getLookAngle().z, 6.0f);
                    customFireball.setOwner(player);
                    level.addFreshEntity(customFireball);
                    player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 40 , 5));

                    player.getCooldowns().addCooldown(this, 40);
                    if (player instanceof ServerPlayer serverPlayer) {
                        ModMessage.sendToPlayer(new ManaDataSyncS2CPacket(mana.getMana()), serverPlayer);
                    }
                }
            }
        }
    }


    @Override
    public void onUseTick(Level level, LivingEntity entity, ItemStack stack, int count) {
        if(level.isClientSide){
            if(entity instanceof Player player){
                randomSpawnParticles(ParticleTypes.FLAME , level, player, 2 ,1,2);
                }

            }
        }
    }
