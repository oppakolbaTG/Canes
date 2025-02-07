package net.oppakolba.oppamod.networking.packet;


import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import net.oppakolba.oppamod.client.ClientManaData;

import java.util.function.Supplier;

public class ManaDataSyncC2SPacket {
    private final int mana;


    public ManaDataSyncC2SPacket(int mana){
        this.mana = mana;
    }
    public ManaDataSyncC2SPacket(FriendlyByteBuf buf){
        this.mana = buf.readInt();
    }

    public void toByte(FriendlyByteBuf buf){
        buf.writeInt(mana);
    }


    public boolean handle(Supplier<NetworkEvent.Context> supplier){
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // Все что здесь происходит на клиенте
            ClientManaData.set(mana);
    });
        return true;

    }

}
