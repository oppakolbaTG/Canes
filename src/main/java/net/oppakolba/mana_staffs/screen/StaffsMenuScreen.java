package net.oppakolba.mana_staffs.screen;

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
import net.oppakolba.mana_staffs.item.staffs.*;
import net.oppakolba.mana_staffs.item.misc.StaffsItem;
import net.oppakolba.mana_staffs.networking.StaffUpgradeRequirements;
import net.oppakolba.mana_staffs.networking.ModMessage;
import net.oppakolba.mana_staffs.networking.packet.UpgradeCharC2SPacket;
import net.oppakolba.mana_staffs.screen.utils.ScreenUtils;
import org.jetbrains.annotations.NotNull;

public class StaffsMenuScreen extends Screen  {
    private static final ResourceLocation TEXTURES = new ResourceLocation("manastaffs", "textures/gui/terra_menu_screen1.png");
    private static final ResourceLocation UPG_BUTTON = new ResourceLocation("manastaffs", "textures/gui/buttonn_upg.png");
    private static final ResourceLocation REQUIREMENTS = new ResourceLocation("manastaffs", "textures/gui/requirements.png");
    protected int screenWidth;
    protected  int screenHeight;
    public int backgroundHeight = 152;
    public int backgroundWidth = 266;
    Font font = Minecraft.getInstance().font;
    int color = 0xcfa170;
    int shadowColor = 0xe7bf8b;
    private ImageButton upgButton;
    private String staffCode;
    private static final String MANA_TAG = "mana";
    private static final String MAX_MANA_TAG = "max_mana";


    public StaffsMenuScreen(Component pTitle) {
        super(pTitle);
    }


