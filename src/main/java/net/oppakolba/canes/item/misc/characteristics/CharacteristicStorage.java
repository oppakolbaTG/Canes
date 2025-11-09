package net.oppakolba.canes.item.misc.characteristics;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.INBTSerializable;

public class CharacteristicStorage implements ICanesChar, INBTSerializable<CompoundTag> {
    private String power = "power";
    private String radius = "radius";
    private String heal = "heal";
    private String amt = "amt";
    private final ItemStack stack;


    public CharacteristicStorage(ItemStack stack) {
        this.stack = stack;
        initializeNBT();
    }

    private void initializeNBT() {
        CompoundTag tag = stack.getOrCreateTag();
        if (!tag.contains(power)) {
            tag.putInt(power, 1);
        }
        if (!tag.contains(radius)) {
            tag.putInt(radius, 1);
        }
        if (!tag.contains(amt)) {
            tag.putInt(amt, 1);
        }
        if (!tag.contains(heal)) {
            tag.putInt(heal, 1);
        }
    }
    @Override
    public int getPower(ItemStack stack){
        CompoundTag tag = stack.getOrCreateTag();
        return tag.getInt(power);
    }
    @Override
    public int getRadius(ItemStack stack){
        CompoundTag tag = stack.getOrCreateTag();
        return tag.getInt(radius);
    }
    @Override
    public int getHeal(ItemStack stack){
        CompoundTag tag = stack.getOrCreateTag();
        return tag.getInt(heal);
    }
    @Override
    public int getAmt(ItemStack stack){
        CompoundTag tag = stack.getOrCreateTag();
        return tag.getInt(amt);
    }
    @Override
    public void addPower(ItemStack stack, int value){
        CompoundTag tag = stack.getOrCreateTag();
        int cValue = Math.min(5, Math.max(getPower(stack) + value, 0));
        tag.putInt(power, cValue);
    }
    @Override
    public void addRadius(ItemStack stack, int value){
        CompoundTag tag = stack.getOrCreateTag();
        int cValue = Math.min(5, Math.max(getRadius(stack) + value, 0));
        tag.putInt(radius, cValue);
    }
    @Override
    public void addHeal(ItemStack stack, int value){
        CompoundTag tag = stack.getOrCreateTag();
        int cValue = Math.min(5, Math.max(getHeal(stack) + value, 0));
        tag.putInt(heal, cValue);
    }
    @Override
    public void addAmt(ItemStack stack, int value){
        CompoundTag tag = stack.getOrCreateTag();
        int cValue = Math.min(5, Math.max(getAmt(stack) + value, 0));
        tag.putInt(amt, cValue);
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
