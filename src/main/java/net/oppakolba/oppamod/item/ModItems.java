package net.oppakolba.oppamod.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.oppakolba.oppamod.Oppamod;
import net.oppakolba.oppamod.block.ModBlocks;
import net.oppakolba.oppamod.item.Custom.EyeItem;
import net.oppakolba.oppamod.item.Custom.OrangeItem;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Oppamod.MOD_ID);



    public static final RegistryObject<Item> SUMMON_EYE = ITEMS.register("summon_eye",
            () -> new EyeItem(EntityType.WARDEN, 15132362, 10682368, (new Item.Properties().stacksTo(1)).tab(ModCreativeModeTab.OPPA_TAB)));

    public static final RegistryObject<Item> GOLDEN_ORANGE = ITEMS.register("golden_orange",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.OPPA_TAB)
                    .food(new FoodProperties.Builder()
                            .nutrition(6).saturationMod(1.2f)
                            .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 600, 1), 1.0f)
                            .effect(() -> new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 2000, 1), 1.0f)
                            .effect(() -> new MobEffectInstance(MobEffects.DAMAGE_BOOST, 2000, 1),1.0f).alwaysEat().build())));


//below common item

 public static final RegistryObject<Item> ORANGE = ITEMS.register("orange",
            () -> new OrangeItem(new Item.Properties().tab(ModCreativeModeTab.OPPA_TAB)
                    .food(new FoodProperties.Builder()
                            .nutrition(2)
                            .saturationMod(0.5f)
                            .build())));

    public static final RegistryObject<Item> WATER_LEAF_SEED = ITEMS.register("water_leaf_seed",
            () -> new ItemNameBlockItem(ModBlocks.WATER_LEAF_BLOCK.get(),
                    new Item.Properties().tab(ModCreativeModeTab.OPPA_TAB)));

    public static final RegistryObject<Item> WATER_LEAF = ITEMS.register("water_leaf",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.OPPA_TAB)));

    public static final RegistryObject<Item> PLATINUM_BAR = ITEMS.register("platinum_bar",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.OPPA_TAB)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
