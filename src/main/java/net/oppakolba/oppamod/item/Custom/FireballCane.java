package net.oppakolba.oppamod.item.Custom;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.LazyOptional;
import net.oppakolba.oppamod.init.ModEntities;
import net.oppakolba.oppamod.entity.custom.CustomFireball;
import net.oppakolba.oppamod.mana.PlayerMana;
import net.oppakolba.oppamod.mana.PlayerManaProvider;
import net.oppakolba.oppamod.networking.ModMessage;
import net.oppakolba.oppamod.networking.packet.ManaDataSyncS2CPacket;

public class  FireballCane extends Item {
    public FireballCane(Properties properties){
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        player.startUsingItem(hand);
        return InteractionResultHolder.consume(stack);
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 1000; // Максимальное время зарядки (как у лука)
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entity, int timeLeft) {
        if (!level.isClientSide && entity instanceof Player player) {
            int charge = 1000 - timeLeft; // Теперь правильно вычисляем длительность зарядки

            if (charge < 20) {
                player.sendSystemMessage(Component.literal("Зарядка слишком короткая!")); // Предупреждение
                return;
            }

            // Проверяем наличие маны
            LazyOptional<PlayerMana> manaOptional = player.getCapability(PlayerManaProvider.PLAYER_MANA);
            if (manaOptional.isPresent()) {
                PlayerMana mana = manaOptional.orElseThrow(IllegalAccessError::new);
                if (mana.getMana() >= 10) {
                    mana.subMana(10);

                    // Создание кастомного fireball
                    CustomFireball customFireball = new CustomFireball(ModEntities.CUSTOM_FIREBALL.get(), level, player,
                            player.getLookAngle().x, player.getLookAngle().y, player.getLookAngle().z, 6.0f);
                    level.addFreshEntity(customFireball);

                    // Накладываем эффект сопротивления урону
                    player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 40, 5));

                    // Добавляем откат на предмет
                    player.getCooldowns().addCooldown(this, 40);

                    // Синхронизируем ману с клиентом
                    if (player instanceof ServerPlayer serverPlayer) {
                        ModMessage.sendToPlayer(new ManaDataSyncS2CPacket(mana.getMana()), serverPlayer);
                    }
                } else {
                    player.sendSystemMessage(Component.literal("Мало маны!"));
                }
            }
        }
    }

    @Override
    public void onUseTick(Level world, LivingEntity entity, ItemStack stack, int count) {
        if (world.isClientSide) {
            world.addParticle(ParticleTypes.FLAME, entity.getX(), entity.getEyeY(), entity.getZ(), 0, 0, 0);
        }
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }
}

