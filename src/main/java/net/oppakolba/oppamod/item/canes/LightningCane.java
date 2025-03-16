package net.oppakolba.oppamod.item.canes;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.LazyOptional;
import net.oppakolba.oppamod.init.ModParticles;
import net.oppakolba.oppamod.item.misc.CanesItem;
import net.oppakolba.oppamod.mana.PlayerMana;
import net.oppakolba.oppamod.mana.PlayerManaProvider;
import net.oppakolba.oppamod.networking.ModMessage;
import net.oppakolba.oppamod.networking.packet.ManaDataSyncS2CPacket;

public class LightningCane extends CanesItem {
    public LightningCane(Properties pProperties) {
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

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entity, int timeLeft) {
        if (!level.isClientSide && entity instanceof Player player) {
            int charge = 1000 - timeLeft;

            if (charge < 20) {
                 return;
            }

            LazyOptional<PlayerMana> manaOptional = player.getCapability(PlayerManaProvider.PLAYER_MANA);
            if (manaOptional.isPresent()) {
                PlayerMana mana = manaOptional.orElseThrow(IllegalAccessError::new);
                if (mana.getMana() >= 30) {
                    mana.subMana(30);
                    Vec3 lookAngle = player.getLookAngle();
                    Vec3 playerPos = player.position();


                    for(int i = 0; i < 10; i++) {
                        Vec3 targetPos = playerPos.add(lookAngle.scale(i + 2));
                        LightningBolt lightningBolt = new LightningBolt(EntityType.LIGHTNING_BOLT, level);
                        lightningBolt.setPos(targetPos.x, player.getY(), targetPos.z);
                        level.addFreshEntity(lightningBolt);
                        try {
                            Thread.sleep(40);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }

                    }
                    player.getCooldowns().addCooldown(this, 30);
                    if (player instanceof ServerPlayer serverPlayer) {
                        ModMessage.sendToPlayer(new ManaDataSyncS2CPacket(mana.getMana()), serverPlayer);
                    }
                }
            }
        }
    }


    @Override
    @SuppressWarnings("deprecation")
    public void onUseTick(Level level, LivingEntity entity, ItemStack stack, int count) {
        if(level.isClientSide){
            if(entity instanceof Player player){
                randomSpawnParticles(ModParticles.LIGHTNING_PARTICLE.get(), level, player);
            }
        }
    }




    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }
}
