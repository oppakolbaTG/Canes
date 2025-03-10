package net.oppakolba.oppamod.init;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.oppakolba.oppamod.Oppamod;
import net.oppakolba.oppamod.entity.custom.CustomFireball;
import net.oppakolba.oppamod.entity.custom.FireballSeal;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Oppamod.MOD_ID);


    public static final RegistryObject<EntityType<CustomFireball>> CUSTOM_FIREBALL =
            ENTITIES.register("custom_fireball", () ->
                    EntityType.Builder.<CustomFireball>of(CustomFireball::new, MobCategory.MISC)
                            .sized(1f, 1f).build(new ResourceLocation(Oppamod.MOD_ID, "custom_fireball").toString()));

    public static final RegistryObject<EntityType<FireballSeal>> FIREBALL_SEAL =
            ENTITIES.register("fireball_seal",
                    () -> EntityType.Builder.<FireballSeal>of(FireballSeal::new, MobCategory.MISC)
                            .sized(4.0F, 4.0F).build("fireball_seal"));


    public static void register(IEventBus eventBus){
        ENTITIES.register(eventBus);
    }
}
