//package net.oppakolba.oppamod.client.renderer.entity;
//
//import com.mojang.blaze3d.vertex.PoseStack;
//import net.minecraft.client.model.geom.ModelPart;
//import net.minecraft.client.renderer.MultiBufferSource;
//import net.minecraft.client.renderer.RenderType;
//import net.minecraft.client.renderer.entity.EntityRenderer;
//import net.minecraft.client.renderer.entity.EntityRendererProvider;
//import net.minecraft.core.BlockPos;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.util.Mth;
//import net.oppakolba.oppamod.client.model.misc.ManaOrbModel;
//import net.oppakolba.oppamod.entity.item.ManaOrb;
//
//public class ManaOrbRenderer extends EntityRenderer<ManaOrb> {
//    private static final ResourceLocation TEXTURE = new ResourceLocation("oppamod", "textures/entity/mana_orb.png");
//    private final ManaOrbModel<ManaOrb> model;
//
//    public ManaOrbRenderer(EntityRendererProvider.Context pContext) {
//        super(pContext);
//        this.model = new ManaOrbModel<>(ManaOrbModel.createBodyLayer().bakeRoot());
//        this.shadowRadius = 0.8f;
//        this.shadowStrength = 0.6f;
//    }
//
//    @Override
//    protected int getBlockLightLevel(ManaOrb pEntity, BlockPos pPos) {
//        return Mth.clamp(super.getBlockLightLevel(pEntity, pPos) + 7, 0 , 16);
//    }
//
//    @Override
//    public void render(ManaOrb pEntity, float pEntityYaw, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
//
//        super.render(pEntity, pEntityYaw, pPartialTick, pPoseStack, pBuffer, pPackedLight);
//    }
//
//    @Override
//    public ResourceLocation getTextureLocation(ManaOrb pEntity) {
//        return TEXTURE;
//    }
//}
