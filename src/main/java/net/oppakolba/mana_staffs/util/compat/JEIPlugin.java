package net.oppakolba.mana_staffs.util.compat;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;
import net.oppakolba.mana_staffs.ManaStaffs;
import net.oppakolba.mana_staffs.recipe.AlterioTableRecipe;
import net.oppakolba.mana_staffs.screen.AlterioTableScreen;

import java.util.List;

@JeiPlugin
public class JEIPlugin implements IModPlugin {
    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(ManaStaffs.MOD_ID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new AlterioningCategory(registration.getJeiHelpers().getGuiHelper()));
    }


    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager recipeManager = Minecraft.getInstance().level.getRecipeManager();
        List<AlterioTableRecipe> alterioningRecipe = recipeManager.getAllRecipesFor(AlterioTableRecipe.Type.INSTANCE);
        registration.addRecipes(AlterioningCategory.ALTERIONING_TYPE, alterioningRecipe);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(AlterioTableScreen.class, 120, 32, 15, 35, AlterioningCategory.ALTERIONING_TYPE);
    }
}
