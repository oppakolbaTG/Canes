package net.oppakolba.canes.item.misc;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.INBTSerializable;

public class ManaStorage implements ICanesMana, INBTSerializable<CompoundTag> {
    private String MANA_TAG = "mana";
    private String MAX_MANA_TAG = "maxMana";
    private final ItemStack stack;
    private final int defaultMaxMana;

    public ManaStorage(ItemStack stack, int defaultMaxMana) {
        this.stack = stack;
        this.defaultMaxMana = defaultMaxMana;
        // Инициализируем NBT если нужно
        initializeNBT();
    }


    private void initializeNBT() {
        CompoundTag tag = stack.getOrCreateTag();
        if (!tag.contains(MAX_MANA_TAG)) {
            tag.putInt(MAX_MANA_TAG, defaultMaxMana);
        }
        if (!tag.contains(MANA_TAG)) {
            tag.putInt(MANA_TAG, 0);
        }
    }

    @Override
    public int getMana(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        return tag.getInt(MANA_TAG);
    }

    @Override
    public int getMaxMana(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        return tag.getInt(MAX_MANA_TAG);
    }

    @Override
    public void setMana(int mana, ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        int maxMana = getMaxMana(stack);
        int clampedMana = Math.min(Math.max(mana, 0), maxMana);
        tag.putInt(MANA_TAG, clampedMana);
    }


    @Override
    public void setMaxMana(int Mana, ItemStack itemStack) {
        CompoundTag tag = stack.getOrCreateTag();
        int maxMana = getMaxMana(stack);
        int clampedMana = Math.min(Math.max(Mana, 0), maxMana);
        tag.putInt(MANA_TAG, clampedMana);
    }

    @Override
    public void addMana(int amount, ItemStack stack) {
        int currentMana = getMana(stack);
        int maxMana = getMaxMana(stack);
        setMana(currentMana + amount, stack);
    }

    @Override
    public void subtractMana(int amount, ItemStack stack) {
        int currentMana = getMana(stack);
        setMana(currentMana - amount, stack);
    }

    @Override
    public boolean hasEnoughMana(int amount, ItemStack stack) {
        return getMana(stack) >= amount;
    }

    @Override
    public boolean isFull(ItemStack stack) {
        return getMana(stack) >= getMaxMana(stack);
    }

    @Override
    public CompoundTag serializeNBT() {
        return stack.getOrCreateTag().copy();
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        if (tag != null) {
            stack.getOrCreateTag().merge(tag);
        }
    }
}