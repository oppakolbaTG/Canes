package net.oppakolba.oppamod.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.oppakolba.oppamod.Oppamod;
import net.oppakolba.oppamod.entity.custom.MagicChargeEntity;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Oppamod.MOD_ID);

    public static final RegistryObject<EntityType<MagicChargeEntity>> MAGIC_CHARGE =
            ENTITIES.register("magic_charge",
                    () -> EntityType.Builder.<MagicChargeEntity>of(MagicChargeEntity::new, MobCategory.MISC)
                            .sized(0.5f, 0.5f)
                            .build("magic_charge"));
}
