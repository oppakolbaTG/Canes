package net.oppakolba.canes.event;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.oppakolba.canes.Canes;
import net.oppakolba.canes.item.misc.CanesItem;
import net.oppakolba.canes.networking.ModMessage;
import net.oppakolba.canes.networking.packet.ManaDataSyncPacket;

import java.util.concurrent.atomic.AtomicInteger;


@Mod.EventBusSubscriber(modid = Canes.MOD_ID)
public class ModEvents {
    private static final AtomicInteger tickCounter = new AtomicInteger(0);
    private static final String MANA_TAG = "mana";
    private static final String MAX_MANA_TAG = "max_mana";

    /**
     * Смотрит предметы в инвентаре и постоянно увеличивает их ману
     **/

    @SubscribeEvent
    public static void onCanesTick(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            int currentTick = tickCounter.incrementAndGet();

            if (currentTick >= 20) {
                tickCounter.set(0);

                if (event.getServer() != null) {
                    for (ServerPlayer player : event.getServer().getPlayerList().getPlayers()) {
                        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
                            ItemStack stack = player.getInventory().getItem(i);
                            if (!stack.isEmpty() && stack.getItem() instanceof CanesItem canesItem) {
                                updateManaForItem(player, stack, i);
                            }
                        }
                    }
                }
            }
        }
    }

    private static void updateManaForItem(ServerPlayer player, ItemStack stack, int slotId) {
        int currentMana = CanesItem.getMana(stack);
        int maxMana = CanesItem.getMaxMana(stack);
        initializeMana(stack);
        if (currentMana < maxMana) {
            int newMana = Math.min(currentMana + 1, maxMana);
            CanesItem.setMana(stack, newMana);

            if (currentMana != newMana) {
                ModMessage.sendToClient(new ManaDataSyncPacket(newMana, slotId), player);
            }
        }
    }



    private static void initializeMana(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        if (!tag.contains(MAX_MANA_TAG)) {
            tag.putInt(MAX_MANA_TAG, 20);
        }
        if (!tag.contains(MANA_TAG)) {
            tag.putInt(MANA_TAG, 0);
        }
    }
}