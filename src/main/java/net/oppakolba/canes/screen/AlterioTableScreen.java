package net.oppakolba.canes.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.oppakolba.canes.Canes;

public class AlterioTableScreen extends AbstractContainerScreen<AlterioTableMenu> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Canes.MOD_ID, "textures/gui/alterio_table_screen.png");

    public AlterioTableScreen(AlterioTableMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component);
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    protected void renderBg(PoseStack pstack, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        this.blit(pstack, x, y, 0, 0, imageWidth, imageHeight);

        renderProgressArrow(pstack, x, y);
    }

    private void renderProgressArrow(PoseStack pPoseStack, int x, int y){
        if(menu.isCrafting()){
            blit(pPoseStack, x + 124, y + 33, 176, 0, 8, menu.getScaledProgress());
        }
    }

    @Override
    public void render(PoseStack pPoseStack, int MouseX, int MouseY, float delta) {
        renderBackground(pPoseStack);
        super.render(pPoseStack, MouseX, MouseY, delta);
        renderTooltip(pPoseStack, MouseX, MouseY);
    }
}
