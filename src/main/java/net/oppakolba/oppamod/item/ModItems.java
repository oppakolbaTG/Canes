package net.oppakolba.oppamod.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.oppakolba.oppamod.Oppamod;
import net.oppakolba.oppamod.item.Custom.EyeItem;
import net.oppakolba.oppamod.item.Custom.OrangeItem;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Oppamod.MOD_ID);

    public static final RegistryObject<Item> ORANGE = ITEMS.register("orange",
            () -> new OrangeItem(new Item.Properties().tab(ModCreativeModeTab.OPPA_TAB).food(new FoodProperties.Builder()
                            .nutrition(2)
                            .saturationMod(0.5f)
                            .build())));

    public static final RegistryObject<Item> GOLDEN_ORANGE = ITEMS.register("golden_orange",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.OPPA_TAB).food(new FoodProperties.Builder()
                    .nutrition(6).saturationMod(1.2f)
                    .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 600, 1), 1.0f)
                    .effect(() -> new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 2000, 1), 1.0f)
                    .effect(() -> new MobEffectInstance(MobEffects.DAMAGE_BOOST, 2000, 1),1.0f).alwaysEat().build())));

    public static final RegistryObject<Item> SUMMON_EYE = ITEMS.register("summon_eye",
            () -> new EyeItem(new Item.Properties().tab(ModCreativeModeTab.OPPA_TAB).stacksTo(1)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
