package net.oppakolba.oppamod.init;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.oppakolba.oppamod.Oppamod;
import net.oppakolba.oppamod.entity.item.ManaOrb;

public class ModItemEntities {
    public static final DeferredRegister<EntityType<?>> ITEM_ENTITIES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Oppamod.MOD_ID);


    public void register(IEventBus eventBus){
        ITEM_ENTITIES.register(eventBus);
    }
}
