package net.oppakolba.mana_staffs.init;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.*;
import net.oppakolba.mana_staffs.ManaStaffs;
import net.oppakolba.mana_staffs.block.entity.AlterioTableEntity;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, ManaStaffs.MOD_ID);

    public static final RegistryObject<BlockEntityType<AlterioTableEntity>> ALTERIO_TABLE =
            BLOCK_ENTITIES.register("alterio_table", () ->
                    BlockEntityType.Builder.of(AlterioTableEntity::new,
                            ModBlocks.AlTERIO_TABLE.get()).build(null));

    public static void register(IEventBus eventBus){
        BLOCK_ENTITIES.register(eventBus);
    }
}
