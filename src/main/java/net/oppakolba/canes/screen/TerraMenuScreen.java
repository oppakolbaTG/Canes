package net.oppakolba.canes.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.ItemStack;
import net.oppakolba.canes.client.manahud.ClientManaData;
import net.oppakolba.canes.init.ModItems;
import net.oppakolba.canes.mana.PlayerManaProvider;
import net.oppakolba.canes.networking.ModMessage;
import net.oppakolba.canes.networking.packet.TerraMenuC2SPacket;
import net.oppakolba.canes.networking.packet.TerraMenuReloadC2SPacket;
import net.oppakolba.canes.networking.packet.UpgradeC2SPacket;
import org.jetbrains.annotations.NotNull;

public class TerraMenuScreen extends Screen {
    private static final ResourceLocation TEXTURE = new ResourceLocation("canes", "textures/gui/terra_menu_screen1.png");
    private static final ResourceLocation BUTTON_TEXTURE = new ResourceLocation("canes", "textures/gui/upg_button.png");
    private static final ResourceLocation RBUTTON_TEXTURE = new ResourceLocation("canes", "textures/gui/reset_button.png");
    private static final ResourceLocation UPG_WIDGET_F_TEXTURE = new ResourceLocation("canes", "textures/gui/upg_widget_f.png");
    private static final ResourceLocation UPG_WIDGET_T_TEXTURE = new ResourceLocation("canes", "textures/gui/upg_widget_t.png");
    private static final ResourceLocation LEVEL_1 = new ResourceLocation("canes", "textures/gui/level_1.png");
    private static final ResourceLocation LEVEL_2 = new ResourceLocation("canes", "textures/gui/level_2.png");
    private static final ResourceLocation LEVEL_3 = new ResourceLocation("canes", "textures/gui/level_3.png");
    private static final ResourceLocation LEVEL_4 = new ResourceLocation("canes", "textures/gui/level_4.png");
    private static final ResourceLocation LEVEL_5 = new ResourceLocation("canes", "textures/gui/level_5.png");
    private Button upgButton;
    private Button reloadbutton;



    public TerraMenuScreen(Component pTitle) {
        super(pTitle);
    }



    @Override
    protected void init() {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player != null) {
            upgButton = new ImageButton(this.width / 2 - 50, this.height / 2 - 10, 20, 20,
                    0, 0, 20, BUTTON_TEXTURE, 20, 40, button -> {
                player.getCapability(PlayerManaProvider.PLAYER_MANA).ifPresent(mana -> {
                    if(haveItems(player)) {
                        ModMessage.sendToServer(new TerraMenuC2SPacket(mana.getMAX_MANA()));
                        System.out.println(mana.getMAX_MANA());

                        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
                            ItemStack stack = player.getInventory().getItem(i);
                            if (stack.getItem() == ModItems.MANA_CRYSTAL.get() && !stack.isEmpty()) {
                                ModMessage.sendToServer(new UpgradeC2SPacket());
                                stack.shrink(3);
                                break;
                            }
                        }
                    }
                });
            });
            reloadbutton = new ImageButton(this.width/ 2 + 10, this.height /2 - 10, 20, 20,
                    0, 0, 20, RBUTTON_TEXTURE, 20, 40, pButton -> {
                if(ClientManaData.getPlayerMaxMana() == 100) {
                    player.getCapability(PlayerManaProvider.PLAYER_MANA).ifPresent(mana -> {

                        ModMessage.sendToServer(new TerraMenuReloadC2SPacket());
                    });
                    player.addEffect(new MobEffectInstance(MobEffects.LUCK));
                }
            });
            this.addRenderableWidget(upgButton);
            this.addRenderableWidget(reloadbutton);
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
        switch (ClientManaData.getPlayerMaxMana()){
            case 20:
                RenderSystem.setShaderTexture(0, LEVEL_1);
                blit(pPoseStack, width - 64, height / 2 - 60, 0, 0, 150, 75, 80,80);
                break;
            case 40:
                RenderSystem.setShaderTexture(0, LEVEL_2);
                blit(pPoseStack, width - 64, height / 2 - 60, 0, 0, 150, 75, 80,80);
                break;
            case 60:
                RenderSystem.setShaderTexture(0, LEVEL_3);
                blit(pPoseStack, width - 64, height / 2 - 60, 0, 0, 150, 75, 80,80);
                break;
            case 80:
                RenderSystem.setShaderTexture(0, LEVEL_4);
                blit(pPoseStack, width - 64, height / 2 - 60, 0, 0, 150, 75, 80,80);
                break;
            case 100:
                RenderSystem.setShaderTexture(0, LEVEL_5);
                blit(pPoseStack, width - 64, height / 2 - 60, 0, 0, 150, 75, 80,80);
                break;
        }

        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);

        if(player != null) {
            if (upgButton.isHoveredOrFocused() && !(haveItems(player))) {
                RenderSystem.setShaderTexture(0, UPG_WIDGET_F_TEXTURE);
                blit(pPoseStack, pMouseX - 1, pMouseY - 1, 0, 32, 32, 19, 32, 32);
            }
            else if (upgButton.isHoveredOrFocused() && haveItems(player)){
                RenderSystem.setShaderTexture(0, UPG_WIDGET_T_TEXTURE);
                blit(pPoseStack, pMouseX - 1, pMouseY - 1, 0, 32, 32, 19, 32, 32);
            }
        }
    }

    @Override
    public void onClose() {
        super.onClose();
    }


    private boolean haveItems(LocalPlayer player) {
        return player.getInventory().countItem(ModItems.MANA_CRYSTAL.get()) >= 3;
    }
}
