package net.oppakolba.canes.mana;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CanesManaProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<CanesMana> CANES_MANA = CapabilityManager.get(new CapabilityToken<CanesMana>() {  });
    private final ItemStack stack;
    private final LazyOptional<ICanesMana> handler;

    public CanesManaProvider(ItemStack stack) {
        this.stack = stack;
        this.handler = LazyOptional.of(() -> new CanesMana(stack));
    }


    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == ManaCapabilities.ITEM_MANA) {
            if (stack != null){
                return LazyOptional.of(() -> new CanesManaProvider(stack)).cast();
        }
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        handler.ifPresent(handler -> {
                    nbt.putInt("mana", handler.getMana());
                    nbt.putInt("max_mana", handler.getMaxMana());
                });

        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        CompoundTag tag = new CompoundTag();
        handler.ifPresent(handler -> {
            if (nbt.contains("mana")) {
                handler.setMana(nbt.getInt("mana"));
            }
            if (nbt.contains("max_mana")) {
                handler.setMaxMana(nbt.getInt("max_mana"));
            }
        });
    }
}
