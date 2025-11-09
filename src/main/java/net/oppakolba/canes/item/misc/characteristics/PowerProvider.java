package net.oppakolba.canes.item.misc.characteristics;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PowerProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    private final ICanesChar power;
    private final LazyOptional<ICanesChar> optional;

    public PowerProvider(ItemStack stack) {
        this.power = new CharacteristicStorage(stack);
        this.optional = LazyOptional.of(() -> power );
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == CanesCharacteristics.CHARACTERISTICS_CAP){
            return optional.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        if (power instanceof INBTSerializable) {
            return ((INBTSerializable<CompoundTag>) power).serializeNBT();
        }
        return new CompoundTag();
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        if (power instanceof INBTSerializable) {
            ((INBTSerializable<CompoundTag>) power).deserializeNBT(nbt);
        }
    }
}
