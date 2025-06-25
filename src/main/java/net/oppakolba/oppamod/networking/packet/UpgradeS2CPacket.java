package net.oppakolba.oppamod.networking.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import java.util.function.Supplier;

public class UpgradeS2CPacket {
    public UpgradeS2CPacket(FriendlyByteBuf buf) { }

    public void toByte(FriendlyByteBuf buf) { }

    public UpgradeS2CPacket() { }

    public static void handle(UpgradeS2CPacket message, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();
        ctx.enqueueWork(() -> {
            // Тут твой клиентский код
            System.out.println("Клиент получил UpgradeS2CPacket!");
            // Тут можно запускать анимации, показывать сообщения и т.д.
        });
        ctx.setPacketHandled(true);
    }
}
