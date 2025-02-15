package net.oppakolba.oppamod.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.ResourceLoadStateTracker;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.oppakolba.oppamod.Oppamod;
import net.oppakolba.oppamod.entity.custom.CustomFireball;

@OnlyIn(Dist.CLIENT)
public class CustomFireballRenderer extends EntityRenderer<CustomFireball> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Oppamod.MOD_ID, "textures/entity/custom_fireball.png");

    public CustomFireballRenderer(EntityRendererProvider.Context context){
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(CustomFireball customFireball) {
        return TEXTURE;
    }

    @Override
    public void render(CustomFireball p_114485_, float p_114486_, float p_114487_, PoseStack p_114488_, MultiBufferSource p_114489_, int p_114490_) {
        super.render(p_114485_, p_114486_, p_114487_, p_114488_, p_114489_, p_114490_);
    }
}
