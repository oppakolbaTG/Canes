package net.oppakolba.mana_staffs.item.misc.characteristics;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

public class StaffsCharacteristics {
    public static final Capability<IStaffsChar> CHARACTERISTICS_CAP =
            CapabilityManager.get(new CapabilityToken<>() {
            });

    public static void register() {
    }
}
