package net.oppakolba.canes.entity.projectile;

import lombok.Getter;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.*;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;

public class ParticleCharge extends ThrowableProjectile  {
    private LivingEntity target;
    private int damage;
    private Double sRadius = 20.0D;
    private final double maxSpeed = 0.5D;
    private final double acceleration = 0.2D;
    int liveTime = 500;
    int pValue = 0;
    @Getter
    public Player Owner;

    public ParticleCharge(EntityType<? extends ParticleCharge> particleChargeEntityType, Level level) {
        super(particleChargeEntityType, level);

    }

    public ParticleCharge(EntityType<? extends ParticleCharge> pEntityType, Level pLevel, Player player, int value, int power) {
        super(pEntityType, pLevel);
        this.damage = power;
        if(player != null) {
            setPos(player.getX(), player.getY() + 2 , player.getZ());
        }
        pValue = value;

    }



    @Override
    public void tick() {
        super.tick();
        Player player = this.level.getNearestPlayer(this, 50);
        if (player != null) {
            if (!this.level.isClientSide) {
                Vec3 startPos = this.position();
                Vec3 endPos = startPos.add(this.getDeltaMovement());
                EntityHitResult hitResult = findEntityHitResult(startPos, endPos);
                BlockHitResult blockHitResult = this.level.clip(new ClipContext(
                        startPos, endPos,
                        ClipContext.Block.COLLIDER,
                        ClipContext.Fluid.NONE,
                        this
                ));
                if (blockHitResult.getType() != HitResult.Type.MISS) {
                    this.onHit(blockHitResult);
                    return;
                }

                    if (tickCount < 4) {
                        notFindTarget();
                    }
                    if(tickCount >= 4) {

                        if (target == null || !target.isAlive() || tickCount % 20 == 0) {
                            findNewTarget(player);
                        }
                        if (target != null && target.isAlive()) {
                            moveTowardsTarget();
                            if (hitResult != null) {
                                Entity hitEntity = hitResult.getEntity();
                                if (hitEntity instanceof LivingEntity) {
                                    hitEntity.hurt(DamageSource.thorns(this), damage);
                                    discard();
                                }
                            }
                        }
                        else{
                            //this.setDeltaMovement(this.getDeltaMovement().add( 0.0F, -0.01,  0.0F));
                        }
                    }
                }else{
                level.addParticle(ParticleTypes.BUBBLE_POP, this.getX(), this.getY(), this.getZ(),0.3f, 0.3f, 0.3f);
            }

                this.move(MoverType.SELF, this.getDeltaMovement());

                float f = 0.98f;
                if (this.onGround) {
                    BlockPos pos =new BlockPos(this.getX(), this.getY() - 1.0D, this.getZ());
                    f = this.level.getBlockState(pos).getFriction(this.level, pos, this) * 0.98F;
                }

                if (tickCount >= liveTime) {
                    discard();
                }
            }
    }

    private EntityHitResult findEntityHitResult(Vec3 startPos, Vec3 endPos) {
        AABB movementArea = this.getBoundingBox().expandTowards(this.getDeltaMovement()).inflate(1.0D);

        for (Entity entity : this.level.getEntities(this, movementArea)) {
            if (entity instanceof LivingEntity && entity != this.getOwner()) {
                AABB entityBounds = entity.getBoundingBox().inflate(0.3D);
                Vec3 hitPos = entityBounds.clip(startPos, endPos).orElse(null);

                if (hitPos != null) {
                    return new EntityHitResult(entity, hitPos);
                }
            }
        }

        return null;
    }


    @Override
    protected void onHit(HitResult pResult) {
        super.onHit(pResult);
        this.discard();
    }

    public void notFindTarget(){
        Vec3 currentMovement = this.getDeltaMovement();
        double randomX = (random.nextDouble() - 0.5) * 0.2;
        double randomZ = (random.nextDouble() - 0.5) * 0.2;
        this.setDeltaMovement(currentMovement.x + randomX, currentMovement.y + 0.1 , currentMovement.z + randomZ);
    }

    public void moveTowardsTarget() {
        Vec3 toTarget = new Vec3(target.getX() - this.getX(),
                target.getY() + target.getEyeHeight() / 2.0D - this.getY(),
                target.getZ() - this.getZ());
        float friction = 0.98F;
        double distance = toTarget.length();

        if (distance < 64.0D) {
            double strength = 1.0D - Math.sqrt(distance) / 8.0D;
            Vec3 desiredMovement = toTarget.normalize().scale(strength * maxSpeed);
            Vec3 newMovement = this.getDeltaMovement().add(desiredMovement.subtract(this.getDeltaMovement()).scale(acceleration));
            if (newMovement.length() > maxSpeed) {
                newMovement = newMovement.normalize().multiply(friction, 0.98D, friction);
            }

            this.setDeltaMovement(newMovement);
        }
    }


    public void findNewTarget(Player player) {
        AABB area = new AABB(player.getX() - sRadius, player.getY() - sRadius,
                player.getZ() - sRadius, player.getX() + sRadius,
                player.getY() + sRadius, player.getZ() + sRadius);

        target = this.level.getNearestEntity(LivingEntity.class,
                TargetingConditions.forCombat().range(20).selector(null),
                player, player.getX(), player.getY(), player.getZ(), area);
    }

    @Override
    protected void defineSynchedData() {

    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        if (tag.contains("damage")) {
            this.damage = tag.getInt("damage");
        }
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        tag.putInt("damage", damage);
    }

    @Override
    public @NotNull Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
