package net.oppakolba.mana_staffs.init;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.oppakolba.mana_staffs.ManaStaffs;


public class ModParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
            DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, ManaStaffs.MOD_ID);

    public static final RegistryObject<SimpleParticleType> LIGHTNING_PARTICLE =
            PARTICLE_TYPES.register("lightning_particle", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> HEAL_LINE_PARTICLE =
            PARTICLE_TYPES.register("heal_line_particle", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> BEAM_EXPLOSION_PARTICLE =
            PARTICLE_TYPES.register("beam_explosion_particle", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> BEAM_PARTICLE =
            PARTICLE_TYPES.register("beam_particle", () -> new SimpleParticleType(true));
    //public static final RegistryObject<SimpleParticleType> CIRCLE_BEAM_PARTICLE =
          //  PARTICLE_TYPES.register("circle_beam_particle", () -> new SimpleParticleType(true));

    public static void register(IEventBus eventBus){
        PARTICLE_TYPES.register(eventBus);
    }
}