package net.oppakolba.canes.init;

import net.minecraft.world.entity.EntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import net.oppakolba.canes.Canes;

public class ModItemEntities {
    public static final DeferredRegister<EntityType<?>> ITEM_ENTITIES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Canes.MOD_ID);


    public void register(IEventBus eventBus){
        ITEM_ENTITIES.register(eventBus);
    }
}
