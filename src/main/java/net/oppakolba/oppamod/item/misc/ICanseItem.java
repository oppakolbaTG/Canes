package net.oppakolba.oppamod.item.misc;

import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.oppakolba.oppamod.entity.item.ManaOrb;
import net.oppakolba.oppamod.init.ModEntities;
import net.oppakolba.oppamod.init.ModItemEntities;



public interface ICanseItem {
    default boolean AddManaExperience() {
        return false;
    }

    default void dropManaExp(Level level, Vec3 pos, int amount) {
        if (amount <= 0)
            return;


        var random = level.getRandom();


        int maxExpPerOrb = 5;
        int orbs = Math.max(
                amount / maxExpPerOrb,
                random.nextInt(amount) + 1
        );
        for (int i = 0; i < orbs; i++) {
            ManaOrb orb = new ManaOrb(ModEntities.MANA_ORB.get(), level);

            orb.setPos(pos);


            orb.setDeltaMovement(
                    (random.nextDouble() - 0.5) * 0.3,
                    0.1 + random.nextDouble() * 0.2,
                    (random.nextDouble() - 0.5) * 0.3
            );
            level.addFreshEntity(orb);
        }
    }
}