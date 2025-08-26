package net.oppakolba.canes.entity.projectile;

import lombok.extern.log4j.Log4j2;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import net.oppakolba.canes.client.renderer.particles.BeamParticle;
import net.oppakolba.canes.item.canes.BeamCane;

import javax.annotation.Nullable;


@Log4j2
public class BeamEntity extends Entity {
    private int lifeTime = 0;
    private final float length;
    private final Vec3 direction;
    private LivingEntity owner;



    public BeamEntity(EntityType<?> entityType, Level level) {
        super(entityType, level);
        this.length = 20.0f;
        this.direction = Vec3.ZERO;
        this.setBoundingBox(new AABB(-0.5, -0.5, -0.5, 0.5, 0.5, 0.5));
    }

    public BeamEntity(EntityType<?> pEntityType, Level pLevel, Vec3 lookVec, float length, Player player, Vec3 pLookPos) {
        super(pEntityType, pLevel);
        this.setPos(player.getX(), player.getY() + 0.6f, player.getZ() - 0.7);
        lookAt(EntityAnchorArgument.Anchor.EYES, pLookPos);
        this.direction = lookVec.normalize();
        this.length = length;
        this.lifeTime = 0;
        this.owner = player;
    }

    @Override
    public void tick() {
        super.tick();
        this.lifeTime++;
        this.setYRot(0);
        this.setXRot(0);
        this.yRotO = 0;
        this.xRotO = 0;
        Player player = this.level.getNearestPlayer(this, 50);

        if (this.shouldBeRemoved()) {
            this.discard();
            return;
        }

        if (this.lifeTime >= 1000) {
            this.discard();
        }
        else {
            if (player != null) {
                double newX = this.position().x + (player.getX() - this.position().x) * 0.2;
                double newY = this.position().y + (player.getY() + 0.6 - this.position().y) * 0.2;
                double newZ = this.position().z + (player.getZ() - this.position().z) * 0.2;
                setPos(newX, newY,  newZ);

                Vec3 lookDirection = player.getLookAngle();
                Vec3 playerEyePos = player.getEyePosition();
                Vec3 pLookPos = playerEyePos.add(lookDirection.scale(10.0));
                lookAt(EntityAnchorArgument.Anchor.EYES, pLookPos);
            }
        }
    }

    @Nullable
    public LivingEntity getOwner() {
        return this.owner;
    }

    private void cleanUpBeamId() {
        if (this.owner instanceof Player player) {
            // Проверяем все слоты инвентаря
            for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
                ItemStack stack = player.getInventory().getItem(i);
                if (stack.getItem() instanceof net.oppakolba.canes.item.canes.BeamCane) {
                    CompoundTag tag = stack.getOrCreateTag();
                    if (tag.contains("BeamId") && tag.getInt("BeamId") == this.getId()) {
                        tag.remove("BeamId");
                    }
                }
            }
        }
    }


    private boolean shouldBeRemoved() {
        Player player = this.level.getNearestPlayer(this, 50);
        if (player == null || !player.isAlive()) {
            this.cleanUpBeamId();
            System.out.println("4");
            return true;
        }

        ItemStack usingItem = player.getUseItem();
        if (!(usingItem.getItem() instanceof net.oppakolba.canes.item.canes.BeamCane)) {
            this.cleanUpBeamId();
            System.out.println("2");
            return true;
        }

        if (!player.isUsingItem()) {
            this.cleanUpBeamId();
            System.out.println("1");
            return true;
        }

        return false;
    }

    @Override
    public void onRemovedFromWorld() {
        this.cleanUpBeamId(); // Очищаем при удалении
        super.onRemovedFromWorld();
    }

    @Override
    protected void defineSynchedData() {}

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {
        this.lifeTime = pCompound.getInt("lifeTime");
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {
        pCompound.putInt("lifeTime", this.lifeTime);
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }


}