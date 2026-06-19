package net.oppakolba.canes.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class RequestWidget extends AbstractWidget {
    private final ResourceLocation Widget = new ResourceLocation("canes" ,"textures/gui/request_screen.png");
    private final ResourceLocation CHARGED_AMETHYST = new ResourceLocation("canes", "textures/item/charged_amethyst.png");
    private final ResourceLocation CHARGED_COPPER_INGOT = new ResourceLocation("canes", "textures/item/charged_copper_ingot.png");
    private final ResourceLocation FIRE_CHARGE = new ResourceLocation("minecraft", "textures/item/fire_charge.png");
    private int backgroundHeight = 49;
    private int backgroundWidth = 71;
    private final String itemInHand;

    public RequestWidget(int x, int y,String pMessage) {
        super(x,y, 266, 148, Component.empty());
        this.itemInHand = pMessage;
    }


    @Override
    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        int x = (this.width - backgroundWidth) / 2;
        int y = (this.height - backgroundHeight) / 2;
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, Widget);
        blit(pPoseStack,x + 100, y + 60, 0, 0, 49,71, 49, 71, 64, 128);
        switch (itemInHand){
            // case "FireBallCane" -> renderItem();
        }
    }


    private void renderItem(PoseStack pPoseStack ,ResourceLocation itemOne, ResourceLocation itemTwo, int x, int y){
        RenderSystem.setShaderTexture(0, CHARGED_AMETHYST);
        blit(pPoseStack,x, y, 0 ,0, 32,64, 256, 256, 32, 64);
    }

    @Override
    public void updateNarration(NarrationElementOutput pNarrationElementOutput) {

    }
}
