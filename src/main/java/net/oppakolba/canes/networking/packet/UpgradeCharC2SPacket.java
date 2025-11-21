package net.oppakolba.canes.networking.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import net.oppakolba.canes.item.misc.CanesItem;

import java.util.Objects;
import java.util.function.Supplier;

public class UpgradeCharC2SPacket {
    private final String characteristic;
    private int value;

    public UpgradeCharC2SPacket(String characteristic, int value){
        this.characteristic = characteristic;
        this.value = value;
    }

    public UpgradeCharC2SPacket(FriendlyByteBuf buf) {
        this.characteristic = buf.readUtf();
        this.value = buf.readInt();
    }

    public void toByte(FriendlyByteBuf buf) {
        buf.writeUtf(characteristic);
        buf.writeInt(value);
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context ctx = supplier.get();
        ctx.enqueueWork(() -> {
            ServerPlayer serverPlayer = ctx.getSender();
            ItemStack itemStack = Objects.requireNonNull(serverPlayer).getMainHandItem();
            if (itemStack.getItem() instanceof CanesItem) {
                    applyChar(itemStack);
                }
            serverPlayer.inventoryMenu.broadcastChanges();
        });
        ctx.setPacketHandled(true);
    }

    private void applyChar(ItemStack stack){
        switch (characteristic){
            case "power" -> addPowerServer(stack, value);
            case "radius" -> addRadiusServer(stack, value);
            case "heal" -> addHealServer(stack, value);
            case "amt" -> addAmtServer(stack, value);
        }
    }

    private void addPowerServer(ItemStack stack, int value) {
        var tag = stack.getOrCreateTag();
        int current = tag.getInt("power");
        int newValue = Math.min(5, Math.max(current + value, 0));
        tag.putInt("power", newValue);
    }

    private void addRadiusServer(ItemStack stack, int value) {
        var tag = stack.getOrCreateTag();
        int current = tag.getInt("radius");
        int newValue = Math.min(5, Math.max(current + value, 0));
        tag.putInt("radius", newValue);
    }

    private void addHealServer(ItemStack stack, int value) {
        var tag = stack.getOrCreateTag();
        int current = tag.getInt("heal");
        int newValue = Math.min(5, Math.max(current + value, 0));
        tag.putInt("heal", newValue);
    }

    private void addAmtServer(ItemStack stack, int value) {
        var tag = stack.getOrCreateTag();
        int current = tag.getInt("amt");
        int newValue = Math.min(5, Math.max(current + value, 0));
        tag.putInt("amt", newValue);
    }
}
