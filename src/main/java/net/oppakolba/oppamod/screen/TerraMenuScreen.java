package net.oppakolba.oppamod.screen;

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
import net.oppakolba.oppamod.init.ModItems;
import net.oppakolba.oppamod.mana.PlayerManaProvider;
import net.oppakolba.oppamod.networking.ModMessage;
import net.oppakolba.oppamod.networking.packet.TerraMenuC2SPacket;
import net.oppakolba.oppamod.networking.packet.TerraMenuReloadC2SPacket;

public class TerraMenuScreen extends Screen {
    private static final ResourceLocation TEXTURE = new ResourceLocation("oppamod", "textures/gui/terra_menu_screen.png");
    private static final ResourceLocation BUTTON_TEXTURE = new ResourceLocation("oppamod", "textures/gui/button.png");
    private static final ResourceLocation RBUTTON_TEXTURE = new ResourceLocation("oppamod", "textures/gui/reset_button.png");
    private static final ResourceLocation UPG_WIDGET_TEXTURE = new ResourceLocation("oppamod", "textures/gui/upg_widget.png");
    private Button upgButton;



    public TerraMenuScreen(Component pTitle) {
        super(pTitle);
    }


    private int level = 0;
    @Override
    protected void init() {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player != null) {

            upgButton = new  ImageButton(this.width / 2 - 50, this.height / 2 - 10, 20, 20,
                    0, 0, 21, BUTTON_TEXTURE, 32, 64, button -> {
                player.getCapability(PlayerManaProvider.PLAYER_MANA).ifPresent(mana -> {
                    if(haveItems(player)) {
                        ModMessage.sendToServer(new TerraMenuC2SPacket(mana.getMAX_MANA()));
                        level += 1;
                    }
                });
            });
            this.addRenderableWidget(upgButton);

            this.addRenderableWidget(new ImageButton(this.width/ 2 + 10, this.height /2 - 10, 30, 20,
                    0, 0, 20 , RBUTTON_TEXTURE, 32, 64, pButton -> {
                player.getCapability(PlayerManaProvider.PLAYER_MANA).ifPresent(mana -> {
                    ModMessage.sendToServer(new TerraMenuReloadC2SPacket());
                });
                player.addEffect(new MobEffectInstance(MobEffects.LUCK));
            }));
        }
    }


    @Override
    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, TEXTURE);

        this.blit(pPoseStack, width / 2 - 107, height / 2 - 107, 0, 0, 215, 215);

        if(upgButton.isHoveredOrFocused()){
            RenderSystem.setShaderTexture(0, UPG_WIDGET_TEXTURE);
            this.blit(pPoseStack, pMouseX - 1, pMouseY - 1, 0, 0, 32, 18);
        }
        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
    }

    @Override
    public void onClose() {
        super.onClose();
    }


    private boolean haveItems(LocalPlayer player) {
        var count = 0;
        for(ItemStack itemStack : player.getInventory().items) {
            if (itemStack.getItem() == ModItems.MANA_CRYSTAL.get()) {
                count += 1;
                if(count < 3){
                    return false;
                }
            }
        }
        return true;
    }


}
