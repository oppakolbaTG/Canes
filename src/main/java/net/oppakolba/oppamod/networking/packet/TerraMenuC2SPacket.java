package net.oppakolba.oppamod.networking.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import net.oppakolba.oppamod.mana.PlayerManaProvider;
import net.oppakolba.oppamod.networking.ModMessage;

import java.util.function.Supplier;

public class TerraMenuC2SPacket {
    private final int mana;

    public TerraMenuC2SPacket(int mana){this.mana = mana;}
    public TerraMenuC2SPacket(FriendlyByteBuf buf){this.mana = buf.readInt();}
    public void toByte(FriendlyByteBuf buf){buf.writeInt(mana);}

    public boolean handle(Supplier<NetworkEvent.Context> supplier){
        NetworkEvent.Context context = supplier.get();
        ServerPlayer player = context.getSender();
        if(player == null){
            System.out.println("ERROREE");
            return false;

        }
        context.enqueueWork(() -> {
                player.getCapability(PlayerManaProvider.PLAYER_MANA).ifPresent(mana ->{
                    if(mana.getMAX_MANA() < 100) {
                        mana.updateMana(20);
                        ModMessage.sendToPlayer(new TerraMenuS2CPacket(mana.getMAX_MANA()), player);
                    }
                    else {
                        ModMessage.sendToPlayer(new TerraMenuS2CPacket(mana.getMAX_MANA()), player);
                    }
                });
        });
        context.setPacketHandled(true);
        return true;
    }

}
