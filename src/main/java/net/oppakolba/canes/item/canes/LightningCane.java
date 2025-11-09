package net.oppakolba.canes.item.canes;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.oppakolba.canes.init.ModParticles;
import net.oppakolba.canes.item.misc.CanesItem;
import org.jetbrains.annotations.NotNull;

public class LightningCane extends CanesItem {
    public LightningCane(Properties pProperties) {
        super(pProperties,20);
    }


    @Override
    public int getUseDuration(ItemStack stack) {
        return 1000;
    }

    @Override
    public void releaseUsing(@NotNull ItemStack stack, Level level, @NotNull LivingEntity entity, int timeLeft) {
        int amt = 1 + getAmt(stack) * 2;
        if (!level.isClientSide && entity instanceof Player player) {
            int charge = 1000 - timeLeft;

            if (charge < 20) {
                return;
            }
            int currentMana = getMana(stack);
            if (getMana(stack) >= 20) {
                setMana(stack, currentMana - 20);
                System.out.println(getMana(stack));
                Vec3 lookAngle = player.getLookAngle();
                Vec3 playerPos = player.position();


                for (int i = 0; i < amt; i++) {
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

            }
            }
        }



    @Override
    public void onUseTick(Level level, @NotNull LivingEntity entity, @NotNull ItemStack stack, int count) {
        super.onUseTick(level, entity, stack, count);
        if(level.isClientSide){
            if(entity instanceof Player player){
                randomSpawnParticles(ModParticles.LIGHTNING_PARTICLE.get(), level, player, 2, 1, 2);
                    System.out.println(getMana(stack));
            }
        }
    }




    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack stack) {
        return UseAnim.BOW;
    }
}