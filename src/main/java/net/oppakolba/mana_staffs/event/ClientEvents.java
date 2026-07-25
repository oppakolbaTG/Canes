package net.oppakolba.mana_staffs.event;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.oppakolba.mana_staffs.ManaStaffs;
import net.oppakolba.mana_staffs.client.renderer.item.IStaffsRenderer;
import net.oppakolba.mana_staffs.init.ModItems;
import net.oppakolba.mana_staffs.item.misc.StaffsItem;
import net.oppakolba.mana_staffs.screen.StaffsMenuScreen;
import net.oppakolba.mana_staffs.util.KeyBinding;

public class ClientEvents {
    @Mod.EventBusSubscriber(modid = ManaStaffs.MOD_ID, value = Dist.CLIENT)
    public static class ClientForgeEvents {
        private static final String POWER = "power";
        private static final String HEAL = "heal";
        private static final String RADIUS = "radius";
        private static final String AMT = "amt";

        @SubscribeEvent
        public static void onKeyInput(InputEvent.Key event) {
            Minecraft minecraft = Minecraft.getInstance();
            Player player = minecraft.player;
            if(player != null) {
                if (KeyBinding.OPENING_GUI_KEY.consumeClick() && player.getMainHandItem().getItem() instanceof StaffsItem) {
                    initialize(player.getItemInHand(InteractionHand.MAIN_HAND));
                    Minecraft.getInstance().setScreen(new StaffsMenuScreen(Component.empty()));
                }
            }
        }



        @SubscribeEvent
        public static void registerItemProperties(FMLClientSetupEvent event) {
            IStaffsRenderer.register(ModItems.FIREBALL_STAFF.get());
        }


        private static void initialize(ItemStack stack) {
            CompoundTag tag = stack.getOrCreateTag();
            if (!tag.contains(POWER)) {
                tag.putInt(POWER, 1);
            }
            if (!tag.contains(RADIUS)) {
                tag.putInt(RADIUS, 1);
            }
            if (!tag.contains(HEAL)) {
                tag.putInt(HEAL, 1);
            }
            if (!tag.contains(AMT)) {
                tag.putInt(AMT, 1);
            }

        }

    }
    @Mod.EventBusSubscriber(modid = ManaStaffs.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientMobBusEvents {

        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event) {
            event.register(KeyBinding.MANA_USING_KEY);
            event.register(KeyBinding.OPENING_GUI_KEY);
        }
    }


}


