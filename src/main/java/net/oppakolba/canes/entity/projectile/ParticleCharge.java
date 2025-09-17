package net.oppakolba.canes.entity.projectile;

import lombok.Getter;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;

public class ParticleCharge extends Entity {
    private LivingEntity target;
    private int damage;
    private Double sRadius = 20.0D;
    private final double maxSpeed = 1.3D;
    private final double acceleration = 0.05D;
    int liveTime = 500;
    int pValue = 0;
    boolean waitFor = true;
    @Getter
    public Player Owner;

    public ParticleCharge(EntityType<? extends ParticleCharge> particleChargeEntityType, Level level) {
        super(particleChargeEntityType, level);
        this.damage = random.nextInt(1, 2);

    }

    public ParticleCharge(EntityType<? extends ParticleCharge> pEntityType, Level pLevel, Player player, int value) {
        super(pEntityType, pLevel);
        this.damage = random.nextInt(1, 2);
        if(player != null) {
            setPos(player.getX(), player.getY() + 2 , player.getZ());
        }
        if(player == null){
            System.out.println("player is null !!!!!!!!!!!!!!!!!!!");
        }
        pValue = value;
        System.out.println("Создаем");
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

                    if (tickCount < 4) {
                        notFindTarget(player);
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
                    }


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


    public void notFindTarget(Player player){
        Vec3 currentMovement = this.getDeltaMovement();
        double randomX = (random.nextDouble() - 0.5) * 0.2;
        double randomZ = (random.nextDouble() - 0.5) * 0.2;
        this.setDeltaMovement(currentMovement.x + randomX, currentMovement.y + 0.1 , currentMovement.z + randomZ);
    }

    private void moveTowardsTarget() {
        Vec3 toTarget = new Vec3(target.getX() - this.getX(),
                target.getY() + target.getEyeHeight() / 2.0D - this.getY(),
                target.getZ() - this.getZ());
        float friction = 0.98F;
        double distance = toTarget.length();

        if (distance < 64.0D) {
            double strength = 1.0D - Math.sqrt(distance) / 8.0D;
            Vec3 desiredMovement = toTarget.normalize().scale(strength * maxSpeed);
            Vec3 currentMovement = this.getDeltaMovement();
            Vec3 newMovement = currentMovement.add(
                    desiredMovement.subtract(currentMovement).scale(acceleration)
            );
            if (newMovement.length() > maxSpeed) {
                newMovement = newMovement.normalize().multiply(friction, 0.98D, friction);
            }

            this.setDeltaMovement(newMovement);
        }
    }


    private void findNewTarget(Player player) {
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
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
