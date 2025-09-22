package net.oppakolba.canes.mana;

import lombok.Setter;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class CanesMana implements ICanesMana {
    ItemStack itemStack;

    private static final String MANA_TAG = "mana";
    private static final String MAX_MANA_TAG = "max_mana";


   public CanesMana(ItemStack stack){
       this.itemStack = stack;
       if(!stack.hasTag()){
           stack.setTag(new CompoundTag());
       }
   }

    private void ensureDefaultValues() {
        CompoundTag tag = itemStack.getOrCreateTag();
        if (!tag.contains("mana")) {
            tag.putInt("mana", 0);
        }
        if (!tag.contains("max_mana")) {
            tag.putInt("max_mana", 20);
        }
    }

    public int getMana() {
       CompoundTag tag = itemStack.getOrCreateTag();
        return tag.getInt(MANA_TAG);
    }

    public int getMaxMana(){
       CompoundTag tag = itemStack.getOrCreateTag();
       return tag.getInt(MAX_MANA_TAG);
    }

    public void setMana(int mana){
       CompoundTag tag = itemStack.getOrCreateTag();
       tag.putInt(MANA_TAG, Math.max(0, Math.min(getMaxMana(), mana)));
    }

    public void setMaxMana(int maxMana){
       CompoundTag tag = itemStack.getOrCreateTag();
       tag.putInt(MAX_MANA_TAG, Math.max(0, maxMana));

       if(getMana() >= maxMana){
           setMana(maxMana);
       }
    }


    public boolean hasEnoughMana(int amount){
       return getMana() <= amount;
    }

    public boolean isFull(){
       return getMana() == getMaxMana();
    }



}