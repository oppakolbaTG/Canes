package net.oppakolba.canes.entity.orbs;

import lombok.Getter;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerXpEvent;
import net.minecraftforge.network.NetworkHooks;
import net.oppakolba.canes.item.misc.CanesItem;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

//Движение + движение к игроку
//Присоединение к игроку(мб звук?)
//
//
public class ManaOrb extends Entity {
    private static final int LIFETIME = 6000;
    private static final int ENTITY_SCAN_PERIOD = 20; //?
    private static final int MAX_FOLLOW_DIST = 8;
    private int lifeTime;
    @Getter
    public int value;
    private Player followingPlayer;

    public ManaOrb(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public void tick() {
        super.tick();
        this.xo = this.getX();
        this.yo = this.getY();
        this.zo = this.getZ();
        if (this.isEyeInFluid(FluidTags.WATER)) {
            this.setUnderwaterMovement();
        } else if (!this.isNoGravity()) {
            this.setDeltaMovement(this.getDeltaMovement().add((double) 0.0F, -0.03, (double) 0.0F));
        }

        if (this.level.getFluidState(this.blockPosition()).is(FluidTags.LAVA)) {
            this.setDeltaMovement((double) ((this.random.nextFloat() - this.random.nextFloat()) * 0.2F), (double) 0.2F, (double) ((this.random.nextFloat() - this.random.nextFloat()) * 0.2F));
        }

        if (!this.level.noCollision(this.getBoundingBox())) {
            this.moveTowardsClosestSpace(this.getX(), (this.getBoundingBox().minY + this.getBoundingBox().maxY) / (double) 2.0F, this.getZ());
        }

        if (this.tickCount % 20 == 1) {
            this.scanForEntities();
        }

        if (this.followingPlayer != null && (this.followingPlayer.isSpectator() || this.followingPlayer.isDeadOrDying())) {
            this.followingPlayer = null;
        }

        if (this.followingPlayer != null && hasLineOfSight(this.followingPlayer)) {
            Vec3 vec3 = new Vec3(this.followingPlayer.getX() - this.getX(), this.followingPlayer.getY() + (double) this.followingPlayer.getEyeHeight() / (double) 2.0F - this.getY(), this.followingPlayer.getZ() - this.getZ());
            double dist = vec3.lengthSqr();
            if (dist < (double) 64.0F) {
                double factor = 1.0F - Math.sqrt(dist) / (double) 8.0F;
                this.setDeltaMovement(this.getDeltaMovement().add(vec3.normalize().scale(factor * factor * 0.1D)));
            }
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

        ++this.lifeTime;
        if (this.lifeTime >= 6000) {
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
            this.followingPlayer = this.level.getNearestPlayer(this, (double) 8.0F);
        }
    }

//    public static void award(ServerLevel pLevel, Vec3 pPos, int pAmount) {
//        while(pAmount > 0) {
//            int i = getExperienceValue(pAmount);
//            pAmount -= i;
//            if (!tryMergeToExisting(pLevel, pPos, i)) {
//                pLevel.addFreshEntity(new ExperienceOrb(pLevel, pPos.x(), pPos.y(), pPos.z(), i));
//            }
//        }
//
//    }


    private void setUnderwaterMovement() {
        Vec3 vec3 = this.getDeltaMovement();
        this.setDeltaMovement(vec3.x * (double) 0.99F, Math.min(vec3.y + (double) 5.0E-4F, (double) 0.06F), vec3.z * (double) 0.99F);
    }

    protected void doWaterSplashEffect() {
    }


/**
Касание маны запускает данную функцию
    **/
    @Override
    public void playerTouch(@NotNull Player player) {
        if (!this.level.isClientSide) {
            this.discard();
            for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
                ItemStack stack = player.getInventory().getItem(i);
                if (!stack.isEmpty() && stack.getItem() instanceof CanesItem canesItem) {

                }
            }


        }
    }

    /**
     * А это надо переделать так чтобы мана закидывалась к посох
     * @return
     */
//    private int repairPlayerCanes(Player pPlayer, int pRepairAmount) {
//        if (entry != null) {
//            ItemStack itemstack = (ItemStack)entry.getValue();
//            int i = Math.min((int)((float)this.value * itemstack.getXpRepairRatio()), itemstack.getDamageValue());
//            itemstack.setDamageValue(itemstack.getDamageValue() - i);
//            int j = pRepairAmount - this.durabilityToMana(i);
//            return j > 0 ? this.repairPlayerItems(pPlayer, j) : 0;
//        } else {
//            return pRepairAmount;
//        }
//    }


    public void checkPlayerItems(){

    }

    //Срабатывает когда умирает моб или специальный предмет
    //Orb разлетается и только после срабатывает обычная логика движения к игроку
    public void spawnOrb(){

    }


    private int durabilityToMana(int pDurability) {
        return pDurability / 2;
    }

    private int manaToDurability(int pXp) {
        return pXp * 2;
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
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
