package net.oppakolba.canes.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.oppakolba.canes.item.canes.*;
import net.oppakolba.canes.item.misc.CanesItem;
import net.oppakolba.canes.networking.CaneUpgradeRequirements;
import net.oppakolba.canes.networking.ModMessage;
import net.oppakolba.canes.networking.packet.UpgradeCharC2SPacket;
import net.oppakolba.canes.screen.utils.ScreenUtils;
import org.jetbrains.annotations.NotNull;

public class CanesMenuScreen extends Screen  {
    private static final ResourceLocation TEXTURES = new ResourceLocation("canes", "textures/gui/terra_menu_screen1.png");
    private static final ResourceLocation UPG_BUTTON = new ResourceLocation("canes", "textures/gui/buttonn_upg.png");
    private static final ResourceLocation REQUIREMENTS = new ResourceLocation("canes", "textures/gui/requirements.png");
    protected int screenWidth;
    protected  int screenHeight;
    public int backgroundHeight = 148;
    public int backgroundWidth = 266;
    Font font = Minecraft.getInstance().font;
    int color = 0xcfa170;
    int shadowColor = 0xe7bf8b;
    private ImageButton upgButton;
    private String caneCode;
    private static final String MANA_TAG = "mana";
    private static final String MAX_MANA_TAG = "max_mana";


    public CanesMenuScreen(Component pTitle) {
        super(pTitle);
    }


    @Override
    protected void init() {
        int x = (this.width - backgroundWidth) / 2;
        int y = (this.height - backgroundHeight) / 2;
        LocalPlayer player = Minecraft.getInstance().player;

        if (player == null) return;
        ItemStack itemStack = player.getItemInHand(InteractionHand.MAIN_HAND);
        caneCode = CaneUpgradeRequirements.resolveCaneCode(itemStack.getItem());
        if (caneCode == null){
            upgButton = null;
            return;
        }
        upgButton = new ImageButton(x + 32, y + 78, 32, 32, 0, 0, 32, UPG_BUTTON, 32, 64,  button -> {
            ModMessage.sendToServer(new UpgradeCharC2SPacket(caneCode, 1));
        });
        this.addRenderableWidget(upgButton);
        updateUpgradeButtonState(player);


    }


    @Override
    public void tick() {
        super.tick();
        LocalPlayer localPlayer = Minecraft.getInstance().player;
        if(localPlayer != null) updateUpgradeButtonState(localPlayer);
    }

