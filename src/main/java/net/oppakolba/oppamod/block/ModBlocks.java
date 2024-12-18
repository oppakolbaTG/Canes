package net.oppakolba.oppamod.block;

import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.oppakolba.oppamod.Oppamod;
import net.oppakolba.oppamod.block.custom.SuperBlock;
import net.oppakolba.oppamod.item.ModCreativeModeTab;
import net.oppakolba.oppamod.item.ModItems;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, Oppamod.MOD_ID);

    public static final RegistryObject<Block> CRIMSON_ALTAR = registerBlock("crimson_altar",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.of(Material.STONE)
                    .strength( 40f)
                    .lightLevel(state -> 13)
                    .requiresCorrectToolForDrops(),
                    UniformInt.of(3, 5)), ModCreativeModeTab.OPPA_TAB);

    public static final RegistryObject<Block> DEMONIC_ALTAR = registerBlock("demonic_altar",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.of(Material.STONE).strength( 56f).requiresCorrectToolForDrops(),
                    UniformInt.of(3, 5)), ModCreativeModeTab.OPPA_TAB);
    public static final RegistryObject<Block> SUPER_BLOCK = registerBlock("t_super_block",
            () -> new SuperBlock(BlockBehaviour.Properties.of(Material.STONE)
                    .strength( 56f).requiresCorrectToolForDrops()), ModCreativeModeTab.OPPA_TAB);


//commit


    private static <I extends Block> RegistryObject<I> registerBlock(String name, Supplier<I> block, CreativeModeTab tab) {
        RegistryObject<I> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, tab);

        return toReturn;
    }
    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block,
                                                                            CreativeModeTab tab){
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(tab)));
    }

    public static void register(IEventBus eventBus){
        BLOCKS.register(eventBus);
    }
}
