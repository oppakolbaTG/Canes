package net.oppakolba.canes.networking.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import net.oppakolba.canes.item.misc.CanesItem;
import net.oppakolba.canes.networking.CaneUpgradeRequirements;

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
            String aCode = CaneUpgradeRequirements.resolveCaneCode(itemStack.getItem());
            if(aCode == null) return;
            int level = CaneUpgradeRequirements.primaryLevel(aCode, itemStack);
            if (level >= CaneUpgradeRequirements.MAX_LEVEL) return;
            if (!CaneUpgradeRequirements.hasRequiredItem(serverPlayer, aCode, level)) return;
            CaneUpgradeRequirements.removeRequiredItems(serverPlayer, aCode, level);
            System.out.println("запрос был отправлен");
            applyChar(itemStack, code);
            serverPlayer.inventoryMenu.broadcastChanges();
        });
        ctx.setPacketHandled(true);
    }

    private void applyChar(ItemStack stack, String code ){
        switch (code){
            case "fc", "bc" -> CanesItem.addPower(stack, value);
            case "hc" -> {
                CanesItem.addRadius(stack, value);
                CanesItem.addHeal(stack, value);
            }
            case "roc" -> {
                CanesItem.addPower(stack, value);
                CanesItem.addAmt(stack, value);
            }
            case "lc" -> CanesItem.addAmt(stack, value);
        }
    }
}
