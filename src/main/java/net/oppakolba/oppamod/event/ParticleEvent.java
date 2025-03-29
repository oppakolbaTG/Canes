package net.oppakolba.oppamod.event;


import net.minecraft.advancements.critereon.PlayerHurtEntityTrigger;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.oppakolba.oppamod.client.renderer.particles.LightningParticle;
import net.oppakolba.oppamod.init.ModItems;
import net.oppakolba.oppamod.init.ModParticles;
import net.oppakolba.oppamod.item.misc.CanesItem;
import net.oppakolba.oppamod.mana.PlayerManaProvider;
import net.oppakolba.oppamod.networking.ModMessage;
import net.oppakolba.oppamod.networking.packet.ManaDataSyncS2CPacket;

@Mod.EventBusSubscriber(value=Dist.CLIENT, bus=Mod.EventBusSubscriber.Bus.MOD)
public class ParticleEvent {
    @SubscribeEvent
    public static void registerParticles(RegisterParticleProvidersEvent event) {
        event.register(ModParticles.LIGHTNING_PARTICLE.get(), LightningParticle.Provider::new);
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
            ItemProperties.register(
                    ModItems.FIREBALL_CANE.get(),
                    new ResourceLocation("oppamod", "animation"),
                    (stack, world, entity, seed) -> {
                        if (entity == null) {
                            return 0.0F;
                        }

                        if (entity.getUseItem() == stack) {
                            int timeLeft = entity.getUseItemRemainingTicks();
                            int used = stack.getUseDuration() - timeLeft;
                            int stage = used % 11;

                            return (float) stage;
                        }
                        return 0.0F;
                    }
            );
        }


    }



