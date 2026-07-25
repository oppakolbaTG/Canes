package net.oppakolba.mana_staffs.item.misc.mana;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

public class StaffsCapability {
    public static final Capability<IStaffsMana> MANA_CAPABILITY =
            CapabilityManager.get(new CapabilityToken<>() {
            });

    public static void register() {
    }

}





