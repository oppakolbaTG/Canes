package net.oppakolba.mana_staffs.init;

import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.oppakolba.mana_staffs.ManaStaffs;
import net.oppakolba.mana_staffs.block.custom.AlterioTableBlock;
import net.oppakolba.mana_staffs.item.CreativeModeTab;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, ManaStaffs.MOD_ID);



    public static final RegistryObject<Block> AlTERIO_TABLE = registerBlock("alterio_table",
            () -> new AlterioTableBlock(BlockBehaviour.Properties.of(Material.STONE)
                    .noOcclusion()
                    .strength( 18f)
                    .requiresCorrectToolForDrops()), CreativeModeTab.OPPA_TAB);

    private static <I extends Block> RegistryObject<I> registerBlock(String name, Supplier<I> block, net.minecraft.world.item.CreativeModeTab tab) {
        RegistryObject<I> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, tab);

        return toReturn;
    }
    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block,
                                                                            net.minecraft.world.item.CreativeModeTab tab){
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(tab)));
    }

    public static void register(IEventBus eventBus){
        BLOCKS.register(eventBus);
    }
}
