package net.oppakolba.oppamod.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.oppakolba.oppamod.Oppamod;
import net.oppakolba.oppamod.client.model.seal.FireBallSealModel;
import net.oppakolba.oppamod.entity.custom.FireballSeal;


@OnlyIn(Dist.CLIENT)
public class FireballSealRenderer extends EntityRenderer<FireballSeal> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Oppamod.MOD_ID, "textures/entity/fireball_seal.png");
    private final FireBallSealModel<FireballSeal> model;

    public FireballSealRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
        this.model = new FireBallSealModel<>(FireBallSealModel.createBodyLayer().bakeRoot());
    }


    @Override
    public void render(FireballSeal pEntity, float pEntityYaw, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
            pPoseStack.scale(10.0f, 10.0f, 10.0f);
            model.renderToBuffer(pPoseStack, pBuffer.getBuffer(RenderType.entityCutout(TEXTURE)), pPackedLight, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 1f);

        }


    @Override
    public ResourceLocation getTextureLocation(FireballSeal pEntity) {
        return TEXTURE;
    }
}
