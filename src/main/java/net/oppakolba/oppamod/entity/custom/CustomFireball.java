package net.oppakolba.oppamod.entity.custom;

import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.network.NetworkHooks;

public class CustomFireball extends AbstractHurtingProjectile  {
    private static final EntityDataAccessor<Float> POWER = SynchedEntityData.defineId(CustomFireball.class, EntityDataSerializers.FLOAT);

    public CustomFireball(EntityType<? extends CustomFireball> entityType, Level level){
        super(entityType, level);
    }

    public CustomFireball(EntityType<? extends CustomFireball> entityType, Level level, LivingEntity entity, double xP, double yP, double zP, float power){
        super(entityType, entity, xP, yP, zP, level);
        this.entityData.set(POWER, power);
        this.setPos(this.getX(), this.getY() + 2, this.getZ());
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(POWER, 1.5f);
    }

    @Override
    public void tick() {
        super.tick();;

    }

    public float getPower(){
        return this.entityData.get(POWER);
    }


    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        if(!this.level.isClientSide()){
            this.level.explode(null , this.getX(), this.getY(), this.getZ(),getPower() / 2.0f, Explosion.BlockInteraction.DESTROY);
            this.discard();
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult blockHitResult) {
        super.onHitBlock(blockHitResult);
        if(!this.level.isClientSide()){
            this.level.explode(null , this.getX(), this.getY(), this.getZ(),getPower() / 2.0f, Explosion.BlockInteraction.DESTROY);
            this.discard();
        }
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected boolean shouldBurn() {
        return false;
    }


}
