package net.oppakolba.oppamod.item.Custom;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.oppakolba.oppamod.entity.ModEntities;
import net.oppakolba.oppamod.entity.custom.MagicChargeEntity;
import net.oppakolba.oppamod.mana.PlayerManaProvider;

public class Majicwand extends Item {
    public Majicwand(Properties properties){
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(net.minecraft.world.level.Level level, Player player, InteractionHand hand) {
        if(!level.isClientSide){
            player.getCapability(PlayerManaProvider.PLAYER_MANA).ifPresent(mana -> {
                if(mana.getMana() > 10){
                    for(int i = 0; i < 11; i++) {
                        mana.subMana(1);
                    }
                }
            });
            MagicChargeEntity chargeEntity = new MagicChargeEntity(ModEntities.MAGIC_CHARGE.get(), level);
            Vec3 look = player.getLookAngle();
            chargeEntity.setPos(player.getX(),player.getY() - 0.1,player.getZ());
            chargeEntity.setDeltaMovement(look.scale(1.5));
            level.addFreshEntity(chargeEntity);
        }
        return InteractionResultHolder.success(player.getItemInHand(hand));
    }
}
