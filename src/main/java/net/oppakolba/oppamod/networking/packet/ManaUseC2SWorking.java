package net.oppakolba.oppamod.networking.packet;


import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import net.oppakolba.oppamod.init.ModItems;
import net.oppakolba.oppamod.mana.PlayerManaProvider;
import net.oppakolba.oppamod.networking.ModMessage;
import net.oppakolba.oppamod.init.ModSounds;

import java.util.function.Supplier;

public class ManaUseC2SWorking {
    public ManaUseC2SWorking(){

    }
    public ManaUseC2SWorking(FriendlyByteBuf buf){

    }

    public void toByte(FriendlyByteBuf buf){

    }


    public boolean handle(Supplier<NetworkEvent.Context> supplier){
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // Все что здесь происходит на сервере
            ServerPlayer player = context.getSender();
            ServerLevel level = player.getLevel();
            player.getCapability(PlayerManaProvider.PLAYER_MANA).ifPresent(mana ->{
                    if(ManaCrystalIsSelected(player, level) && mana.getMana() < mana.getMAX_MANA()) {
                        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
                            ItemStack stack = player.getInventory().getItem(i);
                            if (stack.getItem() == ModItems.MANA_CRYSTAL.get() && !stack.isEmpty()) {
                                stack.shrink(1);
                                break;
                            }
                            }
                        level.playSound(null, player.getOnPos(), ModSounds.MANA_USE.get(), SoundSource.PLAYERS, 0.5f, level.random.nextFloat() * 0.1f + 0.9f);
                        mana.addMana(20);
                        player.getInventory().contains(new ItemStack(ModItems.MANA_CRYSTAL.get()));
                        ModMessage.sendToPlayer(new ManaDataSyncS2CPacket(mana.getMana()), player);
                        if(mana.getMana() > mana.getMAX_MANA()){
                            int rem = mana.getMana() - mana.getMAX_MANA();
                            mana.subMana(rem);
                        }
                    }
                    else{
                        ModMessage.sendToPlayer(new ManaDataSyncS2CPacket(mana.getMana()), player);
                    }
            });
        });
        return true;
    }

    private boolean ManaCrystalIsSelected(ServerPlayer player, ServerLevel level) {
        if(!(player.getInventory().contains(new ItemStack(ModItems.MANA_CRYSTAL.get())))){
            return false;
        }
        return true;
    }

}
