package net.oppakolba.canes.item.canes;


import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.LazyOptional;
import net.oppakolba.canes.init.ModParticles;
import net.oppakolba.canes.item.misc.CanesItem;
import net.oppakolba.canes.mana.CanesMana;
import net.oppakolba.canes.mana.CanesManaProvider;

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

            LazyOptional<CanesMana> manaOptional = player.getCapability(CanesManaProvider.CANES_MANA);
            if (manaOptional.isPresent()) {
                CanesMana mana = manaOptional.orElseThrow(IllegalAccessError::new);
                if (mana.getMana() >= 30) {
                    mana.subtractMana(30);
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

                }
            }
        }
    }


    @Override
    @SuppressWarnings("deprecation")
    public void onUseTick(Level level, LivingEntity entity, ItemStack stack, int count) {
        if(level.isClientSide){
            if(entity instanceof Player player){
                randomSpawnParticles(ModParticles.LIGHTNING_PARTICLE.get(), level, player, 2, 1, 2);
            }
        }
    }




    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }
}