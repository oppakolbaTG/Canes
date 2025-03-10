package net.oppakolba.oppamod.event;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.oppakolba.oppamod.Oppamod;
import net.oppakolba.oppamod.client.ManaHudOverlay;
import net.oppakolba.oppamod.networking.ModMessage;
import net.oppakolba.oppamod.networking.packet.ManaUseC2SWorking;
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
