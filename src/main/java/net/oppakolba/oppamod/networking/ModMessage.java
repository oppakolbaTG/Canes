package net.oppakolba.oppamod.networking;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import net.oppakolba.oppamod.Oppamod;
import net.oppakolba.oppamod.networking.packet.*;

public class  ModMessage {
    private static SimpleChannel INSTANCE;

    private static int packetId = 0;
    private static int id(){
        return packetId++;
    }

    public static void register() {
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(Oppamod.MOD_ID, "messages"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        INSTANCE = net;

        net.messageBuilder(ManaUseC2SWorking.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(ManaUseC2SWorking::new)
                .encoder(ManaUseC2SWorking::toByte)
                .consumerMainThread(ManaUseC2SWorking::handle)
                .add();

        net.messageBuilder(ManaDataSyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(ManaDataSyncS2CPacket::new)
                .encoder(ManaDataSyncS2CPacket::toByte)
                .consumerMainThread(ManaDataSyncS2CPacket::handle)
                .add();

        net.messageBuilder(TerraMenuC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(TerraMenuC2SPacket::new)
                .encoder(TerraMenuC2SPacket::toByte)
                .consumerMainThread(TerraMenuC2SPacket::handle).add();

        net.messageBuilder(TerraMenuS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(TerraMenuS2CPacket::new)
                .encoder(TerraMenuS2CPacket::toByte)
                .consumerMainThread(TerraMenuS2CPacket::handle).add();

        net.messageBuilder(TerraMenuReloadC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(TerraMenuReloadC2SPacket::new)
                .encoder(TerraMenuReloadC2SPacket::toByte)
                .consumerMainThread(TerraMenuReloadC2SPacket::handle).add();
    }

    public static <MSG> void sendToServer(MSG message){
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player){
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }
}
