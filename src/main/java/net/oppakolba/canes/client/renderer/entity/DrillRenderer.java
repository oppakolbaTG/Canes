//package net.oppakolba.canes.client.renderer.entity;
//
//import com.mojang.blaze3d.vertex.PoseStack;
//import com.mojang.math.Quaternion;
//import com.mojang.math.Vector3f;
//import net.minecraft.client.Minecraft;
//import net.minecraft.client.renderer.MultiBufferSource;
//import net.minecraft.client.renderer.RenderType;
//import net.minecraft.client.renderer.entity.EntityRenderer;
//import net.minecraft.client.renderer.entity.EntityRendererProvider;
//import net.minecraft.client.renderer.texture.OverlayTexture;
//import net.minecraft.resources.ResourceLocation;
//import net.oppakolba.canes.client.model.bolt.DrilModel;
//import net.oppakolba.canes.entity.projectile.DrillEntity;
//
//public class DrillRenderer extends EntityRenderer<DrillEntity> {
//    public static final ResourceLocation TEXTURE = new ResourceLocation("canes", "textures/entity/drill_texture.png");
//    private final DrilModel<DrillEntity> model;
//
//    public DrillRenderer(EntityRendererProvider.Context pContext) {
//        super(pContext);
//        this.model = new DrilModel<>(DrilModel.createBodyLayer().bakeRoot());
//    }
//
//    @Override
//    public void render(DrillEntity pEntity, float pEntityYaw, float pPartialTick, PoseStack pPoseStack, MultiBufferSource buffer, int pPackedLight) {
//        rotateModel(pPoseStack, pEntity);
//        model.renderToBuffer(pPoseStack, buffer.getBuffer(RenderType.entityCutout(TEXTURE)), pPackedLight, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 1f);
//    }
//
//    @Override
//    public ResourceLocation getTextureLocation(DrillEntity pEntity) {
//        return TEXTURE;
//    }
//
//    protected void rotateModel (PoseStack poseStack, DrillEntity entity){
//        Minecraft minecraft = Minecraft.getInstance();
//        if (minecraft.player != null){
//            double x = minecraft.player.getX() - entity.getX();
//            double z = minecraft.player.getZ() - entity.getZ();
//            float yaw = getCachedYaw(minecraft, entity);
//
//            poseStack.mulPose(Vector3f.YP.rotationDegrees(yaw));
//
//
//        }
//    }
//    private float cachedYaw = Float.NaN;
//
//    public float getCachedYaw(Minecraft mc, DrillEntity entity) {
//        if (Float.isNaN(cachedYaw) && mc.player != null) {
//            double dx = mc.player.getX() - entity.getX();
//            double dz = mc.player.getZ() - entity.getZ();
//            cachedYaw = (float) Math.toDegrees(Math.atan2(dx, dz));
//        }
//        return cachedYaw;
//    }
//
//
//
//}
