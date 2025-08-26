package net.oppakolba.canes.event;


import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.Event;

import javax.annotation.Nullable;

@AllArgsConstructor
public class ICaneEvent extends Event{
    Player player;

    @Getter
    ItemStack itemStack;

    @Nullable
    public Player getEntity(){
        return player;
    }
}
