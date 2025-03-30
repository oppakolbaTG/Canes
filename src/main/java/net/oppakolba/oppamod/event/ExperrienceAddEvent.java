package net.oppakolba.oppamod.event;


import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.oppakolba.oppamod.item.misc.CanesItem;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ExperrienceAddEvent {
    @SubscribeEvent
    public static void onLivingDrops(LivingDropsEvent event) {
        Level level = event.getEntity().level;
        Player killer = event.getSource().getEntity() instanceof Player ? (Player) event.getSource().getEntity() : null;
        if (killer == null)
            return;
        /*
        Could return random quantity of orbs 2 - 8
        */
        ItemStack stack = killer.getMainHandItem();
        if (stack.getItem() instanceof CanesItem canesItem) {
            System.out.println("killed");
            int xpToDrop = 4;
            Vec3 pos = event.getEntity().position();
            canesItem.dropManaExp(level, pos, xpToDrop);
        }
    }
}
