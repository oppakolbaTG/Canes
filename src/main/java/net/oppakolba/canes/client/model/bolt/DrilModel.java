package net.oppakolba.canes.client.model.bolt;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class DrilModel<T extends Entity> extends EntityModel<T> {

	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "drilmodel"), "main");
	private final ModelPart bone;

	public DrilModel(ModelPart root) {
		this.bone = root.getChild("bone");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition bone = partdefinition.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(18, 0).addBox(-7.5F, -13.0F, 2.0F, 7.0F, 7.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(18, 8).addBox(-7.1F, -12.5F, 3.0F, 6.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 33).addBox(-6.5F, -12.2F, 4.0F, 5.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(12, 35).addBox(-6.0F, -11.8F, 5.0F, 4.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(36, 24).addBox(-5.5F, -11.3F, 6.0F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(38, 37).addBox(-5.0F, -10.8F, 7.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(34, 39).addBox(-4.5F, -11.3F, 8.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 41).addBox(-4.5F, -9.3F, 8.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(4, 41).addBox(-4.5F, -10.3F, 9.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(12, 40).addBox(-5.5F, -10.3F, 8.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 40).addBox(-3.5F, -10.3F, 8.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(38, 40).addBox(-4.5F, -10.3F, 8.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-8.0F, -14.0F, 1.0F, 8.0F, 8.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(18, 29).addBox(-8.0F, -6.0F, 0.0F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 29).addBox(-8.0F, -15.0F, 0.0F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 9).addBox(-8.0F, -14.0F, 0.0F, 8.0F, 8.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(18, 31).addBox(-8.0F, -6.0F, -1.0F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 31).addBox(-8.0F, -15.0F, -1.0F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 18).addBox(-8.0F, -14.0F, -1.0F, 8.0F, 8.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 27).addBox(-8.0F, -15.0F, 1.0F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(18, 27).addBox(-8.0F, -6.0F, 1.0F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(22, 39).addBox(-5.0F, -11.8F, 7.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(28, 39).addBox(-5.0F, -8.8F, 7.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(22, 37).addBox(-5.5F, -12.3F, 6.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(30, 37).addBox(-5.5F, -8.3F, 6.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(36, 20).addBox(-6.0F, -12.8F, 5.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(36, 22).addBox(-6.0F, -7.8F, 5.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(34, 35).addBox(-6.5F, -13.2F, 4.0F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(36, 14).addBox(-6.5F, -7.2F, 4.0F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(34, 0).addBox(-7.1F, -6.5F, 3.0F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(34, 4).addBox(-7.1F, -13.5F, 3.0F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(32, 12).addBox(-7.5F, -6.0F, 2.0F, 7.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(12, 33).addBox(-7.5F, -14.0F, 2.0F, 7.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, 29.0F, -6.0F));

		PartDefinition cube_r1 = bone.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(32, 10).addBox(-3.5F, -0.5F, -0.5F, 7.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -9.5F, 2.5F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cube_r2 = bone.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(34, 2).addBox(-2.0F, -0.5F, -0.5F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.6F, -10.5F, 3.5F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cube_r3 = bone.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(22, 35).addBox(-1.0F, -0.5F, -0.5F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -11.2F, 4.5F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cube_r4 = bone.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(36, 18).addBox(0.0F, -0.5F, -0.5F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.5F, -11.8F, 5.5F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cube_r5 = bone.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(36, 30).addBox(1.0F, -0.5F, -0.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, -12.3F, 6.5F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cube_r6 = bone.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(6, 39).addBox(2.0F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.5F, -12.8F, 7.5F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cube_r7 = bone.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(0, 39).addBox(2.0F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.5F, -12.8F, 7.5F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cube_r8 = bone.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(36, 28).addBox(1.0F, -0.5F, -0.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.0F, -12.3F, 6.5F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cube_r9 = bone.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(36, 16).addBox(0.0F, -0.5F, -0.5F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.5F, -11.8F, 5.5F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cube_r10 = bone.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(34, 6).addBox(-1.0F, -0.5F, -0.5F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.0F, -11.2F, 4.5F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cube_r11 = bone.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(28, 33).addBox(-2.0F, -0.5F, -0.5F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.6F, -10.5F, 3.5F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cube_r12 = bone.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(32, 8).addBox(-3.0F, -0.5F, -0.5F, 7.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.0F, -10.0F, 2.5F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cube_r13 = bone.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(18, 17).addBox(-4.0F, -0.5F, -0.5F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(18, 23).addBox(-4.0F, -0.5F, -2.5F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(18, 19).addBox(-4.0F, -0.5F, -1.5F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.5F, -10.0F, 1.5F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cube_r14 = bone.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(18, 15).addBox(-4.0F, -0.5F, -0.5F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(18, 25).addBox(-4.0F, -0.5F, -2.5F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(18, 21).addBox(-4.0F, -0.5F, -1.5F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, -10.0F, 1.5F, 0.0F, 0.0F, 1.5708F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		bone.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}