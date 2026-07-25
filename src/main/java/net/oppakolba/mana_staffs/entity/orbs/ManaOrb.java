package net.oppakolba.mana_staffs.entity.orbs;

import lombok.Getter;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import net.oppakolba.mana_staffs.init.ModEntities;
import net.oppakolba.mana_staffs.item.misc.StaffsItem;
import net.oppakolba.mana_staffs.networking.ModMessage;
import net.oppakolba.mana_staffs.networking.packet.ManaDataSyncPacket;
import org.jetbrains.annotations.NotNull;

import java.util.Random;


//Присоединение к игроку(мб звук?)

public class ManaOrb extends Entity {
    private static final int LIFETIME = 6000;
    private static final int ENTITY_SCAN_PERIOD = 20; //?
    private static final int MAX_FOLLOW_DIST = 8;
    private int lifeTime;
    @Getter
    Random random = new Random();
    public int value = random.nextInt(3, 6);
    private Player followingPlayer;
    private final int POP_STOP_COUNT = 20;

    public ManaOrb(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public void tick() {
        super.tick();
        this.xo = this.getX();
        this.yo = this.getY();
        this.zo = this.getZ();
        ++this.lifeTime;
        if(lifeTime > POP_STOP_COUNT){
            if (this.tickCount % ENTITY_SCAN_PERIOD == 1) {
                this.scanForEntities();
            }

            if (this.followingPlayer != null && (this.followingPlayer.isSpectator() || this.followingPlayer.isDeadOrDying())) {
                this.followingPlayer = null;
            }

            if (this.followingPlayer != null && hasLineOfSight(this.followingPlayer)) {
                Vec3 vec3 = new Vec3(this.followingPlayer.getX() - this.getX(), this.followingPlayer.getY() + (double) this.followingPlayer.getEyeHeight() / (double) 2.0F - this.getY(), this.followingPlayer.getZ() - this.getZ());
                double dist = vec3.lengthSqr();
                if (dist < (double) 64.0F) {
                    double factor = 1.0F - Math.sqrt(dist) / (double) MAX_FOLLOW_DIST;
                    this.setDeltaMovement(this.getDeltaMovement().add(vec3.normalize().scale(factor * factor * 0.1D)));
                }
            }
            if (!level.isClientSide && followingPlayer instanceof ServerPlayer serverPlayer) {
                if (this.distanceToSqr(serverPlayer) < 0.31D) {
                    for (int i = 0; i < followingPlayer.getInventory().getContainerSize(); i++) {
                        ItemStack stack = followingPlayer.getInventory().getItem(i);
                        if (!stack.isEmpty() && stack.getItem() instanceof StaffsItem manastaffsItem) {
                            if (followingPlayer.getItemInHand(InteractionHand.MAIN_HAND).getItem().equals(manastaffsItem)) {
                                updateManaForItem(serverPlayer, stack, i);
                                level.playSound(null, this.getX(),this.getY(),this.getZ(), SoundEvents.NOTE_BLOCK_CHIME, SoundSource.PLAYERS, 0.5f, 1.5f);
                            } else if (!followingPlayer.getItemInHand(InteractionHand.MAIN_HAND).getItem().equals(manastaffsItem)) {
                                updateManaForItem(serverPlayer, stack, i);
                                level.playSound(null, this.getX(),this.getY(),this.getZ(), SoundEvents.NOTE_BLOCK_CHIME, SoundSource.PLAYERS, 0.5f, 1.5f);
                            }
                        }
                    }

                    discard();
                }
            }

        }


        if (this.isEyeInFluid(FluidTags.WATER)) {
            this.setUnderwaterMovement();
        } else if (!this.isNoGravity()) {
            this.setDeltaMovement(this.getDeltaMovement().add( 0.0F, -0.03,  0.0F));
        }

        if (this.level.getFluidState(this.blockPosition()).is(FluidTags.LAVA)) {
            this.setDeltaMovement( ((this.random.nextFloat() - this.random.nextFloat()) * 0.2F),  0.2F,  ((this.random.nextFloat() - this.random.nextFloat()) * 0.2F));
        }

        if (!this.level.noCollision(this.getBoundingBox())) {
            this.moveTowardsClosestSpace(this.getX(), (this.getBoundingBox().minY + this.getBoundingBox().maxY) / (double) 2.0F, this.getZ());
        }


        this.move(MoverType.SELF, this.getDeltaMovement());
        float f = 0.98F;
        if (this.onGround) {
            BlockPos pos = new BlockPos(this.getX(), this.getY() - (double) 1.0F, this.getZ());
            f = this.level.getBlockState(pos).getFriction(this.level, pos, this) * 0.98F;
        }

        this.setDeltaMovement(this.getDeltaMovement().multiply((double) f, 0.98, (double) f));
        if (this.onGround) {
            this.setDeltaMovement(this.getDeltaMovement().multiply((double) 1.0F, -0.9, (double) 1.0F));
        }


        if (this.lifeTime >= LIFETIME) {
            this.discard();
        }

    }

    private boolean hasLineOfSight(Player player) {
        Vec3 from = this.position().add(0, this.getBbHeight() / 2.0, 0);
        Vec3 to = player.getEyePosition();
        BlockHitResult hit = this.level.clip(new ClipContext(
                from, to,
                ClipContext.Block.COLLIDER,
                ClipContext.Fluid.NONE,
                this
        ));
        return hit.getType() == HitResult.Type.MISS;
    }

    private void scanForEntities() {
        if (this.followingPlayer == null || this.followingPlayer.distanceToSqr(this) > (double) 64.0F) {
            this.followingPlayer = this.level.getNearestPlayer(this, (double) MAX_FOLLOW_DIST);
        }
    }

    private void setUnderwaterMovement() {
        Vec3 vec3 = this.getDeltaMovement();
        this.setDeltaMovement(vec3.x * (double) 0.99F, Math.min(vec3.y + (double) 5.0E-4F, (double) 0.06F), vec3.z * (double) 0.99F);
    }


    @Override
    protected void doWaterSplashEffect() {
    }



    //Срабатывает когда умирает моб или специальный предмет
    //Orb разлетается и только после срабатывает обычная логика движения к игроку
    public static ManaOrb spawnOrbWithPop(Level level, double x, double y, double z){
        ManaOrb manaOrb = new ManaOrb(ModEntities.MANA_ORB.get(), level);
        manaOrb.setPos(x,y,z);
        manaOrb.yRotO = (float) (manaOrb.random.nextDouble() * 360.0d);
        manaOrb.setDeltaMovement((manaOrb.random.nextDouble() * 0.2d - 0.1d) * 2.0d, manaOrb.random.nextDouble() * 0.2D * 2.0D + 0.1D,
                (manaOrb.random.nextDouble() * 0.2D - 0.1D) * 2.0D);
        return manaOrb;
    }




    private void updateManaForItem(ServerPlayer player, ItemStack stack, int slotId) {
        int currentMana = StaffsItem.getMana(stack);
        int maxMana = StaffsItem.getMaxMana(stack);
        if (currentMana < maxMana) {
            int newMana = Math.min(currentMana + this.value, maxMana);
            StaffsItem.setMana(stack, newMana);

            if (currentMana != newMana) {
                ModMessage.sendToClient(new ManaDataSyncPacket(newMana, slotId), player);
            }
        }
    }

    @Override
    protected void defineSynchedData() {

    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compoundTag) {
        if(compoundTag.contains("value")){
           this.value = compoundTag.getInt("value");
        }
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compoundTag) {
        compoundTag.putInt("value", value);
    }

    @Override
    public @NotNull Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
