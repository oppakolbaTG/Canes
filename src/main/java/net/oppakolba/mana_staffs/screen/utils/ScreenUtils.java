package net.oppakolba.mana_staffs.screen.utils;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.math.Matrix4f;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.font.FontSet;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;

import java.util.function.Function;

public class ScreenUtils{
    public ScreenUtils(Function<ResourceLocation, FontSet> pFonts, boolean pFilterFishyGlyphs) {
    }


    public static void drawCenteredString(PoseStack poseStack, Font font, Component text, float x, float y, int color, int shadowColor, boolean dropShadow) {
        FormattedCharSequence sequence = text.getVisualOrderText();
        drawString(poseStack ,font, sequence, x - font.width(sequence) / 2f, y, color, shadowColor, dropShadow);
    }

    public static void drawString(PoseStack pPoseStack, Font pFont, FormattedCharSequence pText, float pX, float pY, int pColor, int shadowColor, boolean pDrawShadow) {
        if (pDrawShadow && pFont instanceof ScreenFonts sf) {
            sf.drawShadow(pPoseStack, pText, pX, pY, pColor, shadowColor);
        } else if (pDrawShadow) {
            pFont.drawShadow(pPoseStack, pText, pX, pY, pColor);
        } else {
            pFont.draw(pPoseStack, pText, pX, pY, pColor);
        }
    }

    public static int drawColoredShadow(PoseStack poseStack, Font font, FormattedCharSequence text, float x, float y, int textColor, int shadowColor) {
        Matrix4f matrix = poseStack.last().pose();
        MultiBufferSource.BufferSource buffer = MultiBufferSource.immediate(Tesselator.getInstance().getBuilder());

        font.drawInBatch(text, x + 0.5f, y + 0.5f, shadowColor, false, matrix, buffer, false, 0, 15728880);
        int result = font.drawInBatch(text, x, y, textColor, false, matrix, buffer,false,0, 15728880);
        buffer.endBatch();
        return result;
    }

    public static int drawColoredShadow(PoseStack poseStack, Font font, Component text, float x, float y, int textColor, int shadowColor) {
        return drawColoredShadow(poseStack, font, text.getVisualOrderText(), x, y, textColor, shadowColor);
    }

    public static int drawCenteredColoredShadow(PoseStack poseStack, Font font, Component text, float x, float y, int textColor, int shadowColor) {
        FormattedCharSequence seq = text.getVisualOrderText();
        float textWidth = font.width(seq);
        return drawColoredShadow(poseStack, font, seq, x - textWidth / 2f, y, textColor, shadowColor);
    }
}