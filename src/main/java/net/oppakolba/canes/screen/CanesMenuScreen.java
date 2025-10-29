package net.oppakolba.canes.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.oppakolba.canes.item.canes.*;
import net.oppakolba.canes.item.misc.CanesItem;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class CanesMenuScreen extends Screen {
    private static final ResourceLocation TEXTURES = new ResourceLocation("canes", "textures/gui/terra_menu_screen1.png");
    private static final ResourceLocation BUTTON_TEXTURE = new ResourceLocation("canes", "textures/gui/upg_button.png");
    private static final ResourceLocation LEVEL_1 = new ResourceLocation("canes", "textures/gui/level_1.png");
    private static final ResourceLocation LEVEL_2 = new ResourceLocation("canes", "textures/gui/level_2.png");
    private static final ResourceLocation LEVEL_3 = new ResourceLocation("canes", "textures/gui/level_3.png");
    private static final ResourceLocation LEVEL_4 = new ResourceLocation("canes", "textures/gui/level_4.png");
    private static final ResourceLocation LEVEL_5 = new ResourceLocation("canes", "textures/gui/level_5.png");
    private static final ResourceLocation FRAME_FOR_ITEM = new ResourceLocation("canes", "textures/gui/frame_for_item.png");
    private static final ResourceLocation FIREBALL_CANE = new ResourceLocation("canes", "textures/gui/fireball_cane.png");
    private static final ResourceLocation HEAL_CANE = new ResourceLocation("canes", "textures/gui/heal_cane.png");
    private static final ResourceLocation BEAM_CANE = new ResourceLocation("canes", "textures/gui/beam_cane.png");
    private static final ResourceLocation LIGHTNING_CANE = new ResourceLocation("canes", "textures/gui/beam_cane.png");
    private static final ResourceLocation RAIN_OF_CHAR = new ResourceLocation("canes", "textures/gui/rain_of_char.png");
    private static final ResourceLocation MANA_BAR = new ResourceLocation("canes", "textures/gui/mana_bar.png");
    private Button upgButton;
    private Button reloadbutton;
    protected int screenWidth;
    protected  int screenHeight;
    public int backgroundHeight = 148;
    public int backgroundWidth = 266;
    int color = 0x9b6c40;





    public CanesMenuScreen(Component pTitle) {
        super(pTitle);
    }


//logic
    @Override
    protected void init() {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player != null) {
            var stackItemInHand = player.getItemInHand(InteractionHand.MAIN_HAND);
            if(stackItemInHand.getItem() instanceof CanesItem) {

            }
        }
    }

    @Override
    public void render(@NotNull PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        this.screenWidth = this.minecraft.getWindow().getGuiScaledWidth();
        this.screenHeight = this.minecraft.getWindow().getGuiScaledHeight();
        int IconWidth = this.screenWidth / 2 - 99;
        int IconHeight = this.screenHeight / 2 - 40;


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
                int manaItem = getMana(stackItemInHand);


                if(stackItemInHand.getItem() instanceof FireballCane){
                    int k = getMana(stackItemInHand) == 20 ? 166 : 8 * getMana(stackItemInHand);

                    renderItemIcon(pPoseStack, FIREBALL_CANE);
                    renderManaBar(pPoseStack, stackItemInHand);

//                    pPoseStack.pushPose();
//                    pPoseStack.translate(this.screenWidth / 2 - 38, this.screenHeight / 2 - 4, 0);
//                    pPoseStack.scale(0.6f, 0.6f, 0.6f);
//                    drawCenteredString(pPoseStack, Minecraft.getInstance().font, Component.translatable("screen.damage"), 0, 0, color, false);
//                    pPoseStack.popPose();
                }


                else if (stackItemInHand.getItem() instanceof BeamCane){
                    renderManaBar(pPoseStack, stackItemInHand);
                    renderItemIcon(pPoseStack, BEAM_CANE);
                }
                else if (stackItemInHand.getItem() instanceof LightningCane){
                    renderManaBar(pPoseStack, stackItemInHand);
                    renderItemIcon(pPoseStack, LIGHTNING_CANE);
                }
                else if (stackItemInHand.getItem() instanceof HealCane){
                    renderManaBar(pPoseStack, stackItemInHand);
                    renderItemIcon(pPoseStack, HEAL_CANE);
                }
                else if (stackItemInHand.getItem() instanceof RainOfCharges){
                    renderManaBar(pPoseStack, stackItemInHand);
                    renderItemIcon(pPoseStack, RAIN_OF_CHAR);
                }

                else{
                    System.out.println("no its not");
                }
            }
        }




        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);

    }


    public static void drawCenteredString(PoseStack poseStack ,Font font, Component text, int x, int y, int color, boolean dropShadow) {
        FormattedCharSequence sequence = text.getVisualOrderText();

        drawString(poseStack ,font, sequence, x - font.width(sequence) / 2, y, color);
    }

    public static int getMana(ItemStack stack) {
        if (stack.getItem() instanceof CanesItem) {
            CompoundTag tag = stack.getOrCreateTag();
            return tag.getInt("mana");
        }
        return 0;
    }

    @Override
    public void onClose() {
        super.onClose();
    }


    public void renderManaBar(PoseStack pPoseStack, ItemStack itemStack){
        int k = getMana(itemStack) == 20 ? 166 : 8 * getMana(itemStack);
        RenderSystem.setShaderTexture(0, TEXTURES);
        blit(pPoseStack, this.screenWidth / 2 - 60, this.screenHeight / 2 - 39, 282, 16, k, 4, 512, 512);
    }

    public void renderItemIcon(PoseStack poseStack, ResourceLocation resLoc){
        RenderSystem.setShaderTexture(0, resLoc);
        blit(poseStack, this.screenWidth / 2 - 99, this.screenHeight / 2 - 40, 0, 0, 30, 30, 30,30);
    }

    public void renderItemDescription(PoseStack pPoseStack, String first, String second, String third, boolean dropShadow){
        Minecraft mc = Minecraft.getInstance();



        pPoseStack.pushPose();
        pPoseStack.translate(this.screenWidth / 2 - 18, this.screenHeight / 2 + 1 , 0);
        pPoseStack.scale(0.5f, 0.5f, 0.5f);
        drawCenteredString(pPoseStack, Minecraft.getInstance().font, Component.translatable(first), 0, 0, color, dropShadow);
        pPoseStack.popPose();
        pPoseStack.pushPose();
        pPoseStack.translate(this.screenWidth / 2 - 5, this.screenHeight / 2 + 5, 0);
        pPoseStack.scale(0.5f, 0.5f, 0.5f);
        drawCenteredString(pPoseStack, Minecraft.getInstance().font, Component.translatable(second), 0, 0, color, dropShadow);
        pPoseStack.popPose();
        pPoseStack.pushPose();
        pPoseStack.translate(this.screenWidth / 2 + 4, this.screenHeight / 2 + 9, 0);
        pPoseStack.scale(0.5f, 0.5f, 0.5f);
        drawCenteredString(pPoseStack, Minecraft.getInstance().font, Component.translatable(third), 0, 0, color, dropShadow);
        pPoseStack.popPose();

    }
}
