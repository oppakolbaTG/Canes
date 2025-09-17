package net.oppakolba.canes.init;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.oppakolba.canes.Canes;

import net.oppakolba.canes.entity.projectile.BeamEntity;
import net.oppakolba.canes.entity.projectile.FireballEntity;
import net.oppakolba.canes.entity.projectile.ParticleCharge;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Canes.MOD_ID);


    public static final RegistryObject<EntityType<FireballEntity>> CUSTOM_FIREBALL =
            ENTITIES.register("custom_fireball", () ->
                    EntityType.Builder.<FireballEntity>of(FireballEntity::new, MobCategory.MISC)
                            .sized(1f, 1f).clientTrackingRange(4).updateInterval(2).build(new ResourceLocation(Canes.MOD_ID, "custom_fireball").toString()));


    public static final RegistryObject<EntityType<BeamEntity>> BEAM_ENTITY =
            ENTITIES.register("beam_entity", () ->
                    EntityType.Builder.<BeamEntity>of(BeamEntity::new, MobCategory.MISC)
                            .sized(1f, 1f).clientTrackingRange(4).build(new ResourceLocation(Canes.MOD_ID, "beam_entity").toString()));

    public static final RegistryObject<EntityType<ParticleCharge>> PARTICLE_CHARGE =
            ENTITIES.register("particle_charge", () ->
                    EntityType.Builder.<ParticleCharge>of(ParticleCharge::new, MobCategory.MISC)
                            .sized(0.1f, 0.1f).clientTrackingRange(4).updateInterval(1).build(new ResourceLocation(Canes.MOD_ID, "particle_charge").toString()));


//    public static final RegistryObject<EntityType<DrillEntity>> CANE_DRILL =
//            ENTITIES.register("cane_drill",
//                    () -> EntityType.Builder.<DrillEntity>of(DrillEntity::new, MobCategory.MISC)
//                            .sized(4.0F, 4.0F).build("cane_drill"));




    public static void register(IEventBus eventBus){
        ENTITIES.register(eventBus);
    }
}
