//package net.oppakolba.canes.entity.projectile;
//
//import com.mojang.blaze3d.vertex.PoseStack;
//import net.minecraft.client.Minecraft;
//import net.minecraft.core.BlockPos;
//import net.minecraft.core.Direction;
//import net.minecraft.network.protocol.Packet;
//import net.minecraft.util.datafix.fixes.ChunkPalettedStorageFix;
//import net.minecraft.world.damagesource.DamageSource;
//import net.minecraft.world.entity.Entity;
//import net.minecraft.world.entity.EntityType;
//import net.minecraft.world.entity.LivingEntity;
//import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
//import net.minecraft.world.entity.projectile.ProjectileUtil;
//import net.minecraft.world.level.Level;
//import net.minecraft.world.level.block.state.BlockState;
//import net.minecraft.world.phys.BlockHitResult;
//import net.minecraft.world.phys.EntityHitResult;
//import net.minecraft.world.phys.HitResult;
//import net.minecraft.world.phys.Vec3;
//import net.minecraftforge.network.NetworkHooks;
//import org.jetbrains.annotations.NotNull;
//
//public class DrillEntity extends AbstractHurtingProjectile {
//    int duration = 0;
//
//    public DrillEntity(EntityType<? extends AbstractHurtingProjectile> pEntityType, Level pLevel) {
//        super(pEntityType, pLevel);
//    }
//
//
//    public DrillEntity(EntityType<? extends AbstractHurtingProjectile> pEntityType, LivingEntity entity , Level pLevel, double xP, double yP, double zP) {
//        super(pEntityType, entity,xP * 0.0001, yP * 0.0001, zP * 0.0001, pLevel);
//        this.setPos(this.getX() + 0.5, this.getY() + 2, this.getZ() + 1);
//    }
//
//
//
//    @Override
//    protected void onHitEntity(@NotNull EntityHitResult pResult) {
//        if(!this.level.isClientSide){
//            pResult.getEntity().hurt(DamageSource.indirectMagic(this, this.getOwner()), 10);
//        }
//    }
//
//    @Override
//    protected void onHitBlock(@NotNull BlockHitResult pResult) {
//        if (!this.level.isClientSide) {
//            BlockPos hitPos = pResult.getBlockPos();
//            if (!this.level.getBlockState(this.blockPosition()).isAir()) {
//                for (int dx = -1; dx <= 1; dx++) {
//                    for (int dy = -1; dy <= 1; dy++) {
//                        for (int dz = -1; dz <= 1; dz++) {
//                            BlockPos pos = hitPos.offset(dx, dy, dz);
//                            BlockState state = this.level.getBlockState(pos);
//                            if (!state.isAir()) {
//                                this.level.destroyBlock(pos, true);
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }
//
//
//
//
//
//    @Override
//    public void tick() {
//        duration++;
//        Entity entity = this.getOwner();
//        if (this.level.isClientSide || (entity == null || !entity.isRemoved()) && this.level.hasChunkAt(this.blockPosition())) {
//            if(duration < 500) {
//                this.checkInsideBlocks();
//                Vec3 velocity = this.getDeltaMovement();
//                this.setDeltaMovement(velocity.scale(0.60));
//
//                double maxSpeed = 0.01;
//                if (velocity.length() > maxSpeed) {
//                    this.setDeltaMovement(velocity.normalize().scale(maxSpeed));
//                }
//
//                super.tick();
//
//                HitResult hitresult = ProjectileUtil.getHitResult(this, this::canHitEntity);
//                if (hitresult.getType() != HitResult.Type.MISS && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, hitresult)) {
//                    this.onHit(hitresult);
//                }
//
//                this.checkInsideBlocks();
//                Vec3 vec3 = this.getDeltaMovement();
//                double d0 = this.getX() + vec3.x;
//                double d1 = this.getY() + vec3.y;
//                double d2 = this.getZ() + vec3.z;
//                ProjectileUtil.rotateTowardsMovement(this, 0.2F);
//                float f = this.getInertia();
//                if (this.isInWater()) {
//                    for (int i = 0; i < 4; ++i) {
//                        float f1 = 0.25F;
//                    }
//                    f = 0.8F;
//                }
//
//                this.setDeltaMovement(vec3.add(this.xPower, this.yPower, this.zPower).scale(0.5));
//                this.setPos(d0, d1, d2);
//            }
//            else{
//                discard();
//            }
//        } else {
//            this.discard();
//        }
//    }
//
//    @Override
//    public @NotNull Packet<?> getAddEntityPacket() {
//        return NetworkHooks.getEntitySpawningPacket(this);
//    }
//
//    @Override
//    protected void defineSynchedData() {
//    }
//
//    @Override
//    protected boolean shouldBurn() {
//        return false;
//    }
//}
