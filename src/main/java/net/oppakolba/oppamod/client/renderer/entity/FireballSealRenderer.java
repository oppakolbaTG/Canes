package net.oppakolba.oppamod.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
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
import net.oppakolba.oppamod.Oppamod;
import net.oppakolba.oppamod.client.model.seal.FireBallSealModel;
import net.oppakolba.oppamod.entity.custom.FireballSeal;
import org.jetbrains.annotations.NotNull;


@OnlyIn(Dist.CLIENT)
public class FireballSealRenderer extends EntityRenderer<FireballSeal> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Oppamod.MOD_ID, "textures/entity/fireball_seal.png");
    private final FireBallSealModel<FireballSeal> model;

    public FireballSealRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
        this.model = new FireBallSealModel<>(FireBallSealModel.createBodyLayer().bakeRoot());
    }

    @Override
    protected int getBlockLightLevel(@NotNull FireballSeal pEntity, @NotNull BlockPos pPos) {
        return Mth.clamp(super.getBlockLightLevel(pEntity, pPos) + 7, 0, 15);
    }

    @Override
    public void render(@NotNull FireballSeal pEntity, float pEntityYaw, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
            pPoseStack.scale(10.0f, 10.0f, 10.0f);
            model.renderToBuffer(pPoseStack, pBuffer.getBuffer(RenderType.entityCutout(TEXTURE)), pPackedLight, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 1f);

        }


    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull FireballSeal pEntity) {
        return TEXTURE;
    }
}
