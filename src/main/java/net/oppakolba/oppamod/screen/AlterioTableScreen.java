package net.oppakolba.oppamod.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.oppakolba.oppamod.Oppamod;

public class AlterioTableScreen extends AbstractContainerScreen<AlterioTableMenu> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Oppamod.MOD_ID, "textures/gui/alterionating_table_screen.png");

    public AlterioTableScreen(AlterioTableMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component);
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    protected void renderBg(PoseStack stack, float partialTick, int MouseX, int MouseY) {
        RenderSystem.setShader(GameRenderer::getPositionShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        this.blit(stack, x, y, 0, 0, imageWidth, imageHeight);

        renderProgressArrow(stack, x, y);
    }

    private void renderProgressArrow(PoseStack poseStack, int x, int y){
        if(menu.isCrafting()){
            blit(poseStack, x + 105, y + 33, 176, 0, 8, menu.getScaledProgress());
        }
    }

    @Override
    public void render(PoseStack stack, int MouseX, int MouseY, float delta) {
        renderBackground(stack);
        super.render(stack, MouseX, MouseY, delta);
        renderTooltip(stack, MouseX, MouseY);
    }
}
