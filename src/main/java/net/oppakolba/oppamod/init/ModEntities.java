package net.oppakolba.oppamod.init;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.oppakolba.oppamod.Oppamod;
import net.oppakolba.oppamod.entity.custom.CustomFireball;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Oppamod.MOD_ID);

//    public static final RegistryObject<EntityType<MagicChargeEntity>> MAGIC_CHARGE =
//            ENTITIES.register("magic_charge",
//                    () -> EntityType.Builder.<MagicChargeEntity>of(MagicChargeEntity::new, MobCategory.MISC)
//                            .sized(0.5f, 0.5f)
//                            .build("magic_charge"));



    public static final RegistryObject<EntityType<CustomFireball>> CUSTOM_FIREBALL =
            ENTITIES.register("custom_fireball", () ->
                    EntityType.Builder.<CustomFireball>of((type, level) -> new CustomFireball(type, level), MobCategory.MISC)
                            .sized(4f, 2f).build(new ResourceLocation(Oppamod.MOD_ID, "custom_fireball").toString()));



    public static void register(IEventBus eventBus){
        ENTITIES.register(eventBus);
    }
}
