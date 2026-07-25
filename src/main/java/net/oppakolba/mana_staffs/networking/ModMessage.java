package net.oppakolba.mana_staffs.networking;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import net.oppakolba.mana_staffs.ManaStaffs;
import net.oppakolba.mana_staffs.networking.packet.*;


public class  ModMessage {
    private static SimpleChannel INSTANCE;

    private static int packetId = 0;
    private static int id(){
        return packetId++;
    }

    public static void register() {
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(ManaStaffs.MOD_ID, "messages"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        INSTANCE = net;


        net.messageBuilder(UpgradeCharC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(UpgradeCharC2SPacket::new)
                .encoder(UpgradeCharC2SPacket::toByte)
                .consumerMainThread(UpgradeCharC2SPacket::handle).add();

        net.messageBuilder(UpgradeS2CPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(UpgradeS2CPacket::new)
                .encoder(UpgradeS2CPacket::toByte)
                .consumerMainThread(UpgradeS2CPacket::handle).add();

        net.messageBuilder(ManaDataSyncPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(ManaDataSyncPacket::new)
                .encoder(ManaDataSyncPacket::toByte)
                .consumerMainThread(ManaDataSyncPacket::handle)
                .add();



    }

    public static <MSG> void sendToServer(MSG message){
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToClient(Object packet, ServerPlayer player){
        INSTANCE.sendTo(packet, player.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
    }
}
