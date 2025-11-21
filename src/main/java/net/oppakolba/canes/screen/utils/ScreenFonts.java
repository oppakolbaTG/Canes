package net.oppakolba.canes.screen.utils;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.math.Matrix4f;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.font.FontSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;

import java.util.function.Function;

public class ScreenFonts extends Font {
    public ScreenFonts(Function<ResourceLocation, FontSet> pFonts, boolean pFilterFishyGlyphs) {
        super(pFonts, pFilterFishyGlyphs);
    }

    public int drawShadow(PoseStack poseStack, FormattedCharSequence text, float x, float y, int color, int shadowColor) {
        Matrix4f matrix = poseStack.last().pose();
        MultiBufferSource.BufferSource buffer = MultiBufferSource.immediate(Tesselator.getInstance().getBuilder());

        this.drawInBatch(text, x + 1.0f,   y + 1.0f,shadowColor, false, matrix, buffer, false, 0, 15728880);


        int result = this.drawInBatch(text, x, y, color, false, matrix, buffer, false, 0, 15728880);

        buffer.endBatch();
        return result;
    }


    private int drawInternalm(FormattedCharSequence pReorderingProcessor, float pX, float pY, int pColor, Matrix4f pMatrix, boolean pDrawShadow) {
        MultiBufferSource.BufferSource $$6 = MultiBufferSource.immediate(Tesselator.getInstance().getBuilder());
        int $$7 = this.drawInBatch((FormattedCharSequence)pReorderingProcessor, pX, pY, pColor, pDrawShadow, pMatrix, $$6, false, 0x0e2ac74, 15728880);
        $$6.endBatch();
        return $$7;
    }
}
