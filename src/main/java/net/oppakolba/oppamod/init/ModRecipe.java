package net.oppakolba.oppamod.init;

import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.oppakolba.oppamod.Oppamod;
import net.oppakolba.oppamod.recipe.AlterioTableRecipe;

public class ModRecipe {
   public static final DeferredRegister<RecipeSerializer<?>> SERIALIZER =
           DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Oppamod.MOD_ID);

   public static final RegistryObject<RecipeSerializer<AlterioTableRecipe>> ALTERIO_TABLE_RECIPE =
           SERIALIZER.register("alterio_table", () -> AlterioTableRecipe.Serializer.INSTANCE);

   public static void register(IEventBus eventBus){
       SERIALIZER.register(eventBus);
   }
}
