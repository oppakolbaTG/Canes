package net.oppakolba.mana_staffs.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.oppakolba.mana_staffs.entity.orbs.ManaOrb;


@OnlyIn(Dist.CLIENT)
public class ManaOrbRenderer extends EntityRenderer<ManaOrb> {
    private static final ResourceLocation MANA_ORB_LOCATION = new ResourceLocation("manastaffs", "textures/entity/mana_orb.png");
    private static final RenderType RENDER_TYPE;
    public ManaOrbRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
        this.shadowRadius = 0.13F;
        this.shadowStrength = 0.75F;
    }


    public int getBlockLightLevel(ManaOrb pEntity, BlockPos pPos) {
        return Mth.clamp(super.getBlockLightLevel(pEntity, pPos) + 7, 0, 15);
    }

    public void render(ManaOrb pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
        pMatrixStack.pushPose();


        float b = ((float)pEntity.tickCount + pPartialTicks) / 2.0f;
        int brightness = (int)(Mth.sin(b) * 0.5f +0.5f);
        pMatrixStack.translate(0.0D, (double)0.1F, 0.0D);
        pMatrixStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
        pMatrixStack.mulPose(Vector3f.YP.rotationDegrees(180.0F));
        pMatrixStack.scale(0.3F, 0.3F, 0.3F);
        VertexConsumer vertexconsumer = pBuffer.getBuffer(RENDER_TYPE);
        PoseStack.Pose pose = pMatrixStack.last();
        Matrix4f matrix4f = pose.pose();
        Matrix3f matrix3f = pose.normal();
        float u0 = 0.0F, u1 = 1.0F, v0 = 0.0F, v1 = 1.0F;
        int color = 245;
        vertex(vertexconsumer, matrix4f, matrix3f, -0.5F, -0.25F, color, color,color, u0, v1, pPackedLight);
        vertex(vertexconsumer, matrix4f, matrix3f, 0.5F, -0.25F, color,color,color, u1, v1, pPackedLight);
        vertex(vertexconsumer, matrix4f, matrix3f, 0.5F, 0.75F, color,color,color, u1, v0, pPackedLight);
        vertex(vertexconsumer, matrix4f, matrix3f, -0.5F, 0.75F, color,color,color, u0, v0, pPackedLight);


        pMatrixStack.popPose();
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }

    private static void vertex(VertexConsumer pBuffer, Matrix4f pMatrix, Matrix3f pMatrixNormal, float pX, float pY, int pRed, int pGreen, int pBlue, float pTexU, float pTexV, int pPackedLight) {
        pBuffer.vertex(pMatrix, pX, pY, 0.0F).color(pRed, pGreen, pBlue, 255).uv(pTexU, pTexV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(pPackedLight).normal(pMatrixNormal, 0.0F, 1.0F, 0.0F).endVertex();
    }

    @Override
    public ResourceLocation getTextureLocation(ManaOrb manaOrb) {
        return MANA_ORB_LOCATION;
    }

    static {
        RENDER_TYPE = RenderType.itemEntityTranslucentCull(MANA_ORB_LOCATION);
    }
}
