package net.oppakolba.oppamod.item.canes;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;
import net.minecraftforge.common.util.LazyOptional;
import net.oppakolba.oppamod.item.misc.CanesItem;
import net.oppakolba.oppamod.mana.PlayerMana;
import net.oppakolba.oppamod.mana.PlayerManaProvider;

public class BeamCane extends CanesItem {
    double maxDistance = 10.0D;

    public BeamCane(Properties pProperties) {
        super(pProperties);
    }


    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity livingEntity, int timeCharged) {

    }

    @Override
    @Override
    public void onUseTick(Level level, LivingEntity entity, ItemStack stack, int count) {
        if (entity instanceof Player player) {
            // 1. Серверная логика: проверка и трата маны, эффекты
            if (!level.isClientSide) {
                if (count % 20 == 0) { // Каждые 20 тиков, для примера
                    LazyOptional<PlayerMana> manaOptional = player.getCapability(PlayerManaProvider.PLAYER_MANA);
                    if (manaOptional.isPresent()) {
                        PlayerMana mana = manaOptional.orElseThrow(IllegalAccessError::new);
                        if (mana.getMana() >= 4) {
                            mana.subMana(4);

                            Vec3 start = player.getEyePosition(1.0F);
                            Vec3 lookVec = player.getLookAngle();
                            Vec3 horizontalLook = new Vec3(lookVec.x, 0, lookVec.z).normalize();
                            Vec3 end = start.add(horizontalLook.scale(maxDistance));

                            BlockHitResult hitResult = player.level.clip(new ClipContext(
                                    start,
                                    end,
                                    ClipContext.Block.OUTLINE,
                                    ClipContext.Fluid.NONE,
                                    player
                            ));

                            if (hitResult.getType() == HitResult.Type.BLOCK) {
                                BlockPos blockPos = hitResult.getBlockPos();
                                // ПРИМЕНЯЙ ЭФФЕКТЫ ЗДЕСЬ (например, поджечь блок)
                            }
                        }
                    }
                }
            }

            // 2. Клиентская часть: визуализация луча
            if (level.isClientSide) {
                // Спаун BeamEntity только на клиенте!
                Vec3 start = player.getEyePosition(1.0F);
                Vec3 lookVec = player.getLookAngle();
                float[] color = {1.0F, 0.5F, 1.0F}; // Любой цвет
                int life = 4; // тики
                float length = 10.0F;

                BeamEntity beam = new BeamEntity(level, start, lookVec, length, color, life);
                level.addFreshEntity(beam);
            }
        }
    }

}