    @Override
    public void render(@NotNull PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        this.screenWidth = this.minecraft.getWindow().getGuiScaledWidth();
        this.screenHeight = this.minecraft.getWindow().getGuiScaledHeight();


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
                if(stackItemInHand.getItem() instanceof FireballCane){
                    renderManaBar(pPoseStack, stackItemInHand);
                    renderIconCharacteristics(pPoseStack, 298, 75, 314, 75, true);
                    ScreenUtils.drawColoredShadow(pPoseStack, font, Component.translatable("screen.power").append(": ").append(String.valueOf(2 + CanesItem.getPower(stackItemInHand) * 2)),
                            x + 103, y + 80, color, shadowColor);
                    ScreenUtils.drawColoredShadow(pPoseStack, font, Component.translatable("screen.radius").append(": ").append(String.valueOf(1 + CanesItem.getPower(stackItemInHand) * 2)),
                            x + 103, y + 103, color, shadowColor);
                    renderLevel(pPoseStack, stackItemInHand,"power");
                    renderRequirementsForLvlUp("fc", pPoseStack, x ,y, CanesItem.getPower(stackItemInHand));
                }
                else if (stackItemInHand.getItem() instanceof BeamCane){
                    renderManaBar(pPoseStack, stackItemInHand);
                    renderIconCharacteristics(pPoseStack, 298, 75, 0, 0,false);
                    ScreenUtils.drawColoredShadow(pPoseStack, font, Component.translatable("screen.power").append(": ").append(String.valueOf(1 + CanesItem.getPower(stackItemInHand) * 2)),
                            x + 103, y + 80, color,shadowColor);
                    renderLevel(pPoseStack, stackItemInHand,"power");
                    renderRequirementsForLvlUp("bc", pPoseStack, x ,y, CanesItem.getPower(stackItemInHand));
                }
                else if (stackItemInHand.getItem() instanceof LightningCane){
                    renderManaBar(pPoseStack, stackItemInHand);
                    renderIconCharacteristics(pPoseStack, 330, 75, 0,0,false);
                    ScreenUtils.drawColoredShadow(pPoseStack, font, Component.translatable("screen.amt").append(": ").append(String.valueOf(1 + CanesItem.getAmt(stackItemInHand) * 2)),
                            x + 103, y + 80, color, shadowColor);
                    renderLevel(pPoseStack, stackItemInHand,"amt");
                    renderRequirementsForLvlUp("lc", pPoseStack, x ,y, CanesItem.getAmt(stackItemInHand));
                }
                else if (stackItemInHand.getItem() instanceof HealCane){
                    renderManaBar(pPoseStack, stackItemInHand);
                    renderIconCharacteristics(pPoseStack, 314, 75, 346, 75, true);
                    ScreenUtils.drawColoredShadow(pPoseStack, font, Component.translatable("screen.radius").append(": ").append(String.valueOf(1 + CanesItem.getRadius(stackItemInHand) * 2)),
                            x + 103, y + 80, color, shadowColor);
                    ScreenUtils .drawColoredShadow(pPoseStack, font, Component.translatable("screen.heal").append(": ").append(String.valueOf(CanesItem.getHeal(stackItemInHand) * 2)),
                            x + 103, y + 103, color, shadowColor);
                    renderLevel(pPoseStack, stackItemInHand,"radius");
                    renderRequirementsForLvlUp("hc", pPoseStack, x ,y, CanesItem.getRadius(stackItemInHand));
                }
                else if (stackItemInHand.getItem() instanceof RainOfCharges){
                    renderManaBar(pPoseStack, stackItemInHand);
                    renderIconCharacteristics(pPoseStack, 298, 75, 330, 75, true);
                    ScreenUtils.drawColoredShadow(pPoseStack, font, Component.translatable("screen.power").append(": ").append(String.valueOf(2 + CanesItem.getPower(stackItemInHand) * 2)),
                            x + 103, y + 80, color, shadowColor);
                    ScreenUtils.drawColoredShadow(pPoseStack, font, Component.translatable("screen.amt").append(": ").append(String.valueOf(1 + CanesItem.getAmt(stackItemInHand) * 2)),
                            x + 103, y + 103, color,shadowColor);
                    renderLevel(pPoseStack, stackItemInHand,"power");
                    renderRequirementsForLvlUp("roc",pPoseStack ,x ,y ,CanesItem.getPower(stackItemInHand));
                }
            }
        }
        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);

    }


    @Override
    public void onClose() {
        super.onClose();
    }


    private void updateUpgradeButtonState(LocalPlayer player) {
        if (upgButton == null) return;
        ItemStack stack = player.getItemInHand(InteractionHand.MAIN_HAND);
        String cCode = CaneUpgradeRequirements.resolveCaneCode(stack.getItem());
        if (!caneCode.equals(cCode)) {
            this.init(this.minecraft, this.width, this.height);
            return;
        }

        int level = CaneUpgradeRequirements.primaryLevel(caneCode, stack);
        boolean canUpgrade = level < CaneUpgradeRequirements.MAX_LEVEL
                && CaneUpgradeRequirements.hasRequiredItem(player, caneCode, level);
        upgButton.visible = canUpgrade;
        upgButton.active = canUpgrade;
    }
    /**render**/
    public void renderRequirementsForLvlUp(String item, PoseStack poseStack, int x, int y, int lvl){
        RenderSystem.setShaderTexture(0, REQUIREMENTS);
        switch(item){
            case "fc" -> {
                blit(poseStack, x + 200, y + 5, 0, 0, 10, 8, 79, 8);
                blit(poseStack, x + 220, y + 5, 13, 0, 10, 8, 79, 8);
            }
            case "bc" -> {
                blit(poseStack, x + 200, y + 5,0, 0, 10, 8, 79, 8);
                blit(poseStack, x + 220, y + 5, 40, 0, 10, 8, 79, 8);
            }
            case "hc" -> {
                blit(poseStack, x + 200, y + 5, 0, 0, 10, 8, 79, 8);
                blit(poseStack, x + 220, y + 5, 27, 0, 10, 8, 79, 8);
            }
            case "lc" -> {
                blit(poseStack, x + 200, y + 5, 0, 0, 10, 8, 79, 8);
                blit(poseStack, x + 219, y + 5, 53, 0, 10, 8, 79, 8);
            }
            case "roc" -> {
                blit(poseStack, x + 200, y + 5, 0, 0, 10, 8, 79, 8);
                blit(poseStack, x + 220, y + 5, 68, 0, 10, 8, 79, 8);
            }
        }
        switch (lvl) {
            case 1 -> {
                ScreenUtils.drawColoredShadow(poseStack, font, Component.translatable(":1"),x + 230, y + 5, color, shadowColor);
                ScreenUtils.drawColoredShadow(poseStack, font, Component.translatable(":1"),x + 209, y + 5, color, shadowColor);
            }
            case 2 -> {
                ScreenUtils.drawColoredShadow(poseStack, font, Component.translatable(":1"),x + 230, y + 5, color, shadowColor);
                ScreenUtils.drawColoredShadow(poseStack, font, Component.translatable(":2"),x + 209, y + 5, color, shadowColor);
            }
            case 3 -> {
                ScreenUtils.drawColoredShadow(poseStack, font, Component.translatable(":2"),x + 230, y + 5, color, shadowColor);
                ScreenUtils.drawColoredShadow(poseStack, font, Component.translatable(":2"),x + 209, y + 5, color, shadowColor);
            }
            case 4 -> {
                ScreenUtils.drawColoredShadow(poseStack, font, Component.translatable(":2"),x + 230, y + 5, color, shadowColor);
                ScreenUtils.drawColoredShadow(poseStack, font, Component.translatable(":3"),x + 209, y + 5, color, shadowColor);
            }
            case 5 -> {
                ScreenUtils.drawColoredShadow(poseStack, font, Component.translatable(":3"),x + 230, y + 5, color, shadowColor);
                ScreenUtils.drawColoredShadow(poseStack, font, Component.translatable(":3"),x + 209, y + 5, color, shadowColor);
            }
        }

    }
    public void renderIconCharacteristics(PoseStack poseStack, int uOffset, int vOffset, int u2Offset, int v2Offset, boolean renderSecondIcon) {

        RenderSystem.setShaderTexture(0, TEXTURES);
        blit(poseStack, this.screenWidth / 2 - 50, this.screenHeight / 2 + 2, uOffset, vOffset, 15, 15, 512, 512);
        if (renderSecondIcon) {
            blit(poseStack, this.screenWidth / 2 - 50, this.screenHeight / 2 + 25, u2Offset, v2Offset, 15, 15, 512, 512);
        }
    }
    public void renderLevel(PoseStack poseStack, ItemStack stack, String characteristics){
        if(characteristics.equals("power")){
            switch(CanesItem.getPower(stack)){
                case 1 -> renderOne(poseStack);
                case 2 -> renderTwo(poseStack);
                case 3 -> renderThree(poseStack);
                case 4 -> renderFour(poseStack);
                case 5 -> renderFive(poseStack);
            }
        }
        if(characteristics.equals("radius")){
            switch(CanesItem.getRadius(stack)){
                case 1 -> renderOne(poseStack);
                case 2 -> renderTwo(poseStack);
                case 3 -> renderThree(poseStack);
                case 4 -> renderFour(poseStack);
                case 5 -> renderFive(poseStack);
            }
        }
        if(characteristics.equals("amt")){
            switch(CanesItem.getAmt(stack)){
                case 1 -> renderOne(poseStack);
                case 2 -> renderTwo(poseStack);
                case 3 -> renderThree(poseStack);
                case 4 -> renderFour(poseStack);
                case 5 -> renderFive(poseStack);
            }
        }
        if(characteristics.equals("heal")){
            switch(CanesItem.getHeal(stack)){
                case 1 -> renderOne(poseStack);
                case 2 -> renderTwo(poseStack);
                case 3 -> renderThree(poseStack);
                case 4 -> renderFour(poseStack);
                case 5 -> renderFive(poseStack);
            }
        }
    }
    public void renderManaBar(PoseStack pPoseStack, ItemStack itemStack){
        int k = CanesItem.getMana(itemStack) == 20 ? 166 : 8 * CanesItem.getMana(itemStack);
        RenderSystem.setShaderTexture(0, TEXTURES);
        blit(pPoseStack, this.screenWidth / 2 - 60, this.screenHeight / 2 - 39, 282, 16, k, 4, 512, 512);
    }
    private void renderOne(PoseStack poseStack){
        RenderSystem.setShaderTexture(0, TEXTURES);
        blit(poseStack, this.screenWidth / 2 - 96, this.screenHeight / 2 - 35,368,38, 22,22, 512, 512);
    }
    private void renderTwo(PoseStack poseStack){
        RenderSystem.setShaderTexture(0, TEXTURES);
        blit(poseStack, this.screenWidth / 2 - 93, this.screenHeight / 2 - 35,390,38, 17,22,512, 512);
    }
    private void renderThree(PoseStack poseStack){
        RenderSystem.setShaderTexture(0, TEXTURES);
        blit(poseStack, this.screenWidth / 2 - 98, this.screenHeight / 2 - 35,414,38, 26,22,512, 512);

    }
    private void renderFour(PoseStack poseStack){
        RenderSystem.setShaderTexture(0, TEXTURES);
        blit(poseStack, this.screenWidth / 2 - 96, this.screenHeight / 2 - 35,447,38, 24,22,512, 512);

    }
    private void renderFive(PoseStack poseStack){
        RenderSystem.setShaderTexture(0, TEXTURES);
        blit(poseStack, this.screenWidth / 2 - 93, this.screenHeight / 2 - 35,478,38, 15,22,512, 512);

    }
}
