package net.oppakolba.canes.networking.packet;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import net.oppakolba.canes.item.misc.CanesItem;


import java.util.function.Supplier;

public class ManaDataSyncPacket {
private final int mana;
private final int slotId;
private ServerPlayer serverPlayer;



    public ManaDataSyncPacket(int mana, int slotId) {
        this.mana = mana;
        this.slotId = slotId;
    }

    public ManaDataSyncPacket(FriendlyByteBuf buf) {
        mana = buf.readInt();
        slotId = buf.readInt();
    }

    public void toByte(FriendlyByteBuf buf) {
        buf.writeInt(mana);
        buf.writeInt(slotId);
    }


    public boolean handle(Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(() -> {
            Player player = Minecraft.getInstance().player;
            if (player != null && slotId >= 0 && slotId < player.getInventory().getContainerSize()) {
                ItemStack stack = player.getInventory().getItem(slotId);
                if (stack.getItem() instanceof CanesItem) {
                    CanesItem.setMana(stack, mana);
                }
            }
        });

        ctx.get().setPacketHandled(true);
        return true;
    }
}
