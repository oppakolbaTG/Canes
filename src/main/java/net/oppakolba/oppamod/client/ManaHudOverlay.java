package net.oppakolba.oppamod.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.oppakolba.oppamod.Oppamod;
import net.oppakolba.oppamod.mana.PlayerMana;
import net.oppakolba.oppamod.mana.PlayerManaProvider;

public class ManaHudOverlay {
    public static final ResourceLocation MANA_TRAIT = new ResourceLocation(Oppamod.MOD_ID,
            "textures/mana/mana_trait.png");
    public static final ResourceLocation MANA_SHELL = new ResourceLocation(Oppamod.MOD_ID,
            "textures/mana/mana_shell.png");
    public static final ResourceLocation MANA_SHELL4 = new ResourceLocation(Oppamod.MOD_ID,
            "textures/mana/mana_shell4.png");
    public static final ResourceLocation MANA_SHELL3 = new ResourceLocation(Oppamod.MOD_ID,
            "textures/mana/mana_shell3.png");
    public static final ResourceLocation MANA_SHELL2 = new ResourceLocation(Oppamod.MOD_ID,
            "textures/mana/mana_shell2.png");
    public static final ResourceLocation MANA_SHELL1 = new ResourceLocation(Oppamod.MOD_ID,
            "textures/mana/mana_shell1.png");

    public static final IGuiOverlay HUD_MANA = ((gui, poseStack, partialTick, width, height) -> {
        PlayerMana mana = Minecraft.getInstance().player.getCapability(PlayerManaProvider.PLAYER_MANA).orElse(null);
        int shellX = width - 27; // 30 – ширина, 10 – отступ
        int shellY = height - 66;
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f,1.0f,1.0f);
        switch(mana.getMMana()){
            case 20:
                RenderSystem.setShaderTexture(0, MANA_SHELL1);
                GuiComponent.blit(poseStack, width - 27, height - 44, 0, 0 , 30, 45, 30, 45);
                break;
            case 40:
                RenderSystem.setShaderTexture(0, MANA_SHELL2);
                GuiComponent.blit(poseStack, width - 27, height - 56, 0, 0 , 30, 59, 30, 59);
                break;
            case 60:
                RenderSystem.setShaderTexture(0, MANA_SHELL3);
                GuiComponent.blit(poseStack, width - 27, height - 68, 0, 0 , 30, 73, 30, 73);
                break;
            case 80:
                RenderSystem.setShaderTexture(0, MANA_SHELL4);
                GuiComponent.blit(poseStack, width - 27, height - 64    , 0, 0 , 30, 79, 30, 70);
                break;
            case 100:
                RenderSystem.setShaderTexture(0, MANA_SHELL);
                GuiComponent.blit(poseStack, shellX, shellY, 0, 0 , 30, 71, 30, 71);
                break;
        }



        RenderSystem.setShaderTexture(0, MANA_TRAIT);

        for(int i = 0; i < mana.getMMana(); i += 4){
            int traitX = shellX - 5; // Смещаем немного вправо относительно оболочки
            int traitY = shellY + 48;
            if(ClientManaData.getPlayerMana() > i){
                GuiComponent.blit(poseStack, traitX, traitY - (i /4) * 2, 0, 0, 40, 22, 40, 22);
            }
            else{
                break;
            }
        }
    });
}
