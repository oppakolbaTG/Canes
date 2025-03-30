package net.oppakolba.oppamod.entity.item;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;
import net.oppakolba.oppamod.mana.PlayerManaProvider;
import net.oppakolba.oppamod.networking.ModMessage;
import net.oppakolba.oppamod.networking.packet.ManaDataSyncS2CPacket;
import org.jetbrains.annotations.NotNull;


public class ManaOrb extends Entity {
    double distance = 10;
    public ManaOrb(EntityType<? extends Entity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }


    @Override
    public void tick() {
        super.tick();

        Player player = this.level.getNearestPlayer(this.getX(), this.getY(), this.getZ(), distance, entity -> {
            Player pEntity = (Player) entity;
            return !pEntity.isSpectator();
        });
        if(player != null) {
            player.getCapability(PlayerManaProvider.PLAYER_MANA).ifPresent(mana -> {
                if (mana.getMana() != mana.getMAX_MANA()) {
                    if (!this.level.noCollision(this.getBoundingBox()))
                        this.moveTowardsClosestSpace(this.getX(), (this.getBoundingBox().minY + this.getBoundingBox().maxY) / 2.0D, this.getZ());


                    this.setDeltaMovement(this.getDeltaMovement().add(
                            player.position().add(0F, player.getBbHeight() / 2F, 0F)
                                    .subtract(this.position())
                                    .normalize()
                                    .scale((distance - this.position().distanceTo(player.position())) / (distance * 10))));
                    float friction = 0.98F;
                    if (this.isOnGround()) {
                        BlockPos pos = getBlockPosBelowThatAffectsMyMovement();

                        friction = this.level.getBlockState(pos).getFriction(this.level, pos, this) * 0.98F;
                    }

                    this.setDeltaMovement(this.getDeltaMovement().multiply(friction, 0.98D, friction));

                    if (this.isOnGround())
                        this.setDeltaMovement(this.getDeltaMovement().multiply(1.0D, -0.9D, 1.0D));

                    this.move(MoverType.SELF, this.getDeltaMovement());
                }
            });
        }
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

    @Override
    public void playerTouch(@NotNull Player player) {
        player.getCapability(PlayerManaProvider.PLAYER_MANA).ifPresent(mana -> {
            mana.addMana(3);
            this.level.playSound(null, this.blockPosition(), SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.MASTER, 0.5F, 1.25F + this.level.getRandom().nextFloat() * 0.75F);
            System.out.println(mana.getMana());
            if(player instanceof ServerPlayer serverPlayer) {
                ModMessage.sendToPlayer(new ManaDataSyncS2CPacket(mana.getMana()), serverPlayer);
            }
            this.discard();
        });
    }


}
