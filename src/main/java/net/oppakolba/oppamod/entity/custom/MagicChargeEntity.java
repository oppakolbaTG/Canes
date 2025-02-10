package net.oppakolba.oppamod.entity.custom;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;

public class MagicChargeEntity extends ThrowableProjectile {
    public MagicChargeEntity(EntityType<? extends MagicChargeEntity> entityType, Level world){
        super(entityType, world);
    }


    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        Entity entity = result.getEntity();
        entity.hurt(DamageSource.MAGIC, 3);
    }

    @Override
    protected void defineSynchedData() {

    }
}
