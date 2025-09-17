package net.oppakolba.canes.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.oppakolba.canes.client.model.bolt.ParticleChargeModel;
import net.oppakolba.canes.entity.projectile.ParticleCharge;

@OnlyIn(Dist.CLIENT)
public class ParticleChargeRenderer extends EntityRenderer<ParticleCharge> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("oppamod", "textures/entity/particle_charge.png");
    private final ParticleChargeModel<ParticleCharge> model;

    public ParticleChargeRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
        this.model = new ParticleChargeModel<>(ParticleChargeModel.createBodyLayer().bakeRoot());
    }


    @Override
    public void render(ParticleCharge pEntity, float pEntityYaw, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        pPoseStack.pushPose();

        pPoseStack.translate(0.04, -1.42, -0.04);
        model.renderToBuffer(pPoseStack, pBuffer.getBuffer(RenderType.entityTranslucent(TEXTURE)), pPackedLight, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 1f);
        pPoseStack.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(ParticleCharge pEntity) {
        return TEXTURE;
    }
}
