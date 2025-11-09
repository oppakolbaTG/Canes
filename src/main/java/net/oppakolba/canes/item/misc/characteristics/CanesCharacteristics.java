package net.oppakolba.canes.item.misc.characteristics;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.oppakolba.canes.item.misc.mana.ICanesMana;

public class CanesCharacteristics {
    public static final Capability<ICanesChar> CHARACTERISTICS_CAP =
            CapabilityManager.get(new CapabilityToken<>() {
            });

    public static void register() {
    }
}
