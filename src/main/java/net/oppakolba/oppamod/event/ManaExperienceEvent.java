package net.oppakolba.oppamod.event;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.Cancelable;

@Cancelable
public class ManaExperienceEvent extends ICaneEvent{
    @Getter
    @Setter
    int amount;


    public ManaExperienceEvent(Player player, ItemStack itemStack, int amount){
        super(player, itemStack);
        this.amount = amount;
    }
}
