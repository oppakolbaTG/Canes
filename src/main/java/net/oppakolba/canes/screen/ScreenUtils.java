package net.oppakolba.canes.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.font.FontSet;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;

import java.util.function.Function;

public class ScreenUtils extends Font{
    public ScreenUtils(Function<ResourceLocation, FontSet> pFonts, boolean pFilterFishyGlyphs) {
        super(pFonts, pFilterFishyGlyphs);
    }

    public static void drawCenteredString(PoseStack poseStack, Font font, Component text, float x, float y, int color, boolean dropShadow) {
        FormattedCharSequence sequence = text.getVisualOrderText();
        drawString(poseStack ,font, sequence, x - font.width(sequence) / 2f, y, color, dropShadow);
    }

    public static void drawString(PoseStack pPoseStack, Font pFont, FormattedCharSequence pText, float pX, float pY, int pColor, boolean pDrawShadow) {
        if(pDrawShadow) {
            pFont.drawShadow(pPoseStack, pText, pX, pY, pColor);
        }else{
            pFont.draw(pPoseStack, pText, pX, pY, pColor);
        }
    }


}
