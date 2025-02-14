//package net.oppakolba.oppamod.client;
//
//import net.minecraftforge.client.event.EntityRenderersEvent;
//import net.oppakolba.oppamod.Oppamod;
//import net.oppakolba.oppamod.client.model.CustomModel;
//import net.minecraftforge.api.distmarker.Dist;
//import net.minecraftforge.api.distmarker.OnlyIn;
//import net.minecraftforge.eventbus.api.IEventBus;
//import net.minecraftforge.eventbus.api.SubscribeEvent;
//import net.minecraftforge.fml.common.Mod;
//import net.minecraft.client.renderer.entity.EntityRendererProvider;
//import net.minecraft.client.renderer.entity.EntityRenderer;
//import net.oppakolba.oppamod.client.renderer.CustomFireballRenderer;
//import net.oppakolba.oppamod.init.ModEntities;
//
//@Mod.EventBusSubscriber(modid = Oppamod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
//public class ClientModel {
//    // Регистрация слоя модели
//    @OnlyIn(Dist.CLIENT)
//    @SubscribeEvent
//    public static void clientSetup(IEventBus eventBus) {
//        eventBus.addListener(ClientModel::onRegisterRenderers);
//    }
//    private static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
//        event.registerEntityRenderer(ModEntities.CUSTOM_FIREBALL.get(), CustomFireballRenderer::new);
//    }
//}
