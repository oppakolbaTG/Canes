package net.oppakolba.oppamod.item.canes;

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
import net.oppakolba.oppamod.init.ModEntities;
import net.oppakolba.oppamod.entity.custom.CustomFireball;
import net.oppakolba.oppamod.item.misc.CanesItem;
import net.oppakolba.oppamod.mana.PlayerMana;
import net.oppakolba.oppamod.mana.PlayerManaProvider;
import net.oppakolba.oppamod.networking.ModMessage;
import net.oppakolba.oppamod.networking.packet.ManaDataSyncS2CPacket;

public class  FireballCane extends CanesItem {
    public FireballCane(Properties properties){
        super(properties);
    }



    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entity, int timeLeft) {
        if (!level.isClientSide && entity instanceof Player player) {
            int charge = 1000 - timeLeft;

            if (charge < 20) { player.sendSystemMessage(Component.literal("Зарядка слишком короткая!"));
                return;
            }

            LazyOptional<PlayerMana> manaOptional = player.getCapability(PlayerManaProvider.PLAYER_MANA);
            if (manaOptional.isPresent()) {
                PlayerMana mana = manaOptional.orElseThrow(IllegalAccessError::new);
                if (mana.getMana() >= 10) {
                    mana.subMana(10);

                    CustomFireball customFireball = new CustomFireball(ModEntities.CUSTOM_FIREBALL.get(), level, player,
                            player.getLookAngle().x, player.getLookAngle().y - 0.1f, player.getLookAngle().z, 6.0f);

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


    /**
      add some flame particles with random generation
     */
    @Override
    public void onUseTick(Level level, LivingEntity entity, ItemStack stack, int count) {
        if(level.isClientSide){
            if(entity instanceof Player player){
                randomSpawnParticles(ParticleTypes.FLAME , level, player);
                }
                //FireballSeal fireballSeal = new FireballSeal(ModEntities.FIREBALL_SEAL.get(), level, player);
               // level.addFreshEntity(fireballSeal);
            }
        }
    }
