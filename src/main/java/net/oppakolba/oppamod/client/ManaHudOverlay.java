package net.oppakolba.oppamod.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.sun.jna.platform.win32.Guid;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.oppakolba.oppamod.Oppamod;

import java.util.ResourceBundle;

public class ManaHudOverlay {
    public static final ResourceLocation MANA_TRAIT = new ResourceLocation(Oppamod.MOD_ID,
            "textures/mana/mana_trait.png");
    public static final ResourceLocation MANA_SHELL = new ResourceLocation(Oppamod.MOD_ID,
            "textures/mana/mana_shell.png");

    public static final IGuiOverlay HUD_MANA = ((gui, poseStack, partialTick, width, height) -> {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f,1.0f,1.0f);
        RenderSystem.setShaderTexture(0, MANA_SHELL);
        GuiComponent.blit(poseStack, width - 335, 175, 0, 0 , 30, 71, 30, 71);


        RenderSystem.setShaderTexture(0, MANA_TRAIT);
        for(int i = 0; i < 100; i += 4){
            if(ClientManaData.getplayerMana() > i){
                GuiComponent.blit(poseStack, width - 340 , 223 - (i /4) * 2, 0, 0, 40, 22, 40, 22);
            }
            else{
                break;
            }
        }
    });
}
