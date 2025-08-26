package net.oppakolba.canes.networking.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import net.oppakolba.canes.init.ModItems;

import java.util.function.Supplier;

public class UpgradeC2SPacket {

    public UpgradeC2SPacket(){

    }

    public UpgradeC2SPacket(FriendlyByteBuf buf) {

    }

    public void toByte(FriendlyByteBuf buf) {

    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context ctx = supplier.get();
        ctx.enqueueWork(() -> {
            ServerPlayer player = ctx.getSender();
            if (player == null) return;
            int required = 3;
            if (player.getInventory().countItem(ModItems.MANA_CRYSTAL.get()) >= required) {
                int toRemove = required;
                for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
                    ItemStack stack = player.getInventory().getItem(i);
                    if (stack.getItem() == ModItems.MANA_CRYSTAL.get()) {
                        int remove = Math.min(stack.getCount(), toRemove);
                        stack.shrink(remove);
                        toRemove -= remove;
                        if (toRemove <= 0) break;
                    }
                }
            }
        });
        ctx.setPacketHandled(true);
    }
}
