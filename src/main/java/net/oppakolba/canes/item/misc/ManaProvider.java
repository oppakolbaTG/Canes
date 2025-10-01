package net.oppakolba.canes.item.misc;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ManaProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    private final ICanesMana mana;
    private final LazyOptional<ICanesMana> optional;

    public ManaProvider(ItemStack stack, int maxMana) {
        this.mana = new ManaStorage(stack, maxMana);
        this.optional = LazyOptional.of(() -> mana);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == CanesCapability.MANA_CAPABILITY){
            return optional.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        if (mana instanceof INBTSerializable) {
            return ((INBTSerializable<CompoundTag>) mana).serializeNBT();
        }
        return new CompoundTag();
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        if (mana instanceof INBTSerializable) {
            ((INBTSerializable<CompoundTag>) mana).deserializeNBT(nbt);
        }
    }
}