    @Override
    protected void init() {
        int x = (this.width - backgroundWidth) / 2;
        int y = (this.height - backgroundHeight) / 2;
        LocalPlayer player = Minecraft.getInstance().player;

        if (player == null) return;
        ItemStack itemStack = player.getItemInHand(InteractionHand.MAIN_HAND);
        staffCode = StaffUpgradeRequirements.resolveStaffCode(itemStack.getItem());
        if (staffCode == null){
            upgButton = null;
            return;
        }
        upgButton = new ImageButton(x + 32, y + 83, 32, 32, 0, 0, 32, UPG_BUTTON, 32, 64,  button -> {
            ModMessage.sendToServer(new UpgradeCharC2SPacket(staffCode, 1));
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
            if(stackItemInHand.getItem() instanceof StaffsItem) {
                if(stackItemInHand.getItem() instanceof FireballStaff){
                    renderManaBar(pPoseStack, stackItemInHand);
                    renderIconCharacteristics(pPoseStack, 298, 75, 314, 75, true);
                    ScreenUtils.drawColoredShadow(pPoseStack, font, Component.translatable("screen.power").append(": ").append(String.valueOf(2 + StaffsItem.getPower(stackItemInHand) * 2)),
                            x + 103, y + 84, color, shadowColor);
                    ScreenUtils.drawColoredShadow(pPoseStack, font, Component.translatable("screen.radius").append(": ").append(String.valueOf(1 + StaffsItem.getPower(stackItemInHand) * 2)),
                            x + 103, y + 107, color, shadowColor);
                    renderLevel(pPoseStack, stackItemInHand,"power");
                    renderRequirementsForLvlUp("fc", pPoseStack, x ,y, StaffsItem.getPower(stackItemInHand));
                }
                else if (stackItemInHand.getItem() instanceof BeamStaff){
                    renderManaBar(pPoseStack, stackItemInHand);
                    renderIconCharacteristics(pPoseStack, 298, 75, 0, 0,false);
                    ScreenUtils.drawColoredShadow(pPoseStack, font, Component.translatable("screen.power").append(": ").append(String.valueOf(1 + StaffsItem.getPower(stackItemInHand) * 2)),
                            x + 103, y + 84, color,shadowColor);
                    renderLevel(pPoseStack, stackItemInHand,"power");
                    renderRequirementsForLvlUp("bc", pPoseStack, x ,y, StaffsItem.getPower(stackItemInHand));
                }
                else if (stackItemInHand.getItem() instanceof LightningStaff){
                    renderManaBar(pPoseStack, stackItemInHand);
                    renderIconCharacteristics(pPoseStack, 330, 75, 0,0,false);
                    ScreenUtils.drawColoredShadow(pPoseStack, font, Component.translatable("screen.amt").append(": ").append(String.valueOf(1 + StaffsItem.getAmt(stackItemInHand) * 2)),
                            x + 103, y + 84, color, shadowColor);
                    renderLevel(pPoseStack, stackItemInHand,"amt");
                    renderRequirementsForLvlUp("lc", pPoseStack, x ,y, StaffsItem.getAmt(stackItemInHand));
                }
                else if (stackItemInHand.getItem() instanceof HealStaff){
                    renderManaBar(pPoseStack, stackItemInHand);
                    renderIconCharacteristics(pPoseStack, 314, 75, 346, 75, true);
                    ScreenUtils.drawColoredShadow(pPoseStack, font, Component.translatable("screen.radius").append(": ").append(String.valueOf(1 + StaffsItem.getRadius(stackItemInHand) * 2)),
                            x + 103, y + 84, color, shadowColor);
                    ScreenUtils .drawColoredShadow(pPoseStack, font, Component.translatable("screen.heal").append(": ").append(String.valueOf(StaffsItem.getHeal(stackItemInHand) * 2)),
                            x + 103, y + 107, color, shadowColor);
                    renderLevel(pPoseStack, stackItemInHand,"radius");
                    renderRequirementsForLvlUp("hc", pPoseStack, x ,y, StaffsItem.getRadius(stackItemInHand));
                }
                else if (stackItemInHand.getItem() instanceof RainOfCharges){
                    renderManaBar(pPoseStack, stackItemInHand);
                    renderIconCharacteristics(pPoseStack, 298, 75, 330, 75, true);
                    ScreenUtils.drawColoredShadow(pPoseStack, font, Component.translatable("screen.power").append(": ").append(String.valueOf(2 + StaffsItem.getPower(stackItemInHand) * 2)),
                            x + 103, y + 84, color, shadowColor);
                    ScreenUtils.drawColoredShadow(pPoseStack, font, Component.translatable("screen.amt").append(": ").append(String.valueOf(1 + StaffsItem.getAmt(stackItemInHand) * 2)),
                            x + 103, y + 107, color,shadowColor);
                    renderLevel(pPoseStack, stackItemInHand,"power");
                    renderRequirementsForLvlUp("roc",pPoseStack ,x ,y , StaffsItem.getPower(stackItemInHand));
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
        String cCode = StaffUpgradeRequirements.resolveStaffCode(stack.getItem());
        if (!staffCode.equals(cCode)) {
            this.init(this.minecraft, this.width, this.height);
            return;
        }

        int level = StaffUpgradeRequirements.primaryLevel(staffCode, stack);
        boolean canUpgrade = level < StaffUpgradeRequirements.MAX_LEVEL
                && StaffUpgradeRequirements.hasRequiredItem(player, staffCode, level);
        upgButton.visible = canUpgrade;
        upgButton.active = canUpgrade;
    }
    /**render**/
    public void renderRequirementsForLvlUp(String item, PoseStack poseStack, int x, int y, int lvl){
        RenderSystem.setShaderTexture(0, REQUIREMENTS);
        switch(item){
            case "fc" -> {
                blit(poseStack, x + 200, y + 10, 0, 0, 10, 8, 79, 8);
                blit(poseStack, x + 220, y + 10, 13, 0, 10, 8, 79, 8);
            }
            case "bc" -> {
                blit(poseStack, x + 200, y + 10,0, 0, 10, 8, 79, 8);
                blit(poseStack, x + 220, y + 10, 40, 0, 10, 8, 79, 8);
            }
            case "hc" -> {
                blit(poseStack, x + 200, y + 10, 0, 0, 10, 8, 79, 8);
                blit(poseStack, x + 220, y + 10, 27, 0, 10, 8, 79, 8);
            }
            case "lc" -> {
                blit(poseStack, x + 200, y + 10, 0, 0, 10, 8, 79, 8);
                blit(poseStack, x + 219, y + 10, 53, 0, 10, 8, 79, 8);
            }
            case "roc" -> {
                blit(poseStack, x + 200, y + 10, 0, 0, 10, 8, 79, 8);
                blit(poseStack, x + 220, y + 10, 68, 0, 10, 8, 79, 8);
            }
        }
        switch (lvl) {
            case 1 -> {
                ScreenUtils.drawColoredShadow(poseStack, font, Component.translatable(":1"),x + 230, y + 10, color, shadowColor);
                ScreenUtils.drawColoredShadow(poseStack, font, Component.translatable(":1"),x + 209, y + 10, color, shadowColor);
            }
            case 2 -> {
                ScreenUtils.drawColoredShadow(poseStack, font, Component.translatable(":1"),x + 230, y + 10, color, shadowColor);
                ScreenUtils.drawColoredShadow(poseStack, font, Component.translatable(":2"),x + 209, y + 10, color, shadowColor);
            }
            case 3 -> {
                ScreenUtils.drawColoredShadow(poseStack, font, Component.translatable(":2"),x + 230, y + 10, color, shadowColor);
                ScreenUtils.drawColoredShadow(poseStack, font, Component.translatable(":2"),x + 209, y + 10, color, shadowColor);
            }
            case 4 -> {
                ScreenUtils.drawColoredShadow(poseStack, font, Component.translatable(":2"),x + 230, y + 10, color, shadowColor);
                ScreenUtils.drawColoredShadow(poseStack, font, Component.translatable(":3"),x + 209, y + 10, color, shadowColor);
            }
            case 5 -> {
                ScreenUtils.drawColoredShadow(poseStack, font, Component.translatable(":3"),x + 230, y + 10, color, shadowColor);
                ScreenUtils.drawColoredShadow(poseStack, font, Component.translatable(":3"),x + 209, y + 10, color, shadowColor);
            }
        }

    }
    public void renderIconCharacteristics(PoseStack poseStack, int uOffset, int vOffset, int u2Offset, int v2Offset, boolean renderSecondIcon) {

        RenderSystem.setShaderTexture(0, TEXTURES);
        blit(poseStack, this.screenWidth / 2 - 50, this.screenHeight / 2 + 6, uOffset, vOffset, 15, 15, 512, 512);
        if (renderSecondIcon) {
            blit(poseStack, this.screenWidth / 2 - 50, this.screenHeight / 2 + 29, u2Offset, v2Offset, 15, 15, 512, 512);
        }
    }
    public void renderLevel(PoseStack poseStack, ItemStack stack, String characteristics){
        if(characteristics.equals("power")){
            switch(StaffsItem.getPower(stack)){
                case 1 -> renderOne(poseStack);
                case 2 -> renderTwo(poseStack);
                case 3 -> renderThree(poseStack);
                case 4 -> renderFour(poseStack);
                case 5 -> renderFive(poseStack);
            }
        }
        if(characteristics.equals("radius")){
            switch(StaffsItem.getRadius(stack)){
                case 1 -> renderOne(poseStack);
                case 2 -> renderTwo(poseStack);
                case 3 -> renderThree(poseStack);
                case 4 -> renderFour(poseStack);
                case 5 -> renderFive(poseStack);
            }
        }
        if(characteristics.equals("amt")){
            switch(StaffsItem.getAmt(stack)){
                case 1 -> renderOne(poseStack);
                case 2 -> renderTwo(poseStack);
                case 3 -> renderThree(poseStack);
                case 4 -> renderFour(poseStack);
                case 5 -> renderFive(poseStack);
            }
        }
        if(characteristics.equals("heal")){
            switch(StaffsItem.getHeal(stack)){
                case 1 -> renderOne(poseStack);
                case 2 -> renderTwo(poseStack);
                case 3 -> renderThree(poseStack);
                case 4 -> renderFour(poseStack);
                case 5 -> renderFive(poseStack);
            }
        }
    }
    public void renderManaBar(PoseStack pPoseStack, ItemStack itemStack){
        int j = 166 / StaffsItem.getMaxMana(itemStack);
        int k = StaffsItem.getMana(itemStack) == StaffsItem.getMaxMana(itemStack) ? 166 : j * StaffsItem.getMana(itemStack);
        RenderSystem.setShaderTexture(0, TEXTURES);
        blit(pPoseStack, this.screenWidth / 2 - 60, this.screenHeight / 2 - 36, 282, 16, k, 4, 512, 512);
    }
    private void renderOne(PoseStack poseStack){
        RenderSystem.setShaderTexture(0, TEXTURES);
        blit(poseStack, this.screenWidth / 2 - 96, this.screenHeight / 2 - 32,368,38, 22,22, 512, 512);
    }
    private void renderTwo(PoseStack poseStack){
        RenderSystem.setShaderTexture(0, TEXTURES);
        blit(poseStack, this.screenWidth / 2 - 93, this.screenHeight / 2 - 32,390,38, 17,22,512, 512);
    }
    private void renderThree(PoseStack poseStack){
        RenderSystem.setShaderTexture(0, TEXTURES);
        blit(poseStack, this.screenWidth / 2 - 98, this.screenHeight / 2 - 32,414,38, 26,22,512, 512);

    }
    private void renderFour(PoseStack poseStack){
        RenderSystem.setShaderTexture(0, TEXTURES);
        blit(poseStack, this.screenWidth / 2 - 96, this.screenHeight / 2 - 32,447,38, 24,22,512, 512);

    }
    private void renderFive(PoseStack poseStack){
        RenderSystem.setShaderTexture(0, TEXTURES);
        blit(poseStack, this.screenWidth / 2 - 93, this.screenHeight / 2 - 32,478,38, 15,22,512, 512);

    }
}
