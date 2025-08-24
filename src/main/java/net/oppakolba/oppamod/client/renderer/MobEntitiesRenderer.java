package net.oppakolba.oppamod.client.renderer;


import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.oppakolba.oppamod.Oppamod;


import net.oppakolba.oppamod.client.renderer.entity.BeamRenderer;
import net.oppakolba.oppamod.client.renderer.entity.CustomFireballRenderer;
import net.oppakolba.oppamod.init.ModEntities;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = Oppamod.MOD_ID, value = Dist.CLIENT)
public class MobEntitiesRenderer {
    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event){
        event.registerEntityRenderer(ModEntities.CUSTOM_FIREBALL.get(), CustomFireballRenderer::new);
        event.registerEntityRenderer(ModEntities.BEAM_ENTITY.get(), BeamRenderer::new);
    }
}