package net.oppakolba.oppamod.entity.item;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;

import javax.swing.text.Position;

public class ManaOrb extends Entity {
    double distance = 10;
    public ManaOrb(EntityType<? extends Entity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }


    @Override
    public void tick() {
        if(!this.isNoGravity()){
            this.setDeltaMovement(this.getDeltaMovement().add(0.0f, -0.2f, 0.0f));
        }
        if(this.level.noCollision(this.getBoundingBox())){
            this.moveTowardsClosestSpace(this.getX(), this.getY(), this.getZ());
        }
        super.tick();
        Player player = this.level.getNearestPlayer(this.getX(), this.getY(), this.getZ(), distance, entity -> {
           Player pEntity = (Player) entity;
           return !pEntity.isSpectator();
        });

        if(player != null){
            this.setDeltaMovement(this.getDeltaMovement().add(
                player.position().add(0F, player.getBbHeight() / 2F, 0F)
                        .subtract(this.position())
                        .normalize()
                        .scale((distance - this.position().distanceTo(player.position())) / (distance * 10))
        ));
        }
        this.move(MoverType.SELF, this.getDeltaMovement());
    }
    @Override
    protected void defineSynchedData() {

    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {

    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

//    @Override
//    public void playerTouch(@NotNull Player player) {
//        player.getCapability(PlayerManaProvider.PLAYER_MANA).ifPresent(mana -> {
//            mana.addMana(2);
//            System.out.println(mana.getMana());
//            if(player instanceof ServerPlayer serverPlayer) {
//                ModMessage.sendToPlayer(new ManaDataSyncS2CPacket(mana.getMana()), serverPlayer);
//            }
//            this.discard();
//        });
//    }


}
