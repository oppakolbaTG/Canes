package net.oppakolba.canes.event;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.thread.BlockableEventLoop;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.oppakolba.canes.Canes;
import net.oppakolba.canes.client.renderer.item.ICanesRenderer;
import net.oppakolba.canes.entity.projectile.BeamEntity;
import net.oppakolba.canes.init.ModItemEntities;
import net.oppakolba.canes.init.ModItems;
import net.oppakolba.canes.item.misc.CanesItem;
import net.oppakolba.canes.mana.PlayerMana;
import net.oppakolba.canes.mana.PlayerManaProvider;
import net.oppakolba.canes.networking.ModMessage;
import net.oppakolba.canes.networking.packet.ManaDataSyncS2CPacket;

@Mod.EventBusSubscriber(modid = Canes.MOD_ID)
public class ModEvents {


    @SubscribeEvent
    public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event){
        if(event.getObject() instanceof Player){
            if(!event.getObject().getCapability(PlayerManaProvider.PLAYER_MANA).isPresent()) {
                event.addCapability(new ResourceLocation(Canes.MOD_ID, "properties"), new PlayerManaProvider());
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
        if(event.side == LogicalSide.SERVER && currentTime - lastManaUpdateTime >= 1000 && mana.getMana() < mana.getMAX_MANA()) {
            mana.addMana(1);


                    ModMessage.sendToPlayer(new ManaDataSyncS2CPacket(mana.getMana()), ((ServerPlayer) event.player));
                    lastManaUpdateTime = currentTime;
                    if(mana.getMana() > 100){
                        int rem = mana.getMana() - 100;
                        mana.subMana(rem);
                    }
        }
        });
    }

}

