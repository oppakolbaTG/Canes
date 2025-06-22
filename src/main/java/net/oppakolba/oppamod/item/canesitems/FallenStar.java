package net.oppakolba.oppamod.item.canesitems;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.LazyOptional;
import net.oppakolba.oppamod.init.ModItems;
import net.oppakolba.oppamod.mana.PlayerMana;
import net.oppakolba.oppamod.mana.PlayerManaProvider;

public class FallenStar extends Item {
    public FallenStar(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        Player player = pContext.getPlayer();
        ItemStack itemStack = pContext.getItemInHand();
        Level level = pContext.getLevel();
        ItemStack item = new ItemStack(ModItems.MANA_CRYSTAL.get());
        if(!(level instanceof ServerLevel)){
            return InteractionResult.CONSUME;
        }
        LazyOptional<PlayerMana> playerManaLazyOptional = player.getCapability(PlayerManaProvider.PLAYER_MANA);
        if(playerManaLazyOptional.isPresent()) {
            PlayerMana mana = playerManaLazyOptional.orElseThrow(IllegalAccessError::new);
            if (mana.getMana() > 15) {
                itemStack.shrink(1);
                mana.subMana(15);
                player.addItem(item);
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.CONSUME;
    }
}
