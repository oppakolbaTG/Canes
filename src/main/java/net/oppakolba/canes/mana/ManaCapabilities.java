package net.oppakolba.canes.mana;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraft.world.item.ItemStack;
import net.oppakolba.canes.Canes;
import net.oppakolba.canes.item.misc.CanesItem;



@Mod.EventBusSubscriber(modid = Canes.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ManaCapabilities {
    public static final Capability<ICanesMana> ITEM_MANA = CapabilityManager.get(new CapabilityToken<>() {});

    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.register(ICanesMana.class);
    }

    @SubscribeEvent
    public static void onAttachCapabilities(AttachCapabilitiesEvent<ItemStack> event) {
        ItemStack stack = event.getObject();
        if (stack.getItem() instanceof CanesItem) {
            event.addCapability(new ResourceLocation(Canes.MOD_ID, "item_mana"),
                    new CanesManaProvider(stack));
        }
    }
}