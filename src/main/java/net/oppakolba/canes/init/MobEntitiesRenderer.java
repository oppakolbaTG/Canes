package net.oppakolba.canes.init;


import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.oppakolba.canes.Canes;


import net.oppakolba.canes.client.renderer.entity.BeamRenderer;
import net.oppakolba.canes.client.renderer.entity.CustomFireballRenderer;
import net.oppakolba.canes.client.renderer.entity.ParticleChargeRenderer;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = Canes.MOD_ID, value = Dist.CLIENT)
public class MobEntitiesRenderer {
    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event){
        event.registerEntityRenderer(ModEntities.CUSTOM_FIREBALL.get(), CustomFireballRenderer::new);
        event.registerEntityRenderer(ModEntities.BEAM_ENTITY.get(), BeamRenderer::new);
        event.registerEntityRenderer(ModEntities.PARTICLE_CHARGE.get(), ParticleChargeRenderer::new);
    }
}