package net.oppakolba.canes.item.misc;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.checkerframework.checker.units.qual.A;

public class CanesItem extends Item {
    int value;
    private static final String MANA_TAG = "mana";
    private static final String POWER = "power";
    private static final String HEAL = "heal";
    private static final String RADIUS = "radius";
    private static final String AMT = "amt";
    private static final String MAX_MANA_TAG = "max_mana";
    private final int maxMana;

    public CanesItem(Properties pProperties, int value, int maxMana) {
        super(pProperties);
        this.value = value;
        this.maxMana = maxMana;
    }

    public CanesItem(Properties properties, int maxMana) {
        this(properties, 20, maxMana);
    }

    public static int getPower(ItemStack stack){
        if(stack.getItem() instanceof  CanesItem){
            CompoundTag tag = stack.getOrCreateTag();
            return tag.getInt(POWER);
        }
        return 0;
    }
    public static int getRadius(ItemStack stack){
        if(stack.getItem() instanceof  CanesItem){
            CompoundTag tag = stack.getOrCreateTag();
            return tag.getInt(RADIUS);
        }
        return 0;
    }
    public static int getHeal(ItemStack stack){
        if(stack.getItem() instanceof  CanesItem){
            CompoundTag tag = stack.getOrCreateTag();
            return tag.getInt(HEAL);
        }
        return 0;
    }
    public static int getAmt(ItemStack stack){
        if(stack.getItem() instanceof  CanesItem){
            CompoundTag tag = stack.getOrCreateTag();
            return tag.getInt(AMT);
        }
        return 0;
    }


    public static int getMana(ItemStack stack) {
        if (stack.getItem() instanceof CanesItem) {
            CompoundTag tag = stack.getOrCreateTag();
            return tag.getInt("mana");
        }
        return 0;
    }

    public static int getMaxMana(ItemStack stack) {
        if (stack.getItem() instanceof CanesItem) {
            CompoundTag tag = stack.getOrCreateTag();
            return tag.getInt("max_mana");
        }
        return 0;
    }

    public static void setMana(ItemStack stack, int mana) {
        if (stack.getItem() instanceof CanesItem) {
            CompoundTag tag = stack.getOrCreateTag();
            int maxMana = tag.getInt("max_mana");
            tag.putInt("mana", Math.min(Math.max(mana, 0), maxMana));
        }
    }

    public static void setMaxMana(ItemStack stack, int maxMana){
        if (stack.getItem() instanceof CanesItem) {
            CompoundTag tag = stack.getOrCreateTag();
            int oldMaxMana = tag.getInt("maxMana");
            tag.putInt("maxMana", Math.max(oldMaxMana, Math.min(0, maxMana)));
        }
        }

    private void initialize(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        if (!tag.contains(MAX_MANA_TAG)) {
            tag.putInt(MAX_MANA_TAG, this.maxMana);
        }
        if (!tag.contains(MANA_TAG)) {
            tag.putInt(MANA_TAG, 0);
        }
        if (!tag.contains(POWER)) {
            tag.putInt(POWER, 1);
        }
        if (!tag.contains(RADIUS)) {
            tag.putInt(RADIUS, 1);
        }
        if (!tag.contains(HEAL)) {
            tag.putInt(HEAL, 1);
        }
        if (!tag.contains(AMT)) {
            tag.putInt(AMT, 1);
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        player.startUsingItem(hand);
        initialize(stack);
        return InteractionResultHolder.consume(stack);
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 1000;
    }


    public void randomSpawnParticles(ParticleOptions particle, Level level, Player player, int pX,int pY ,int pZ) {

        final double randomX = player.getRandomX(pX);
        final double randomY = player.getRandomY();
        final double randomZ = player.getRandomZ(pZ);


        level.addParticle(particle, randomX, randomY, randomZ, 0, 0.5, 0);
    }

    @Override
    public void onCraftedBy(ItemStack pStack, Level pLevel, Player pPlayer) {
        super.onCraftedBy(pStack, pLevel, pPlayer);
        initialize(pStack);

    }


}

