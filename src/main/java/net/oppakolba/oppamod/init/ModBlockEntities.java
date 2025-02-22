package net.oppakolba.oppamod.init;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.*;
import net.oppakolba.oppamod.Oppamod;
import net.oppakolba.oppamod.block.entity.AlterionatingTableEntity;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Oppamod.MOD_ID);

    public static final RegistryObject<BlockEntityType<AlterionatingTableEntity>> ALTERIO_TABLE =
            BLOCK_ENTITIES.register("alterionatina_table", () ->
                    BlockEntityType.Builder.of(AlterionatingTableEntity::new,
                            ModBlocks.AlTERIONATING_TABLE.get()).build(null));

    public static void register(IEventBus eventBus){
        BLOCK_ENTITIES.register(eventBus);
    }
}
