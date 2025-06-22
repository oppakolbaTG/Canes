package net.oppakolba.oppamod.item.misc;

import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import net.oppakolba.oppamod.init.ModEntities;
import net.oppakolba.oppamod.init.ModItemEntities;


public interface ICanseItem {
    default boolean AddManaExperience() {
        return false;
    }


}
