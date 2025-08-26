//package net.oppakolba.canes.item.canes;
//
//import net.minecraft.core.particles.ParticleTypes;
//import net.minecraft.server.level.ServerPlayer;
//import net.minecraft.world.entity.LivingEntity;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.world.level.Level;
//import net.oppakolba.canes.entity.projectile.DrillEntity;
//import net.oppakolba.canes.init.ModEntities;
//import net.oppakolba.canes.item.misc.CanesItem;
//import net.oppakolba.canes.mana.PlayerManaProvider;
//import net.oppakolba.canes.networking.ModMessage;
//import net.oppakolba.canes.networking.packet.ManaDataSyncS2CPacket;
//
//public class DrillCane extends CanesItem {
//    public DrillCane(Properties pProperties) {
//        super(pProperties);
//    }
//
//
//    @Override
//    public void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity entity, int pTimeCharged) {
//        if(!pLevel.isClientSide && entity instanceof Player player){
//            player.getCapability(PlayerManaProvider.PLAYER_MANA).ifPresent(mana-> {
//                if(mana.getMana() >= 30){
//                    mana.subMana(30);
//                    DrillEntity canesDrill = new DrillEntity(ModEntities.CANE_DRILL.get(),player, pLevel, player.getLookAngle().x * 0.5, player.getLookAngle().y * 0.5, player.getLookAngle().z * 0.5);
//                    pLevel.addFreshEntity(canesDrill);
//                    if(player instanceof ServerPlayer serverPlayer){
//                        ModMessage.sendToPlayer(new ManaDataSyncS2CPacket(mana.getMana()), serverPlayer);
//                    }
//                }
//            });
//        }
//    }
//
//
//    @Override
//    public void onUseTick(Level level, LivingEntity entity, ItemStack stack, int count) {
//        if (level.isClientSide) {
//            if (entity instanceof Player player) {
//                randomSpawnParticles(ParticleTypes.CRIT, level, player, 0, 1, 0);
//            }
//        }
//    }
//}
