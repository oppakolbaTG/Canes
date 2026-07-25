package net.oppakolba.mana_staffs.init;


import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.oppakolba.mana_staffs.ManaStaffs;


import net.oppakolba.mana_staffs.client.renderer.entity.BeamRenderer;
import net.oppakolba.mana_staffs.client.renderer.entity.CustomFireballRenderer;
import net.oppakolba.mana_staffs.client.renderer.entity.ManaOrbRenderer;
import net.oppakolba.mana_staffs.client.renderer.entity.ParticleChargeRenderer;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = ManaStaffs.MOD_ID, value = Dist.CLIENT)
public class MobEntitiesRenderer {
    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event){
        event.registerEntityRenderer(ModEntities.CUSTOM_FIREBALL.get(), CustomFireballRenderer::new);
        event.registerEntityRenderer(ModEntities.BEAM_ENTITY.get(), BeamRenderer::new);
        event.registerEntityRenderer(ModEntities.PARTICLE_CHARGE.get(), ParticleChargeRenderer::new);
        event.registerEntityRenderer(ModEntities.MANA_ORB.get(), ManaOrbRenderer::new);
    }
}