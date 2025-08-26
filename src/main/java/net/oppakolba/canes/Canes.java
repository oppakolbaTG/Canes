package net.oppakolba.canes;

import com.mojang.logging.LogUtils;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.oppakolba.canes.init.*;
import net.oppakolba.canes.networking.ModMessage;
import net.oppakolba.canes.screen.AlterioTableScreen;
import net.oppakolba.canes.world.feature.ModConfiguredFeatures;
import net.oppakolba.canes.world.feature.ModPlacedFeatures;
import org.checkerframework.checker.units.qual.C;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Canes.MOD_ID)
public class Canes {
    public static final String MOD_ID = "canes";
    private static final Logger LOGGER = LogUtils.getLogger();
    public Canes()
    {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.register(bus);
        ModBlocks.register(bus);
        ModPainting.register(bus);
        ModConfiguredFeatures.register(bus);
        ModPlacedFeatures.register(bus);
        ModSounds.SOUNDS.register(bus);
        ModEntities.ENTITIES.register(bus);
        ModItemEntities.ITEM_ENTITIES.register(bus);
        ModBlockEntities.BLOCK_ENTITIES.register(bus);
        ModMenuTypes.MENUS.register(bus);
        ModLootModifier.register(bus);
        ModRecipe.SERIALIZER.register(bus);
        ModParticles.PARTICLE_TYPES.register(bus);
        bus.addListener(this::commonSetup);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            ModMessage.register();
        });

    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModEvents {
        @SubscribeEvent
        @SuppressWarnings("removal")
        public static void onClientSetup(FMLClientSetupEvent event) {
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.WATER_LEAF_BLOCK.get(), RenderType.cutout());
            MenuScreens.register(ModMenuTypes.ALTERIO_TABLE_MENU.get(), AlterioTableScreen::new);
        }
    }
}
