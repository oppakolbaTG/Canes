package net.oppakolba.mana_staffs.util.compat;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.oppakolba.mana_staffs.ManaStaffs;
import net.oppakolba.mana_staffs.init.ModBlocks;
import net.oppakolba.mana_staffs.recipe.AlterioTableRecipe;

public class AlterioningCategory implements IRecipeCategory<AlterioTableRecipe> {
    public static final ResourceLocation UID = new ResourceLocation(ManaStaffs.MOD_ID, "alterioning");
    public static final ResourceLocation TEXTURE = new ResourceLocation(ManaStaffs.MOD_ID, "textures/gui/alterio_table_screen.png");
    public static final RecipeType<AlterioTableRecipe> ALTERIONING_TYPE = new RecipeType<>(UID, AlterioTableRecipe.class);
    private final IDrawable background;
    private  final IDrawable icon;

    public AlterioningCategory(IGuiHelper helper){
        this.background = helper.createDrawable(TEXTURE, 0, 0, 176, 85);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.AlTERIO_TABLE.get()));
    }

    @Override
    public RecipeType<AlterioTableRecipe> getRecipeType() {
        return ALTERIONING_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block.manastaffs.alterio_table");
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, AlterioTableRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 12 ,15).addIngredients(recipe.getIngredients().get(0));
        builder.addSlot(RecipeIngredientRole.INPUT, 105,15).addIngredients(recipe.getIngredients().get(1));
        builder.addSlot(RecipeIngredientRole.OUTPUT,105,60).addItemStack(recipe.getResultItem());
    }
}
