package net.oppakolba.canes.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.player.LocalPlayer;
import net.oppakolba.canes.init.ModItems;
import org.jetbrains.annotations.NotNull;

public class CanesMenuScreen extends Screen {
    private static final ResourceLocation TEXTURE = new ResourceLocation("canes", "textures/gui/terra_menu_screen1.png");
    private static final ResourceLocation BUTTON_TEXTURE = new ResourceLocation("canes", "textures/gui/upg_button.png");
    private static final ResourceLocation LEVEL_1 = new ResourceLocation("canes", "textures/gui/level_1.png");
    private static final ResourceLocation LEVEL_2 = new ResourceLocation("canes", "textures/gui/level_2.png");
    private static final ResourceLocation LEVEL_3 = new ResourceLocation("canes", "textures/gui/level_3.png");
    private static final ResourceLocation LEVEL_4 = new ResourceLocation("canes", "textures/gui/level_4.png");
    private static final ResourceLocation LEVEL_5 = new ResourceLocation("canes", "textures/gui/level_5.png");
    private Button upgButton;
    private Button reloadbutton;



    public CanesMenuScreen(Component pTitle) {
        super(pTitle);
    }



    @Override
    protected void init() {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player != null) {

        }
    }


    @Override
    public void render(@NotNull PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        LocalPlayer player = Minecraft.getInstance().player;
        float scale = Math.min((float) this.width / 1920, (float) this.height / 1080);

        renderBackground(pPoseStack);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, TEXTURE);
        pPoseStack.pushPose();
        pPoseStack.scale(scale, scale, 1.0F);
        blit(pPoseStack, width / 2 - 170, height / 2 - 120,0, 0, 1920, 1080, 1920, 1080);
        pPoseStack.popPose();


        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);

//
    }

    @Override
    public void onClose() {
        super.onClose();
    }


    private boolean haveItems(LocalPlayer player) {
        return player.getInventory().countItem(ModItems.MANA_CRYSTAL.get()) >= 3;
    }
}
