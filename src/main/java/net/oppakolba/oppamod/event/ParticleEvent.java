package net.oppakolba.oppamod.event;


import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.oppakolba.oppamod.Oppamod;
import net.oppakolba.oppamod.client.LightningParticle;
import net.oppakolba.oppamod.init.ModParticles;

@Mod.EventBusSubscriber(value=Dist.CLIENT, bus=Mod.EventBusSubscriber.Bus.MOD)public class ParticleEvent {
    @SubscribeEvent
    public static void registerParticles(RegisterParticleProvidersEvent event) {
        event.register(ModParticles.LIGHTNING_PARTICLE.get(), LightningParticle.Provider::new);
    }
}
