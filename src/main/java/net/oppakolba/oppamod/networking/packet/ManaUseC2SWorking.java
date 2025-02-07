package net.oppakolba.oppamod.networking.packet;


import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import net.oppakolba.oppamod.item.Custom.ManaCrystal;
import net.oppakolba.oppamod.item.ModItems;
import net.oppakolba.oppamod.mana.PlayerMana;
import net.oppakolba.oppamod.mana.PlayerManaProvider;
import net.oppakolba.oppamod.networking.ModMessage;
import net.oppakolba.oppamod.sound.ModSounds;
import net.oppakolba.oppamod.util.KeyBinding;

import java.util.function.Supplier;

public class ManaUseC2SWorking {
    private static final String MESSAGE_MANA_USE = "message.oppamod.mana_use";
    private static final String MESSAGE_MANA_NO_USE = "message.oppamod.mana_no_use";
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
                    if(ManaCrystalIsSelected(player, level) && mana.getMana() < 100) {
                        player.sendSystemMessage(Component.translatable(MESSAGE_MANA_USE));
                        level.playSound(null, player.getOnPos(), ModSounds.MANA_USE.get(), SoundSource.PLAYERS, 0.5f, level.random.nextFloat() * 0.1f + 0.9f);
                        mana.addMana(20);
                        player.getMainHandItem().shrink(1);
                        player.sendSystemMessage(Component.literal("you use this shiet" + mana.getMana()));
                        ModMessage.sendToPlayer(new ManaDataSyncC2SPacket(mana.getMana()), player);
                        if(mana.getMana() > 100){
                            int rem = mana.getMana() - 100;
                            mana.subMana(rem);
                        }
                    }

                    else{
                        player.sendSystemMessage(Component.translatable(MESSAGE_MANA_NO_USE + mana.getMana()));
                        ModMessage.sendToPlayer(new ManaDataSyncC2SPacket(mana.getMana()), player);
                    }
            });
        });
        return true;
    }

    private boolean ManaCrystalIsSelected(ServerPlayer player, ServerLevel level) {
        ItemStack mainHandItem = player.getMainHandItem();
        if(!(mainHandItem.getItem() == ModItems.MANA_CRYSTAL.get())){
            return false;
        }
        return true;
    }

}
