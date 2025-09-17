package net.oppakolba.canes.client.renderer;

import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.oppakolba.canes.init.ModItems;

@Mod.EventBusSubscriber(modid = "canes", bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetup {
//    @SubscribeEvent
//    public static void clientSetup(FMLClientSetupEvent event) {
//        ItemProperties.register(ModItems.FIREBALL_CANE.get(), new ResourceLocation("charge"),
//                (stack, level, entity, seed) -> {
//                    if (entity instanceof Player player) {
//                        if (player.isUsingItem() && player.getUseItem() == stack) {
//                            return (1000 - player.getUseItemRemainingTicks()) / 1000.0F;
//                        }
//                    }
//                    return 0.0F;
//                });
//    }
}
