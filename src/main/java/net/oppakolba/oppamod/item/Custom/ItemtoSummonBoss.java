package net.oppakolba.oppamod.item.Custom;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class ItemtoSummonBoss extends SpawnEggItem {
    public ItemtoSummonBoss(EntityType<? extends Mob> defaultType, int backgroundColor, int highlightColor, Properties properties) {
        super(defaultType, backgroundColor, highlightColor, properties);

    }
    private void OutPut(Player player){
        player.sendSystemMessage(Component.literal("Призывать босса можно только ночью"));
    }

    @Override
    public InteractionResult useOn(UseOnContext context){
        Level level = context.getLevel();
        Player player = context.getPlayer();
        long dayTime = level.getDayTime() % 24000;
        if(dayTime >= 13000 && dayTime < 23000){
            InteractionResult result = super.useOn(context);
            if(!(level instanceof ServerLevel serverLevel)){
                return InteractionResult.SUCCESS;
            }

            BlockPos pos = context.getClickedPos();
            LightningBolt lightningBolt = EntityType.LIGHTNING_BOLT.create(serverLevel);

            if (lightningBolt != null) {
                lightningBolt.moveTo(pos.getX() + 16, pos.getY(), pos.getZ() + 16);
                serverLevel.addFreshEntity(lightningBolt);
            }
            player.getCooldowns().addCooldown(this, 80);
        }
        else {
            if (player != null) {
                OutPut(player);
            }
            else{
                System.out.println("Ошибка 1");
            }
        }
        return InteractionResult.CONSUME;

    }
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if(!level.isClientSide() && hand == InteractionHand.MAIN_HAND){
            // Выводит Предложение(пока что) должен призывать босса
            OutPut1(player);
            //Перезарядка в тиках
            player.getCooldowns().addCooldown(this, 80);
        }
        return super.use(level, player, hand);
    }


    private void OutPut1(Player player){
        player.sendSystemMessage(Component.literal( "Этот предмет работает только при нажатии на блок"));
    }
}

