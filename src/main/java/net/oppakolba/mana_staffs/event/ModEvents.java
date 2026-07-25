package net.oppakolba.mana_staffs.event;


import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.oppakolba.mana_staffs.ManaStaffs;
import net.oppakolba.mana_staffs.entity.orbs.ManaOrb;
import net.oppakolba.mana_staffs.item.misc.StaffsItem;
import net.oppakolba.mana_staffs.networking.ModMessage;
import net.oppakolba.mana_staffs.networking.packet.ManaDataSyncPacket;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;


@Mod.EventBusSubscriber(modid = ManaStaffs.MOD_ID)
public class ModEvents {
    public static Random random = new Random();
    private static final AtomicInteger tickCounter = new AtomicInteger(0);
    private static final String MANA_TAG = "mana";
    private static final String MAX_MANA_TAG = "max_mana";
    /* мотрит предметы в инвентаре и постоянно увеличивает их ману
     **/

    @SubscribeEvent
    public static void onStaffsTick(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            int currentTick = tickCounter.incrementAndGet();

            if (currentTick >= 20) {
                tickCounter.set(0);

                if (event.getServer() != null) {
                    for (ServerPlayer player : event.getServer().getPlayerList().getPlayers()) {
                        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
                            ItemStack stack = player.getInventory().getItem(i);
                            if (!stack.isEmpty() && stack.getItem() instanceof StaffsItem) {
                                updateManaForItem(player, stack, i);
                            }
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void spawnManaAfterDie(LivingDeathEvent event){
        if(!(event.getSource().getEntity() instanceof Player player)){
            return;
        }
        if(!(player.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof StaffsItem)){
            return;
        }
        if(event.getEntity().level.isClientSide){
            return;
        }

        int value = random.nextInt(1, 3);
        for(int i = 0; i < value; i++) {
            ManaOrb manaOrb = ManaOrb.spawnOrbWithPop(player.level, event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ());
            event.getEntity().level.addFreshEntity(manaOrb);
        }
    }

    private static void updateManaForItem(ServerPlayer player, ItemStack stack, int slotId) {
        int currentMana = StaffsItem.getMana(stack);
        int maxMana = StaffsItem.getMaxMana(stack);
        initializeMana(stack);
        if (currentMana < maxMana) {
            int newMana = Math.min(currentMana + 1, maxMana);
            StaffsItem.setMana(stack, newMana);

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