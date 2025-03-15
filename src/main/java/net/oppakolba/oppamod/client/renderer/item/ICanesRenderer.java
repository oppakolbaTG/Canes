package net.oppakolba.oppamod.client.renderer.item;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class ICanesRenderer {
    public static void register(Item item){
        ItemProperties.register(item, new ResourceLocation("oppamod", "animation"), (pStack, pLevel, pEntity, pSeed) -> {
            if(pLevel == null || pEntity == null) return 0.0f;
            return (Minecraft.getInstance().level.getGameTime() % 10) / 10.0f;
        });
    }
}
