package net.oppakolba.mana_staffs.networking;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.oppakolba.mana_staffs.init.ModItems;
import net.oppakolba.mana_staffs.item.staffs.*;
import net.oppakolba.mana_staffs.item.misc.StaffsItem;

public class StaffUpgradeRequirements {
    public static final int MAX_LEVEL = 5;


    public static String resolveStaffCode(Item item){
        if (item instanceof FireballStaff) return "fc";
        if (item instanceof BeamStaff) return "bc";
        if (item instanceof HealStaff) return "hc";
        if (item instanceof LightningStaff) return "lc";
        if (item instanceof RainOfCharges) return "roc";
        return null;
    }

    public static int primaryLevel(String code, ItemStack stack){
        return switch (code){
            case "fc", "bc","roc" -> StaffsItem.getPower(stack);
            case "hc" -> StaffsItem.getRadius(stack);
            case "lc" -> StaffsItem.getAmt(stack);
            default -> 0;
        };
    }

    private static Item specialItemFor(String code) {
        return switch (code) {
            case "fc" -> ModItems.CHARGED_FIREBALL.get();
            case "bc" -> ModItems.CHARGED_AMETHYST.get();
            case "hc" -> ModItems.CHARGED_HONEY_BOTTLE.get();
            case "lc" -> ModItems.CHARGED_COPPER_INGOT.get();
            case "roc" -> ModItems.CHARGED_ECHO_SHARD.get();
            default -> null;
        };
    }

    public record CostEntry(Item specialItem, int specialCount, Item manaCrystal, int manaCount) {}

    public static CostEntry getCost(String code, int level){
        Item special = specialItemFor(code);
        if(special == null || level < 1 || level > MAX_LEVEL) return null;
        int specialCount = (level + 1) / 2;
        int manaCount = (level / 2) + 1;
        return new CostEntry(special, specialCount, ModItems.MANA_CRYSTAL.get(), manaCount);
    }

    public static boolean hasRequiredItem(Player player, String code, int level){
        CostEntry cost = getCost(code, level);
        if(cost == null)return false;
        int foundSpecialItem = player.getInventory().countItem(cost.specialItem());
        int foundManaItem = player.getInventory().countItem(cost.manaCrystal());
        return foundSpecialItem >= cost.specialCount() && foundManaItem >= cost.manaCount();
    }

    public static void removeRequiredItems(Player player, String code, int level){
        CostEntry cost = getCost(code, level);
        if(cost == null) return;
        shrinkItems(player, cost.specialItem(), cost.specialCount());
        shrinkItems(player, cost.manaCrystal(), cost.manaCount());
    };

    private static void shrinkItems(Player player, Item item, int count){
        int remaining = count;
        var inventory = player.getInventory();
        for(int i = 0; i < inventory.getContainerSize() && remaining > 0; i++){
            ItemStack stack = inventory.getItem(i);
            if(stack.is(item)){
                int remove = Math.min(remaining, stack.getCount());
                stack.shrink(remove);
                remaining -= remove;
            }
        }
    }
}
