package net.oppakolba.oppamod.event;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.oppakolba.oppamod.Oppamod;
import net.oppakolba.oppamod.client.ManaHudOverlay;
import net.oppakolba.oppamod.mana.PlayerMana;
import net.oppakolba.oppamod.mana.PlayerManaProvider;
import net.oppakolba.oppamod.networking.ModMessage;
import net.oppakolba.oppamod.networking.packet.ManaDataSyncS2CPacket;
import net.oppakolba.oppamod.networking.packet.ManaUseC2SWorking;
import net.oppakolba.oppamod.networking.packet.TerraMenuS2CPacket;
import net.oppakolba.oppamod.screen.TerraMenuScreen;
import net.oppakolba.oppamod.util.KeyBinding;

public class ClientEvents {
    @Mod.EventBusSubscriber(modid = Oppamod.MOD_ID, value = Dist.CLIENT)
    public static class ClientForgeEvents {

        @SubscribeEvent
        public static void onKeyInput(InputEvent.Key event) {
            if (KeyBinding.MANA_USING_KEY.consumeClick()) {
                ModMessage.sendToServer(new ManaUseC2SWorking());
            }
            if (KeyBinding.OPENING_GUI_KEY.consumeClick()) {
                Minecraft.getInstance().setScreen(new TerraMenuScreen(Component.empty()));
            }
        }

        @SubscribeEvent
        public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
            ServerPlayer player = (ServerPlayer) event.getEntity();
            int Max_mana = player.getCapability(PlayerManaProvider.PLAYER_MANA).map(PlayerMana::getMAX_MANA).orElse(20);

            ModMessage.sendToPlayer(new TerraMenuS2CPacket(Max_mana), player);
        }

        @SubscribeEvent
        public static void onPlayerJoinWorld(EntityJoinLevelEvent event) {
            if (!event.getLevel().isClientSide) {
                if (event.getEntity() instanceof ServerPlayer player) {
                    player.getCapability(PlayerManaProvider.PLAYER_MANA).ifPresent(mana -> {
                        ModMessage.sendToPlayer(new ManaDataSyncS2CPacket(mana.getMana()), player);
                    });
                }
            }
        }
    }
    @Mod.EventBusSubscriber(modid = Oppamod.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientMobBusEvents{

        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event){
            event.register(KeyBinding.MANA_USING_KEY);
            event.register(KeyBinding.OPENING_GUI_KEY);
        }
        @SubscribeEvent
        public static void registerGuiOverlays(RegisterGuiOverlaysEvent event){
            event.registerAboveAll("mana", ManaHudOverlay.HUD_MANA);

    }

    }
}
