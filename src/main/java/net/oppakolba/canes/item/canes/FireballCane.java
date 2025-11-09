    package net.oppakolba.canes.item.canes;

    import net.minecraft.core.particles.ParticleTypes;
    import net.minecraft.world.effect.MobEffectInstance;
    import net.minecraft.world.effect.MobEffects;
    import net.minecraft.world.entity.LivingEntity;
    import net.minecraft.world.entity.player.Player;
    import net.minecraft.world.item.ItemStack;
    import net.minecraft.world.level.Level;
    import net.oppakolba.canes.init.ModEntities;
    import net.oppakolba.canes.entity.projectile.FireballEntity;
    import net.oppakolba.canes.item.misc.CanesItem;
    import org.jetbrains.annotations.NotNull;

    public class  FireballCane extends CanesItem {

        public FireballCane(Properties properties) {
            super(properties, 20);
        }


        @Override
        public void releaseUsing(@NotNull ItemStack stack, Level level, @NotNull LivingEntity entity, int timeLeft) {
            float power = 2 + getPower(stack) * 2;
            if (!level.isClientSide && entity instanceof Player player) {
                int charge = 1000 - timeLeft;

                if (charge < 20) {
                    return;
                }

                int currentMana = CanesItem.getMana(stack);
                int maxMana = CanesItem.getMaxMana(stack);

                if (currentMana >= 20) {
                    CanesItem.setMana(stack, currentMana - 20);
                    FireballEntity customFireball = new FireballEntity(ModEntities.CUSTOM_FIREBALL.get(), level, player,
                            player.getLookAngle().x, player.getLookAngle().y - 0.1f, player.getLookAngle().z, power);
                    customFireball.setOwner(player);
                    level.addFreshEntity(customFireball);


                    player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 40, 5));
                    player.getCooldowns().addCooldown(this, 40);
                }
            }
        }

        @Override
        public void onUseTick(Level level, @NotNull LivingEntity entity, @NotNull ItemStack stack, int count) {
            if (level.isClientSide) {
                if (entity instanceof Player player) {
                    randomSpawnParticles(ParticleTypes.FLAME, level, player, 2, 1, 2);
                }
            }
        }
    }


