package net.oppakolba.oppamod.client.model.seal;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;


public class FireBallSealModel<T extends Entity> extends EntityModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "fireball_seal"), "main");
	private final ModelPart seal;

	public FireBallSealModel(ModelPart root) {
		this.seal = root.getChild("bone");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition bone = partdefinition.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(0, 12).addBox(-3.0F, 0.0F, 7.0F, 6.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(14, 11).addBox(-5.0F, 0.0F, 6.0F, 3.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(14, 12).addBox(2.0F, 0.0F, 6.0F, 3.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(14, 13).addBox(-5.0F, 0.0F, -7.0F, 3.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(8, 16).addBox(2.0F, 0.0F, -7.0F, 3.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 14).addBox(-7.0F, 0.0F, 2.0F, 1.0F, 0.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(14, 0).addBox(-7.0F, 0.0F, -5.0F, 1.0F, 0.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(14, 3).addBox(6.0F, 0.0F, 2.0F, 1.0F, 0.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(14, 6).addBox(6.0F, 0.0F, -5.0F, 1.0F, 0.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-8.0F, 0.0F, -3.0F, 1.0F, 0.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(0, 6).addBox(7.0F, 0.0F, -3.0F, 1.0F, 0.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(0, 13).addBox(-3.0F, 0.0F, -8.0F, 6.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 19).addBox(-6.0F, 0.0F, -6.0F, 2.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(4, 20).addBox(-6.0F, 0.0F, -5.0F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(12, 17).addBox(-6.0F, 0.0F, 4.0F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(18, 18).addBox(-6.0F, 0.0F, 5.0F, 2.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(6, 19).addBox(4.0F, 0.0F, -6.0F, 2.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(8, 20).addBox(5.0F, 0.0F, -5.0F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 14).addBox(5.0F, 0.0F, 4.0F, 1.0F, 0.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(12, 20).addBox(4.0F, 0.0F, 5.0F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 20).addBox(3.0F, 0.0F, 4.0F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(20, 20).addBox(2.0F, 0.0F, 3.0F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 22).addBox(3.0F, 0.0F, 2.0F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 16).addBox(4.0F, 0.0F, -2.0F, 1.0F, 0.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 20).addBox(-4.0F, 0.0F, 2.0F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(12, 18).addBox(-5.0F, 0.0F, 0.0F, 1.0F, 0.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(16, 22).addBox(6.0F, 0.0F, 1.0F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(22, 17).addBox(6.0F, 0.0F, -2.0F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(4, 23).addBox(5.0F, 0.0F, -1.4F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(22, 15).addBox(-6.0F, 0.0F, 0.4F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(12, 22).addBox(-7.25F, 1.0F, 1.05F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(22, 19).addBox(5.0F, 0.0F, 0.4F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(12, 23).addBox(-6.0F, 0.0F, -1.4F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(8, 23).addBox(-7.0F, 0.0F, -2.0F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(18, 19).addBox(-4.0F, 0.0F, -3.0F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(6, 17).addBox(-5.0F, 0.0F, -2.0F, 1.0F, 0.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 17).addBox(4.0F, 0.0F, 0.0F, 1.0F, 0.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(22, 0).addBox(3.0F, 0.0F, -3.0F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 21).addBox(2.0F, 0.0F, -4.0F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(4, 22).addBox(1.0F, 0.0F, -3.0F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(22, 4).addBox(1.0F, 0.0F, -5.0F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(22, 5).addBox(0.0F, 0.0F, -4.0F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(8, 14).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(14, 9).addBox(-1.0F, 0.0F, -7.0F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(8, 21).addBox(-3.0F, 0.0F, -4.0F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(22, 1).addBox(-2.0F, 0.0F, -3.0F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(22, 2).addBox(-1.0F, 0.0F, -4.0F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(22, 3).addBox(-2.0F, 0.0F, -5.0F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(12, 21).addBox(-3.0F, 0.0F, 3.0F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(22, 6).addBox(0.2F, 0.0F, 6.0F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(22, 7).addBox(-1.2F, 0.0F, 6.0F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(8, 22).addBox(-0.4F, 0.0F, 5.0F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(22, 8).addBox(-1.4F, 0.0F, 4.4F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(22, 9).addBox(0.6F, 0.0F, 4.4F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(22, 10).addBox(-0.4F, 0.0F, 4.4F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(22, 11).addBox(-0.4F, 0.0F, 3.4F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 21).addBox(-4.0F, 0.0F, -5.0F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(20, 21).addBox(-4.0F, 0.0F, 4.0F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(4, 21).addBox(3.0F, 0.0F, -5.0F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		seal.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}