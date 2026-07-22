package net.oppakolba.canes.networking;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.fml.common.Mod;
import net.oppakolba.canes.init.ModItems;
import net.oppakolba.canes.item.canes.*;
import net.oppakolba.canes.item.misc.CanesItem;

public class CaneUpgradeRequirements {
    public static final int MAX_LEVEL = 5;


    public static String resolveCaneCode(Item item){
        if (item instanceof FireballCane) return "fc";
        if (item instanceof BeamCane) return "bc";
        if (item instanceof HealCane) return "hc";
        if (item instanceof LightningCane) return "lc";
        if (item instanceof RainOfCharges) return "roc";
        return null;
    }

    public static int primaryLevel(String code, ItemStack stack){
        return switch (code){
            case "fc", "bc","roc" -> CanesItem.getPower(stack);
            case "hc" -> CanesItem.getRadius(stack);
            case "lc" -> CanesItem.getAmt(stack);
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
