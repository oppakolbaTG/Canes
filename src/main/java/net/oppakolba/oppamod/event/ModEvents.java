package net.oppakolba.oppamod.event;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import net.oppakolba.oppamod.Oppamod;
import net.oppakolba.oppamod.mana.PlayerMana;
import net.oppakolba.oppamod.mana.PlayerManaProvider;
import net.oppakolba.oppamod.networking.ModMessage;
import net.oppakolba.oppamod.networking.packet.ManaDataSyncC2SPacket;

@Mod.EventBusSubscriber(modid = Oppamod.MOD_ID)
public class ModEvents {

    @SubscribeEvent
    public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event){
        if(event.getObject() instanceof Player){
            if(!event.getObject().getCapability(PlayerManaProvider.PLAYER_MANA).isPresent()) {
                event.addCapability(new ResourceLocation(Oppamod.MOD_ID, "properties"), new PlayerManaProvider());
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event){
        if(event.isWasDeath()){
            event.getOriginal().getCapability(PlayerManaProvider.PLAYER_MANA).ifPresent(oldStore -> {
                event.getOriginal().getCapability(PlayerManaProvider.PLAYER_MANA).ifPresent(newStore ->{
                        newStore.copyFrom(oldStore);
                });
            });
        }
    }

    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event){
        event.register(PlayerMana.class);
    }

    private static long lastManaUpdateTime = 0;
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event){
        long currentTime = System.currentTimeMillis();
        event.player.getCapability(PlayerManaProvider.PLAYER_MANA).ifPresent(mana -> {
        if(event.side == LogicalSide.SERVER && currentTime - lastManaUpdateTime >= 5000 && mana.getMana() < 100) {
            mana.addMana(1);


                    ModMessage.sendToPlayer(new ManaDataSyncC2SPacket(mana.getMana()), ((ServerPlayer) event.player));
                    event.player.sendSystemMessage(Component.literal("added 1 mana" + mana.getMana()));
                    lastManaUpdateTime = currentTime;
                    if(mana.getMana() > 100){
                        int rem = mana.getMana() - 100;
                        mana.subMana(rem);
                    }
        }
        });
    }


    public static void onPlayerJoinWorld(EntityJoinLevelEvent event){
        if(!event.getLevel().isClientSide){
            if(event.getEntity() instanceof ServerPlayer player){
                player.getCapability(PlayerManaProvider.PLAYER_MANA).ifPresent(mana -> {
                    ModMessage.sendToPlayer(new ManaDataSyncC2SPacket(mana.getMana()), player);
                });
            }
        }
    }
}

