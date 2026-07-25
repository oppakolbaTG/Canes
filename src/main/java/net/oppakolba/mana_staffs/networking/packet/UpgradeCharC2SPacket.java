package net.oppakolba.mana_staffs.networking.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import net.oppakolba.mana_staffs.item.misc.StaffsItem;
import net.oppakolba.mana_staffs.networking.StaffUpgradeRequirements;

import java.util.function.Supplier;

public class UpgradeCharC2SPacket {
    private final String code;
    private final int value;

    public UpgradeCharC2SPacket(String characteristic, int value){
        this.code = characteristic;
        this.value = value;
    }

    public UpgradeCharC2SPacket(FriendlyByteBuf buf) {
        this.code = buf.readUtf();
        this.value = buf.readInt();
    }

    public void toByte(FriendlyByteBuf buf) {
        buf.writeUtf(code);
        buf.writeInt(value);
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context ctx = supplier.get();
        ctx.enqueueWork(() -> {
            ServerPlayer serverPlayer = ctx.getSender();
            if (serverPlayer == null) return;
            ItemStack itemStack = serverPlayer.getMainHandItem();
            String aCode = StaffUpgradeRequirements.resolveStaffCode(itemStack.getItem());
            if(aCode == null) return;
            int level = StaffUpgradeRequirements.primaryLevel(aCode, itemStack);
            if (level >= StaffUpgradeRequirements.MAX_LEVEL) return;
            if (!StaffUpgradeRequirements.hasRequiredItem(serverPlayer, aCode, level)) return;
            StaffUpgradeRequirements.removeRequiredItems(serverPlayer, aCode, level);
            System.out.println("запрос был отправлен");
            applyChar(itemStack, code);
            serverPlayer.inventoryMenu.broadcastChanges();
        });
        ctx.setPacketHandled(true);
    }

    private void applyChar(ItemStack stack, String code ){
        switch (code){
            case "fc", "bc" -> StaffsItem.addPower(stack, value);
            case "hc" -> {
                StaffsItem.addRadius(stack, value);
                StaffsItem.addHeal(stack, value);
            }
            case "roc" -> {
                StaffsItem.addPower(stack, value);
                StaffsItem.addAmt(stack, value);
            }
            case "lc" -> StaffsItem.addAmt(stack, value);
        }
    }
}
