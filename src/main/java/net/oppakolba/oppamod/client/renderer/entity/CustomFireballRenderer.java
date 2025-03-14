package net.oppakolba.oppamod.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.oppakolba.oppamod.Oppamod;
import net.oppakolba.oppamod.client.model.bolt.CustomFireballModel;
import net.oppakolba.oppamod.entity.custom.CustomFireball;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class CustomFireballRenderer extends EntityRenderer<CustomFireball> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Oppamod.MOD_ID, "textures/entity/custom_fireball.png");
    private final CustomFireballModel<CustomFireball> model;

    public CustomFireballRenderer(EntityRendererProvider.Context context){
        super(context);
        this.model = new CustomFireballModel<>(CustomFireballModel.createBodyLayer().bakeRoot());
    }

    @Override
    public void render(CustomFireball entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.mulPose(Vector3f.YP.rotationDegrees(entity.tickCount + 15f));
        poseStack.mulPose(Vector3f.XP.rotationDegrees(entity.tickCount + 15f));
        poseStack.scale(3F, 3F, 3F);
        model.renderToBuffer(poseStack, buffer.getBuffer(RenderType.entityCutout(TEXTURE)), packedLight, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 1f);
    }


    
    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull CustomFireball entity) {
        return TEXTURE;
    }


}
