package net.oppakolba.canes.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.oppakolba.canes.item.canes.*;
import net.oppakolba.canes.item.misc.CanesItem;
import net.oppakolba.canes.networking.ModMessage;
import net.oppakolba.canes.networking.packet.UpgradeCharC2SPacket;
import net.oppakolba.canes.screen.utils.ScreenUtils;
import org.jetbrains.annotations.NotNull;

public class CanesMenuScreen extends Screen  {
    private static final ResourceLocation TEXTURES = new ResourceLocation("canes", "textures/gui/terra_menu_screen1.png");
    private static final ResourceLocation UPG_BUTTON = new ResourceLocation("canes", "textures/gui/buttonn_upg.png");
    protected int screenWidth;
    protected  int screenHeight;
    public int backgroundHeight = 148;
    public int backgroundWidth = 266;
    Font font = Minecraft.getInstance().font;
    int color = 0xcfa170;
    int shadowColor = 0xe7bf8b;


    public CanesMenuScreen(Component pTitle) {
        super(pTitle);
    }


//logic
    @Override
    protected void init() {
        int x = (this.width - backgroundWidth) / 2;
        int y = (this.height - backgroundHeight) / 2;
        LocalPlayer player = Minecraft.getInstance().player;


        if (player != null) {
            var stackItemInHand = player.getItemInHand(InteractionHand.MAIN_HAND);
            if(stackItemInHand.getItem() instanceof CanesItem) {
                Button upgButton = new ImageButton(x + 32, y + 78, 32, 32, 0, 0, 32, UPG_BUTTON, 32, 64, button -> {
                    if (stackItemInHand.getItem() instanceof FireballCane) {
                        ModMessage.sendToServer(new UpgradeCharC2SPacket("power", 1));
                    } else if (stackItemInHand.getItem() instanceof BeamCane) {
                        ModMessage.sendToServer(new UpgradeCharC2SPacket("power", 1));
                    } else if (stackItemInHand.getItem() instanceof LightningCane) {
                        ModMessage.sendToServer(new UpgradeCharC2SPacket("amt", 1));
                    } else if (stackItemInHand.getItem() instanceof HealCane) {
                        ModMessage.sendToServer(new UpgradeCharC2SPacket("radius", 1));
                        ModMessage.sendToServer(new UpgradeCharC2SPacket("heal", 1));
                    } else if (stackItemInHand.getItem() instanceof RainOfCharges) {
                        ModMessage.sendToServer(new UpgradeCharC2SPacket("power", 1));
                        ModMessage.sendToServer(new UpgradeCharC2SPacket("amt", 1));

                    } else {
                        System.out.println("no its not");
                    }
                });
                this.addRenderableWidget(upgButton);
            }
        }
    }

    @Override
    public void render(@NotNull PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        this.screenWidth = this.minecraft.getWindow().getGuiScaledWidth();
        this.screenHeight = this.minecraft.getWindow().getGuiScaledHeight();


        LocalPlayer player = Minecraft.getInstance().player;

        renderBackground(pPoseStack);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, TEXTURES);
        pPoseStack.pushPose();
        int texWidth = 512;
        int texHeight = 512;

        int x = (this.width - backgroundWidth) / 2;
        int y = (this.height - backgroundHeight) / 2;

