package net.oppakolba.mana_staffs.init;


import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.oppakolba.mana_staffs.ManaStaffs;
import net.oppakolba.mana_staffs.item.staffs.*;
import net.oppakolba.mana_staffs.item.CreativeModeTab;
import net.oppakolba.mana_staffs.item.manastaffsitems.ManaCrystal;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, ManaStaffs.MOD_ID);



    public static final RegistryObject<Item> MANA_CRYSTAL = ITEMS.register("mana_crystal",
            () -> new ManaCrystal(new Item.Properties().rarity(Rarity.EPIC).tab(CreativeModeTab.OPPA_TAB)));



    public static final RegistryObject<Item> CHARGED_COPPER_INGOT = ITEMS.register("charged_copper_ingot",
            () -> new Item(new Item.Properties().tab(CreativeModeTab.OPPA_TAB)));

    public static final RegistryObject<Item> CHARGED_AMETHYST = ITEMS.register("charged_amethyst",
            () -> new Item(new Item.Properties().tab(CreativeModeTab.OPPA_TAB)));


    public static final RegistryObject<Item> CHARGED_FIREBALL = ITEMS.register("charged_fireball",
            () -> new Item(new Item.Properties().tab(CreativeModeTab.OPPA_TAB)));


    public static final RegistryObject<Item> CHARGED_ECHO_SHARD = ITEMS.register("charged_echo_shard",
            () -> new Item(new Item.Properties().tab(CreativeModeTab.OPPA_TAB)));


    public static final RegistryObject<Item> CHARGED_HONEY_BOTTLE = ITEMS.register("charged_honey_bottle",
            () -> new Item(new Item.Properties().tab(CreativeModeTab.OPPA_TAB)));



    //manastaffs

    public static final RegistryObject<Item> SAMPLE_STAFF = ITEMS.register("sample_staff",
            () -> new Item(new Item.Properties().tab(CreativeModeTab.MANASTAFFS_TAB).stacksTo(1)));

   public static final RegistryObject<Item> FIREBALL_STAFF = ITEMS.register("fireball_staff",
            () -> new FireballStaff(new Item.Properties().rarity(Rarity.EPIC).tab(CreativeModeTab.MANASTAFFS_TAB).stacksTo(1)));

   public static final RegistryObject<Item> LIGHTNING_STAFF = ITEMS.register("lightning_staff",
            () -> new LightningStaff(new Item.Properties().rarity(Rarity.EPIC).tab(CreativeModeTab.MANASTAFFS_TAB).stacksTo(1)));

   public static final RegistryObject<Item> HEAL_STAFF = ITEMS.register("heal_staff",
            () -> new HealStaff(new Item.Properties().rarity(Rarity.EPIC).tab(CreativeModeTab.MANASTAFFS_TAB).stacksTo(1)));

   public static final RegistryObject<Item> BEAM_STAFF = ITEMS.register("beam_staff",
            () -> new BeamStaff(new Item.Properties().rarity(Rarity.EPIC).tab(CreativeModeTab.MANASTAFFS_TAB).stacksTo(1)));

   public static final RegistryObject<Item> RAIN_OF_CHARGES = ITEMS.register("rain_of_charges",
            () -> new RainOfCharges(new Item.Properties().rarity(Rarity.EPIC).tab(CreativeModeTab.MANASTAFFS_TAB).stacksTo(1)));

   public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
