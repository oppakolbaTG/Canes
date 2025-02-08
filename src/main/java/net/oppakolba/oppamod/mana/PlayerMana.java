package net.oppakolba.oppamod.mana;

import net.minecraft.nbt.CompoundTag;

public class PlayerMana {
    private int mana;
    private final int MIN_MANA = 0;
    private int MAX_MANA = 20;

    public int getMana(){
        return mana;
    }
    public int getMMana(){
        return MAX_MANA;
    }

    public void addMana(int add){
        this.mana = Math.max(mana + add, MIN_MANA);
    }
    public void updateMana(int upd){this.MAX_MANA = Math.max(MAX_MANA + upd, MIN_MANA);}
    public void subMana(int sub){
        this.mana = Math.min(mana - sub, MAX_MANA);
    }
    public void copyFrom(PlayerMana source){
        this.mana = source.mana;
    }
      public void saveNBTData(CompoundTag nbt){
        nbt.putInt("mana", mana);
      }
      public void loadNBTData(CompoundTag nbt){
        nbt.getInt("mana");
      }

}