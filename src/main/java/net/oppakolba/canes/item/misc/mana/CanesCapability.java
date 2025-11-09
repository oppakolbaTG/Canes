package net.oppakolba.canes.item.misc.mana;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

public class CanesCapability {
    public static final Capability<ICanesMana> MANA_CAPABILITY =
            CapabilityManager.get(new CapabilityToken<>() {
            });

    public static void register() {
    }

}





