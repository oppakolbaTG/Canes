package net.oppakolba.oppamod.networking.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import net.oppakolba.oppamod.mana.PlayerManaProvider;
import net.oppakolba.oppamod.networking.ModMessage;

import java.util.function.Supplier;

public class TerraMenuReloadC2SPacket {
    public TerraMenuReloadC2SPacket(){

    }

    public TerraMenuReloadC2SPacket(FriendlyByteBuf buf) {

    }

    public void toByte(FriendlyByteBuf buf) {

    }


    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            player.getCapability(PlayerManaProvider.PLAYER_MANA).ifPresent(mana ->{
                mana.reloadMana();
                ModMessage.sendToPlayer(new TerraMenuS2CPacket(mana.getMAX_MANA()), player);
            });
        });
        context.setPacketHandled(true);
        return true;
    }
}
