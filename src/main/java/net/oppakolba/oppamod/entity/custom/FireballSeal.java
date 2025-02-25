package net.oppakolba.oppamod.entity.custom;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;

import java.util.UUID;

public class FireballSeal extends Entity {
    private int duration = 40;
    private UUID playerUID;

    public FireballSeal(EntityType<? extends FireballSeal> entityType, Level level) {
        super(entityType, level);
    }

    public FireballSeal(EntityType<? extends FireballSeal> entityType, Level level, Player player) {
        super(entityType, level);
        this.playerUID = player.getUUID();
        this.setPos(player.getX(), player.getY() + 3, player.getZ());
    }

    @Override
    protected void defineSynchedData() {
    }

    @Override
    public void tick() {
        super.tick();
        if (this.duration-- <= 0) {
            this.discard();
            return;
        }

        if (playerUID != null && !this.level.isClientSide) {
            Player player = level.getPlayerByUUID(playerUID);
            if (player != null) {
                this.setPos(player.getX(), player.getY() + 3, player.getZ());
            }
        }
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        this.duration = tag.getInt("Duration");
        if (tag.hasUUID("PlayerUUID")) {
            this.playerUID = tag.getUUID("PlayerUUID");
        }
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        tag.putInt("Duration", this.duration);
        if (this.playerUID != null) {
            tag.putUUID("PlayerUUID", this.playerUID);
        }
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
