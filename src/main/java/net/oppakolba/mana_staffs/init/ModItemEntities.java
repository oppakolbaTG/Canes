package net.oppakolba.mana_staffs.init;

import net.minecraft.world.entity.EntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import net.oppakolba.mana_staffs.ManaStaffs;

public class ModItemEntities {
    public static final DeferredRegister<EntityType<?>> ITEM_ENTITIES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, ManaStaffs.MOD_ID);


    public void register(IEventBus eventBus){
        ITEM_ENTITIES.register(eventBus);
    }
}
