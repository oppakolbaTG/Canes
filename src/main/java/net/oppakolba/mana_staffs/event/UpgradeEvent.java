package net.oppakolba.mana_staffs.event;


import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.oppakolba.mana_staffs.ManaStaffs;
import net.oppakolba.mana_staffs.init.ModItems;
import net.oppakolba.mana_staffs.item.manastaffsitems.ManaCrystal;
import net.oppakolba.mana_staffs.item.misc.StaffsItem;
import net.oppakolba.mana_staffs.networking.ModMessage;
import net.oppakolba.mana_staffs.networking.packet.ManaDataSyncPacket;
import net.oppakolba.mana_staffs.networking.packet.MaxManaDataSyncPacket;

@Mod.EventBusSubscriber(modid = ManaStaffs.MOD_ID,  bus = Mod.EventBusSubscriber.Bus.FORGE)
public class UpgradeEvent {

    @SubscribeEvent
    public static void onAnvilUpdate(AnvilUpdateEvent event){
        ItemStack left = event.getLeft();
        ItemStack right = event.getRight();

        if(!(left.getItem() instanceof StaffsItem)) return;
        if(!right.is(ModItems.GEALACH_SEAL.get())) return;
        if(StaffsItem.getMaxMana(left) >= 38) return;

        ItemStack res = left.copy();
        StaffsItem.setMaxMana(res, StaffsItem.getMaxMana(res) + 6);

        if (event.getPlayer() instanceof ServerPlayer serverPlayer) {
            ModMessage.sendToClient(new MaxManaDataSyncPacket(StaffsItem.getMaxMana(res), res), serverPlayer);

        }
        event.setOutput(res);
        event.setCost(6);
        event.setMaterialCost(1);
    }

}
