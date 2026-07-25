package net.oppakolba.mana_staffs.client.renderer;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "manastaffs", bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetup {
//    @SubscribeEvent
//    public static void clientSetup(FMLClientSetupEvent event) {
//        ItemProperties.register(ModItems.FIREBALL_staff.get(), new ResourceLocation("charge"),
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
