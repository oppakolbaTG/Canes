package net.oppakolba.canes.mana;

import lombok.Setter;
import net.minecraft.nbt.CompoundTag;

public class PlayerMana {
    @Setter
    @lombok.Getter
    private int mana;
    private final int MIN_MANA = 0;

    @Setter
    @lombok.Getter
    private int MAX_MANA = 20;

   


    public void addMana(int add){
        this.mana = Math.max(mana + add, MIN_MANA);
    }
    public void updateMana(int upd) {
        this.MAX_MANA = Math.max(this.MAX_MANA + upd, MIN_MANA);}
    public void subMana(int sub){
        this.mana = Math.min(mana - sub, MAX_MANA);
    }
    public void reloadMana(){
        this.MAX_MANA = 20;
    }

    public void copyFrom(PlayerMana source){
        this.mana = source.mana;
        this.MAX_MANA = source.MAX_MANA;
    }
    public void saveNBTData(CompoundTag nbt){
        nbt.putInt("mana", mana);
        nbt.putInt("max_mana", MAX_MANA);
    }
    public void loadNBTData(CompoundTag nbt){
        nbt.getInt("mana");
       this.MAX_MANA = nbt.getInt("max_mana");
    }

}