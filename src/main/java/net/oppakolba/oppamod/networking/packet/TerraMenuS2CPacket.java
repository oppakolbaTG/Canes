package net.oppakolba.oppamod.networking.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import net.oppakolba.oppamod.client.manahud.ClientManaData;

import java.util.function.Supplier;

public class TerraMenuS2CPacket {
    private final int max_mana;

    public TerraMenuS2CPacket(int mana) {
        this.max_mana = mana;
    }

    public TerraMenuS2CPacket(FriendlyByteBuf buf) {
        this.max_mana = buf.readInt();
    }

    public void toByte (FriendlyByteBuf buf) {
        buf.writeInt(max_mana);
    }


    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            if (context.getDirection().getReceptionSide().isClient()) {
                ClientManaData.setPlayerMaxMana(max_mana);
            }
        });
        return true;
    }
}
