package net.oppakolba.mana_staffs.init;

import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.oppakolba.mana_staffs.ManaStaffs;
import net.oppakolba.mana_staffs.recipe.AlterioTableRecipe;

public class ModRecipe {
   public static final DeferredRegister<RecipeSerializer<?>> SERIALIZER =
           DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, ManaStaffs.MOD_ID);

   public static final RegistryObject<RecipeSerializer<AlterioTableRecipe>> ALTERIO_TABLE_RECIPE =
           SERIALIZER.register("alterio_table", () -> AlterioTableRecipe.Serializer.INSTANCE);

   public static void register(IEventBus eventBus){
       SERIALIZER.register(eventBus);
   }
}
