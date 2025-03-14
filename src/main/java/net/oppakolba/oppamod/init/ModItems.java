package net.oppakolba.oppamod.init;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.oppakolba.oppamod.Oppamod;
import net.oppakolba.oppamod.item.Custom.*;
import net.oppakolba.oppamod.item.ModCreativeModeTab;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Oppamod.MOD_ID);




    public static final RegistryObject<Item> GOLDEN_ORANGE = ITEMS.register("golden_orange",
            () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_FOOD)
                    .food(new FoodProperties.Builder()
                            .nutrition(6).saturationMod(1.2f)
                            .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 600, 1), 1.0f)
                            .effect(() -> new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 2000, 1), 1.0f)
                            .effect(() -> new MobEffectInstance(MobEffects.DAMAGE_BOOST, 2000, 1),1.0f).alwaysEat().build())));

    public static final RegistryObject<Item> MANA_CRYSTAL = ITEMS.register("mana_crystal",
            () -> new ManaCrystal(new Item.Properties().tab(ModCreativeModeTab.OPPA_TAB)));

    public static final RegistryObject<Item> FALLEN_STAR = ITEMS.register("fallen_star",
            () -> new FallenStar(new Item.Properties().tab(ModCreativeModeTab.OPPA_TAB)));


//below common item

 public static final RegistryObject<Item> ORANGE = ITEMS.register("orange",
            () -> new OrangeItem(new Item.Properties().tab(CreativeModeTab.TAB_FOOD)
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

    public static final RegistryObject<Item> SAMPLE_CANE = ITEMS.register("sample_cane",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.OPPA_TAB).stacksTo(1)));

   public static final RegistryObject<Item> FIREBALL_CANE = ITEMS.register("fireball_cane",
            () -> new FireballCane(new Item.Properties().tab(ModCreativeModeTab.OPPA_TAB).stacksTo(1)));

   public static final RegistryObject<Item> LIGHTNING_CANE = ITEMS.register("lightning_cane",
            () -> new LightningCane(new Item.Properties().tab(ModCreativeModeTab.OPPA_TAB).stacksTo(1)));
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
