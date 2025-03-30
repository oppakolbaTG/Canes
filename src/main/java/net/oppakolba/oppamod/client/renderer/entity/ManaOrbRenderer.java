package net.oppakolba.oppamod.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.oppakolba.oppamod.client.model.misc.ManaOrbModel;
import net.oppakolba.oppamod.entity.item.ManaOrb;
import org.jetbrains.annotations.NotNull;
@OnlyIn(Dist.CLIENT)
public class ManaOrbRenderer extends EntityRenderer<ManaOrb> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("oppamod", "textures/entity/mana_orb.png");
    private final ManaOrbModel<ManaOrb> model;

    public ManaOrbRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
        this.model = new ManaOrbModel<>(ManaOrbModel.createBodyLayer().bakeRoot());
        this.shadowRadius = 0.1f;
        this.shadowStrength = 0.5f;
    }

    @Override
    protected int getBlockLightLevel(ManaOrb pEntity, BlockPos pPos) {
        return Mth.clamp(super.getBlockLightLevel(pEntity, pPos) + 7, 0 , 16);
    }

    @Override
    public void render(ManaOrb pEntity, float pEntityYaw, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        pPoseStack.pushPose();
        pPoseStack.scale(1.5f, 1.5f, 1.5f);
        pPoseStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
        pPoseStack.mulPose(Vector3f.YP.rotationDegrees(180F));
        pPoseStack.mulPose(Vector3f.XP.rotationDegrees(180F));
        model.renderToBuffer(pPoseStack, pBuffer.getBuffer(RenderType.entityCutout(TEXTURE)), pPackedLight, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 1f);
        pPoseStack.popPose();
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(ManaOrb pEntity) {
        return TEXTURE;
    }
}
