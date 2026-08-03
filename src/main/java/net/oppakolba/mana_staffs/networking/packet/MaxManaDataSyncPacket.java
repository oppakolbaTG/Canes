package net.oppakolba.mana_staffs.networking.packet;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import net.oppakolba.mana_staffs.item.misc.StaffsItem;

import java.util.function.Supplier;

public class MaxManaDataSyncPacket {
private final int maxMana;
private final ItemStack stack;




    public MaxManaDataSyncPacket(int maxMana, ItemStack stack) {
        this.maxMana = maxMana;
        this.stack = stack;
    }

    public MaxManaDataSyncPacket(FriendlyByteBuf buf) {
        maxMana = buf.readInt();
        stack = buf.readItem();
    }

    public void toByte(FriendlyByteBuf buf) {
        buf.writeInt(maxMana);
        buf.writeItem(stack);
    }


    public boolean handle(Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(() -> {
            Player player = Minecraft.getInstance().player;
            if (player != null) {
                if (stack.getItem() instanceof StaffsItem) {
                    StaffsItem.setMaxMana(stack, maxMana);
                }
            }
        });

        ctx.get().setPacketHandled(true);
        return true;
    }
}
