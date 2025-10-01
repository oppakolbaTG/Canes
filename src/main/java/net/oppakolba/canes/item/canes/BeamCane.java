package net.oppakolba.canes.item.canes;

import lombok.extern.slf4j.Slf4j;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;
import net.oppakolba.canes.entity.projectile.BeamEntity;
import net.oppakolba.canes.init.ModEntities;
import net.oppakolba.canes.init.ModParticles;
import net.oppakolba.canes.item.misc.CanesCapability;
import net.oppakolba.canes.item.misc.CanesItem;
import org.jetbrains.annotations.NotNull;


@Slf4j
public class BeamCane extends CanesItem {
    protected final RandomSource random = RandomSource.create();
    /**
     * If the Euclidean distance to the moused-over block in meters is less than this value, the "Looking at" text will appear on the debug overlay.
     */
    public float beamRayDistance = 20.0f;
    boolean hit = false;



    public BeamCane(Properties pProperties) {
        super(pProperties, 20);
    }



    @Override
    public void releaseUsing(@NotNull ItemStack stack, Level level, @NotNull LivingEntity livingEntity, int timeCharged) {
        if (!level.isClientSide) {
            CompoundTag tag = stack.getOrCreateTag();
            resetBeam(level, tag);


        }
    }


    /**
     * Наносит урон
     **/

    @Override
    public void onUseTick(@NotNull Level level, @NotNull LivingEntity entity, ItemStack stack, int count) {
        super.onUseTick(level, entity, stack, count);
        CompoundTag tag = stack.getOrCreateTag();
        if (entity instanceof Player player) {
            if (!level.isClientSide) {
                if (count % 20 == 0) {

                    int currentMana = CanesItem.getMana(stack);
                        if (CanesItem.getMana(stack) >= 4) {
                            CanesItem.setMana(stack, currentMana - 4);


                            Vec3 lookVec = player.getLookAngle();
                            Vec3 playerEyePos = player.getEyePosition();
                            Vec3 pLookPos = playerEyePos.add(lookVec.scale(5.0));
                            if (!tag.contains("BeamId")) {
                                BeamEntity beam = new BeamEntity(ModEntities.BEAM_ENTITY.get(), level, lookVec, beamRayDistance, player, pLookPos);
                                level.addFreshEntity(beam);
                                tag.putInt("BeamId", beam.getId());


                            }
                            if (tag.contains("BeamId")) {
                                int beamId = tag.getInt("BeamId");
                                Entity beamEntity = level.getEntity(beamId);

                                if (beamEntity == null || !beamEntity.isAlive()) {
                                    tag.remove("BeamId");
                                }
                            }


                            Entity lEntity = getPlayerLookAtEntity(player, beamRayDistance);
                            if (lEntity != null) {
                                    lEntity.hurt(DamageSource.sting(player), 8);
                                    hit = true;
                                    System.out.println(lEntity);
                            } else {
                                hit = false;
                            }
                        } else {
                            resetBeam(level, tag);
                        }
                }
            } else {
                if (tag.contains("BeamId")) {

                    BlockHitResult blockHit = getPlayerLookAtBlock(player, beamRayDistance);
                    Vec3 hitPos = blockHit.getLocation();

                    Entity lookedAtEntity = getPlayerLookAtEntity(player, beamRayDistance);


                    for (int i = 0; i < 12; i++) {
                        if (blockHit.getType() != HitResult.Type.MISS) {
                            level.addParticle(ModParticles.BEAM_EXPLOSION_PARTICLE.get(),
                                    hitPos.x + offset(0.5),
                                    hitPos.y + offset(0.5) - 0.2,
                                    hitPos.z + offset(0.5),
                                    (this.random.nextDouble() - 0.5) * 0.8D,
                                    Math.pow(-1, i) * (this.random.nextDouble()) * 0.6D,
                                    (this.random.nextDouble() - 0.5) * 0.8D
                            );
                        } else if (blockHit.getType() != HitResult.Type.MISS) {

                            level.addParticle(ModParticles.BEAM_EXPLOSION_PARTICLE.get(),
                                    hitPos.x + offset(0.5),
                                    hitPos.y + offset(0.5) - 0.2,
                                    hitPos.z + offset(0.5),
                                    (this.random.nextDouble() - 0.5) * 0.8D,
                                    Math.pow(-1, i) * (this.random.nextDouble()) * 0.6D,
                                    (this.random.nextDouble() - 0.5) * 0.8D
                            );
                        }
                    }



                Vec3 startPos = player.position().subtract(0.65 * player.getBbWidth(), 0.1 * player.getBbWidth(), 0.65 * player.getBbWidth());
                Vec3 endPos = lookedAtEntity != null && hit ?
                        new Vec3(lookedAtEntity.getX(), lookedAtEntity.getY() + lookedAtEntity.getBbHeight() / 2, lookedAtEntity.getZ()) :
                        hitPos;

                Vec3 direction = endPos.subtract(startPos);

            for (int i = 0; i < 14; i++) {
                for (int j = 0; j < 3; j++) {
                    double progress = i / 14.0;
                    Vec3 particlePos = startPos.add(direction.scale(progress));
                    level.addParticle(ModParticles.BEAM_PARTICLE.get(),
                            particlePos.x + offset(0.1),
                            particlePos.y + offset(0.1),
                            particlePos.z + offset(0.1),
                            0.3, 0.3, 0.3);

                }
            }
        }


            }
        }
    }




    private double offset(double koef) {
        return this.random.nextDouble() - koef;
    }


    public static BlockHitResult getPlayerLookAtBlock(Player player, double reachDistance) {
        Vec3 eyePos = player.getEyePosition(1.0F);
        Vec3 lookVec = player.getViewVector(1.0F);
        Vec3 endPos = eyePos.add(lookVec.x * reachDistance, lookVec.y * reachDistance, lookVec.z * reachDistance);

        return player.level.clip(new ClipContext(eyePos, endPos, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, player));

    }

    public static Entity getPlayerLookAtEntity(Player player, double reachDistance) {
        Vec3 eyePos = player.getEyePosition(1.0F);
        Vec3 lookVec = player.getViewVector(1.0F);
        Vec3 endPos = eyePos.add(lookVec.x * reachDistance, lookVec.y * reachDistance, lookVec.z * reachDistance);

        EntityHitResult result = ProjectileUtil.getEntityHitResult(
                player,
                eyePos,
                endPos,
                new AABB(eyePos, endPos),
                e -> !e.isSpectator() && e.isPickable(),
                reachDistance * reachDistance
        );
        return result != null ? result.getEntity() : null;
    }


    private void resetBeam(Level level, CompoundTag tag) {
        if (tag.contains("BeamId")) {
            int beamId = tag.getInt("BeamId");
            Entity entity = level.getEntity(beamId);
            if (entity instanceof BeamEntity beam) {
                beam.discard();
            }
            tag.remove("BeamId");
        }
    }
}
