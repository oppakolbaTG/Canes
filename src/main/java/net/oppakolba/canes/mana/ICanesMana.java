package net.oppakolba.canes.mana;

public interface ICanesMana {
    int getMana();
    int getMaxMana();
    void setMana(int mana);
    void setMaxMana(int maxMana);

    default void addMana(int amount) {
        setMana(getMana() + amount);
    }

    default void subtractMana(int amount) {
        setMana(getMana() - amount);
    }

    default boolean hasEnoughMana(int amount) {
        return getMana() >= amount;
    }

    default boolean isFull(){
        return getMana() == getMaxMana();
    }

}
