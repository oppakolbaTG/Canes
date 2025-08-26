package net.oppakolba.canes.item.misc;

import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import net.oppakolba.canes.init.ModEntities;
import net.oppakolba.canes.init.ModItemEntities;


public interface ICanseItem {
    default boolean AddManaExperience() {
        return false;
    }


}
