package net.oppakolba.oppamod.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.oppakolba.oppamod.Oppamod;
import org.jetbrains.annotations.Nullable;

public class AlterioTableRecipe implements Recipe<SimpleContainer> {
    private final ResourceLocation id;
    private final ItemStack outPut;
    private final NonNullList<Ingredient> recipeItem;

    public AlterioTableRecipe(ResourceLocation id, ItemStack outPut, NonNullList<Ingredient> recipeItem){
        this.id = id;
        this.outPut = outPut;
        this.recipeItem = recipeItem;
    }


    @Override
    public boolean matches(SimpleContainer simpleContainer, Level level) {
        if(level.isClientSide) {
            return false;
        }
        return recipeItem.get(0).test(simpleContainer.getItem(1));
    }

    @Override
    public ItemStack assemble(SimpleContainer simpleContainer) {
        return outPut;
    }

    @Override
    public boolean canCraftInDimensions(int i, int i1) {
        return false;
    }

    @Override
    public ItemStack getResultItem() {
        return outPut.copy();
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<AlterioTableRecipe>{
        private Type(){}
        public static final Type INSTANCE = new Type();
        public static final String ID = "alterio_table";
    }

    public static class Serializer implements RecipeSerializer<AlterioTableRecipe>{
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = new ResourceLocation(Oppamod.MOD_ID,"alterio_table");

        @Override
        public AlterioTableRecipe fromJson(ResourceLocation resourceLocation, JsonObject jsonObject) {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(jsonObject, "output"));

            JsonArray Ingredient = GsonHelper.getAsJsonArray(jsonObject, "ingredient");
            NonNullList<Ingredient> inputs = NonNullList.withSize(1, net.minecraft.world.item.crafting.Ingredient.EMPTY);
            for(int i = 0; i < inputs.size(); i++){
                inputs.set(i, net.minecraft.world.item.crafting.Ingredient.fromJson(Ingredient.get(i)));
            }
            return new AlterioTableRecipe(resourceLocation, output, inputs);
        }

        @Override
        public @Nullable AlterioTableRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(buf.readInt(), Ingredient.EMPTY);
             for(int i = 0; i < inputs.size(); i++){
                 inputs.set(i, Ingredient.fromNetwork(buf));
             }
             ItemStack output = buf.readItem();
             return new AlterioTableRecipe(id, output, inputs);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, AlterioTableRecipe recipe) {
            buf.writeInt(recipe.getIngredients().size());

            for(Ingredient ing : recipe.getIngredients()){
                ing.toNetwork(buf);
            }
            buf.writeItemStack(recipe.getResultItem(), false);
        }
    }
}