        blit(pPoseStack, x, y, 0, 0, backgroundWidth, backgroundHeight, texWidth, texHeight);
        pPoseStack.popPose();
        if (player != null) {
            var stackItemInHand = player.getItemInHand(InteractionHand.MAIN_HAND);
            if(stackItemInHand.getItem() instanceof CanesItem) {
                if(stackItemInHand.getItem() instanceof FireballCane){
                    this.addRenderableWidget(new RequestWidget(x + 103 , y + 103,"FireBallCane"));
                    renderManaBar(pPoseStack, stackItemInHand);
                    renderIconCharacteristics(pPoseStack, 298, 75, 314, 75, true);
                    ScreenUtils.drawColoredShadow(pPoseStack, font, Component.translatable("screen.power").append(": ").append(String.valueOf(2 + CanesItem.getPower(stackItemInHand) * 2)),
                            x + 103, y + 80, color, shadowColor);
                    ScreenUtils.drawColoredShadow(pPoseStack, font, Component.translatable("screen.radius").append(": ").append(String.valueOf(1 + CanesItem.getPower(stackItemInHand) * 2)),
                            x + 103, y + 103, color, shadowColor);
                    renderLevel(pPoseStack, stackItemInHand,"power");
                }


                else if (stackItemInHand.getItem() instanceof BeamCane){
                    renderManaBar(pPoseStack, stackItemInHand);
                    renderIconCharacteristics(pPoseStack, 298, 75, 0, 0,false);
                    ScreenUtils.drawColoredShadow(pPoseStack, font, Component.translatable("screen.power").append(": ").append(String.valueOf(1 + CanesItem.getPower(stackItemInHand) * 2)),
                            x + 103, y + 80, color,shadowColor);
                    renderLevel(pPoseStack, stackItemInHand,"power");
                }
                else if (stackItemInHand.getItem() instanceof LightningCane){
                    System.out.println(CanesItem.getAmt(stackItemInHand));
                    renderManaBar(pPoseStack, stackItemInHand);
                    renderIconCharacteristics(pPoseStack, 330, 75, 0,0,false);
                    ScreenUtils.drawColoredShadow(pPoseStack, font, Component.translatable("screen.amt").append(": ").append(String.valueOf(1 + CanesItem.getAmt(stackItemInHand) * 2)),
                            x + 103, y + 80, color, shadowColor);
                    renderLevel(pPoseStack, stackItemInHand,"amt");
                }
                else if (stackItemInHand.getItem() instanceof HealCane){
                    renderManaBar(pPoseStack, stackItemInHand);
                    renderIconCharacteristics(pPoseStack, 314, 75, 346, 75, true);
                    ScreenUtils.drawColoredShadow(pPoseStack, font, Component.translatable("screen.radius").append(": ").append(String.valueOf(1 + CanesItem.getRadius(stackItemInHand) * 2)),
                            x + 103, y + 80, color, shadowColor);
                    ScreenUtils .drawColoredShadow(pPoseStack, font, Component.translatable("screen.heal").append(": ").append(String.valueOf(CanesItem.getHeal(stackItemInHand) * 2)),
                            x + 103, y + 103, color, shadowColor);
                    renderLevel(pPoseStack, stackItemInHand,"radius");
                }
                else if (stackItemInHand.getItem() instanceof RainOfCharges){
                    System.out.println(CanesItem.getRadius(stackItemInHand));
                    System.out.println(CanesItem.getAmt(stackItemInHand));
                    renderManaBar(pPoseStack, stackItemInHand);
                    renderIconCharacteristics(pPoseStack, 298, 75, 330, 75, true);
                    ScreenUtils.drawColoredShadow(pPoseStack, font, Component.translatable("screen.power").append(": ").append(String.valueOf(2 + CanesItem.getPower(stackItemInHand) * 2)),
                            x + 103, y + 80, color, shadowColor);
                    ScreenUtils.drawColoredShadow(pPoseStack, font, Component.translatable("screen.amt").append(": ").append(String.valueOf(1 + CanesItem.getAmt(stackItemInHand) * 2)),
                            x + 103, y + 103, color,shadowColor);
                    renderLevel(pPoseStack, stackItemInHand,"power");
                }

                else{
                    System.out.println("no its not");
                }
            }
        }




        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);

    }


    @Override
    public void onClose() {
        super.onClose();
    }


    public void renderManaBar(PoseStack pPoseStack, ItemStack itemStack){
        int k = CanesItem.getMana(itemStack) == 20 ? 166 : 8 * CanesItem.getMana(itemStack);
        RenderSystem.setShaderTexture(0, TEXTURES);
        blit(pPoseStack, this.screenWidth / 2 - 60, this.screenHeight / 2 - 39, 282, 16, k, 4, 512, 512);
    }

    public void renderItemIcon(PoseStack poseStack, ResourceLocation resLoc){
        RenderSystem.setShaderTexture(0, resLoc);
        blit(poseStack, this.screenWidth / 2 - 99, this.screenHeight / 2 - 40, 0, 0, 30, 30, 30,30);
    }



    /**if param renderSecondItem is false 5 and 6 are not taken into account**/
    public void renderIconCharacteristics(PoseStack poseStack, int uOffset, int vOffset, int u2Offset, int v2Offset, boolean renderSecondIcon) {

        RenderSystem.setShaderTexture(0, TEXTURES);
        blit(poseStack, this.screenWidth / 2 - 50, this.screenHeight / 2 + 2, uOffset, vOffset, 15, 15, 512, 512);
        if (renderSecondIcon) {
            blit(poseStack, this.screenWidth / 2 - 50, this.screenHeight / 2 + 25, u2Offset, v2Offset, 15, 15, 512, 512);
        }
    }

    public void renderLevel(PoseStack poseStack, ItemStack stack, String characteristics){
        if(characteristics.equals("power")){
            switch(CanesItem.getPower(stack)){
                case 1 -> renderOne(poseStack);
                case 2 -> renderTwo(poseStack);
                case 3 -> renderThree(poseStack);
                case 4 -> renderFour(poseStack);
                case 5 -> renderFive(poseStack);
            }
        }
        if(characteristics.equals("radius")){
            switch(CanesItem.getRadius(stack)){
                case 1 -> renderOne(poseStack);
                case 2 -> renderTwo(poseStack);
                case 3 -> renderThree(poseStack);
                case 4 -> renderFour(poseStack);
                case 5 -> renderFive(poseStack);
            }
        }
        if(characteristics.equals("amt")){
            switch(CanesItem.getAmt(stack)){
                case 1 -> renderOne(poseStack);
                case 2 -> renderTwo(poseStack);
                case 3 -> renderThree(poseStack);
                case 4 -> renderFour(poseStack);
                case 5 -> renderFive(poseStack);
            }
        }
        if(characteristics.equals("heal")){
            switch(CanesItem.getHeal(stack)){
                case 1 -> renderOne(poseStack);
                case 2 -> renderTwo(poseStack);
                case 3 -> renderThree(poseStack);
                case 4 -> renderFour(poseStack);
                case 5 -> renderFive(poseStack);
            }
        }
    }

    private void renderOne(PoseStack poseStack){
        RenderSystem.setShaderTexture(0, TEXTURES);
        blit(poseStack, this.screenWidth / 2 - 96, this.screenHeight / 2 - 35,368,38, 22,22, 512, 512);
    }
    private void renderTwo(PoseStack poseStack){
        RenderSystem.setShaderTexture(0, TEXTURES);
        blit(poseStack, this.screenWidth / 2 - 93, this.screenHeight / 2 - 35,390,38, 17,22,512, 512);
    }
    private void renderThree(PoseStack poseStack){
        RenderSystem.setShaderTexture(0, TEXTURES);
        blit(poseStack, this.screenWidth / 2 - 98, this.screenHeight / 2 - 35,414,38, 26,22,512, 512);

    }
    private void renderFour(PoseStack poseStack){
        RenderSystem.setShaderTexture(0, TEXTURES);
        blit(poseStack, this.screenWidth / 2 - 96, this.screenHeight / 2 - 35,447,38, 24,22,512, 512);

    }
    private void renderFive(PoseStack poseStack){
        RenderSystem.setShaderTexture(0, TEXTURES);
        blit(poseStack, this.screenWidth / 2 - 93, this.screenHeight / 2 - 35,478,38, 15,22,512, 512);

    }
}
