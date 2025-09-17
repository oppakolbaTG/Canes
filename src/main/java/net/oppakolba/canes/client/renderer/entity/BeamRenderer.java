package net.oppakolba.canes.client.renderer.entity;

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
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.oppakolba.canes.Canes;
import net.oppakolba.canes.client.model.bolt.BeamEntityModel;
import net.oppakolba.canes.entity.projectile.BeamEntity;



public class BeamRenderer extends EntityRenderer<BeamEntity> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Canes.MOD_ID, "textures/entity/beam_texture.png");
    private final BeamEntityModel<BeamEntity> model;


    public BeamRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
        this.model = new BeamEntityModel<>(BeamEntityModel.createBodyLayer().bakeRoot());

    }

    @Override
    public void render(BeamEntity entity, float pEntityYaw, float pPartialTick, PoseStack poseStack, MultiBufferSource buffer, int pPackedLight) {
        poseStack.pushPose();

        poseStack.mulPose(Vector3f.YP.rotationDegrees(180 - entity.getYRot()));
        poseStack.mulPose(Vector3f.XP.rotationDegrees(180 - entity.getXRot()));


        poseStack.scale(4.0f, 4.0f, 19.0f);
        poseStack.translate(0,-1.5f,0.7f);

        float startAlpha = 0.9f;
        float endAlpha = 0.3f;
        float averageAlpha = (startAlpha + endAlpha) / 2f;

        model.renderToBuffer(poseStack, buffer.getBuffer(RenderType.entityTranslucent(TEXTURE)), pPackedLight, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, averageAlpha);

        poseStack.popPose();
    }





    @Override
    public ResourceLocation getTextureLocation(BeamEntity pEntity) {
        return TEXTURE;
    }
}
