package net.oppakolba.oppamod.init;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.oppakolba.oppamod.Oppamod;
import net.oppakolba.oppamod.entity.projectile.FireballEntity;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Oppamod.MOD_ID);


    public static final RegistryObject<EntityType<FireballEntity>> CUSTOM_FIREBALL =
            ENTITIES.register("custom_fireball", () ->
                    EntityType.Builder.<FireballEntity>of(FireballEntity::new, MobCategory.MISC)
                            .sized(1f, 1f).build(new ResourceLocation(Oppamod.MOD_ID, "custom_fireball").toString()));


//    public static final RegistryObject<EntityType<DrillEntity>> CANE_DRILL =
//            ENTITIES.register("cane_drill",
//                    () -> EntityType.Builder.<DrillEntity>of(DrillEntity::new, MobCategory.MISC)
//                            .sized(4.0F, 4.0F).build("cane_drill"));




    public static void register(IEventBus eventBus){
        ENTITIES.register(eventBus);
    }
}
