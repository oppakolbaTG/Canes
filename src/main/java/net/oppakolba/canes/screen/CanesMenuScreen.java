package net.oppakolba.canes.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
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

public class CanesMenuScreen extends Screen  {
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
    private static final ResourceLocation LIGHTNING_CANE = new ResourceLocation("canes", "textures/gui/lightning_cane.png");
    private static final ResourceLocation RAIN_OF_CHAR = new ResourceLocation("canes", "textures/gui/rain_of_char.png");
    private Button upgButton;
    private Button reloadbutton;
    protected int screenWidth;
    protected  int screenHeight;
    public int backgroundHeight = 148;
    public int backgroundWidth = 266;
    Font font = Minecraft.getInstance().font;
    MultiBufferSource.BufferSource bufferSource = MultiBufferSource.immediate(Tesselator.getInstance().getBuilder());

    int blowColor = 0xdc4311;
    int healColor =  0xd61818;
    int amtColor = 0x04d706;
    int powerColor = 0x3aaec3;


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
                    renderManaBar(pPoseStack, stackItemInHand);
                    renderCharacteristics(pPoseStack, 298, 75, 314, 75, true);
                    ScreenUtils.drawCenteredString(pPoseStack, font, Component.translatable("screen.power").append(": ").append(String.valueOf(2 + getPower(stackItemInHand) * 2)),
                            x + 120, y + 80, powerColor, false);
                    ScreenUtils .drawCenteredString(pPoseStack, font, Component.translatable("screen.radius").append(": ").append(String.valueOf(getPower(stackItemInHand) * 2)),
                            x + 148, y + 103, blowColor, false);
                }


                else if (stackItemInHand.getItem() instanceof BeamCane){
                    renderManaBar(pPoseStack, stackItemInHand);
                    renderCharacteristics(pPoseStack, 298, 75, 0, 0,false);
                    ScreenUtils.drawCenteredString(pPoseStack, font, Component.translatable("screen.power").append(": ").append(String.valueOf(1 + getPower(stackItemInHand) * 2)),
                            x + 120, y + 80, powerColor, false);
                }
                else if (stackItemInHand.getItem() instanceof LightningCane){
                    System.out.println(getAmt(stackItemInHand));
                    renderManaBar(pPoseStack, stackItemInHand);
                    renderCharacteristics(pPoseStack, 330, 75, 0,0,false);
                    ScreenUtils.drawCenteredString(pPoseStack, font, Component.translatable("screen.amt").append(": ").append(String.valueOf(1 + getPower(stackItemInHand) * 2)),
                            x + 120, y + 80, amtColor, false);
                }
                else if (stackItemInHand.getItem() instanceof HealCane){
                    renderManaBar(pPoseStack, stackItemInHand);
                    renderCharacteristics(pPoseStack, 314, 75, 346, 75, true);
                    ScreenUtils.drawCenteredString(pPoseStack, font, Component.translatable("screen.radius").append(": ").append(String.valueOf(1 + getPower(stackItemInHand) * 2)),
                            x + 120, y + 80, blowColor, false);
                    ScreenUtils .drawCenteredString(pPoseStack, font, Component.translatable("screen.heal").append(": ").append(String.valueOf(getPower(stackItemInHand) * 2)),
                            x + 148, y + 103, healColor, false);
                }
                else if (stackItemInHand.getItem() instanceof RainOfCharges){
                    System.out.println(getRadius(stackItemInHand));
                    System.out.println(getAmt(stackItemInHand));
                    renderManaBar(pPoseStack, stackItemInHand);
                    renderCharacteristics(pPoseStack, 298, 75, 330, 75, true);
                    ScreenUtils.drawCenteredString(pPoseStack, font, Component.translatable("screen.power").append(": ").append(String.valueOf(2 + getPower(stackItemInHand) * 2)),
                            x + 120, y + 80, powerColor, false);
                    ScreenUtils.drawCenteredString(pPoseStack, font, Component.translatable("screen.amt").append(": ").append(String.valueOf(1 + getPower(stackItemInHand) * 2)),
                            x + 148, y + 103, amtColor, false);
                }

                else{
                    System.out.println("no its not");
                }
            }
        }




        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);

    }


    public static int getMana(ItemStack stack) {
        if (stack.getItem() instanceof CanesItem) {
            CompoundTag tag = stack.getOrCreateTag();
            return tag.getInt("mana");
        }
        return 0;
    }

    public static int getPower(ItemStack stack){
        if(stack.getItem() instanceof  CanesItem){
            CompoundTag tag = stack.getOrCreateTag();
            return tag.getInt("power");
        }
        return 0;
    }
    public static int getRadius(ItemStack stack){
        if(stack.getItem() instanceof  CanesItem){
            CompoundTag tag = stack.getOrCreateTag();
            return tag.getInt("radius");
        }
        return 0;
    }
    public static int getHeal(ItemStack stack){
        if(stack.getItem() instanceof  CanesItem){
            CompoundTag tag = stack.getOrCreateTag();
            return tag.getInt("heal");
        }
        return 0;
    }
    public static int getAmt(ItemStack stack){
        if(stack.getItem() instanceof  CanesItem){
            CompoundTag tag = stack.getOrCreateTag();
            return tag.getInt("amt");
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



    /**if param renderSecondItem is false 5 and 6 are not taken into account**/
    public void renderCharacteristics(PoseStack poseStack, int uOffset, int vOffset, int u2Offset, int v2Offset, boolean renderSecondIcon){

        RenderSystem.setShaderTexture(0, TEXTURES);
        blit(poseStack, this.screenWidth / 2 - 50, this.screenHeight / 2 + 2, uOffset, vOffset, 15 , 15, 512, 512);
        if(renderSecondIcon){
            blit(poseStack, this.screenWidth / 2 - 50, this.screenHeight / 2 + 25, u2Offset, v2Offset, 15 , 15, 512, 512);
        }
    }
}
