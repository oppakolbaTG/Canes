package net.oppakolba.oppamod.item.Custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.BaseSpawner;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.oppakolba.oppamod.sound.ModSounds;


public class ItemtoSummonBoss extends SpawnEggItem {
    public ItemtoSummonBoss(EntityType<? extends Mob> defaultType, int backgroundColor, int highlightColor, Properties properties) {
        super(defaultType, backgroundColor, highlightColor, properties);

    }
    private void OutPut(Player player){
        player.sendSystemMessage(Component.literal("Призывать босса можно только ночью"));
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        if (!(level instanceof ServerLevel)) {
            return InteractionResult.SUCCESS;
        } else {
            ItemStack itemstack = context.getItemInHand();
            BlockPos blockpos = context.getClickedPos();
            Direction direction = context.getClickedFace();
            BlockState blockstate = level.getBlockState(blockpos);
            if (blockstate.is(Blocks.SPAWNER)) {
                BlockEntity blockentity = level.getBlockEntity(blockpos);
                if (blockentity instanceof SpawnerBlockEntity) {
                    BaseSpawner basespawner = ((SpawnerBlockEntity)blockentity).getSpawner();
                    EntityType<?> entitytype1 = this.getType(itemstack.getTag());
                    basespawner.setEntityId(entitytype1);
                    blockentity.setChanged();
                    level.sendBlockUpdated(blockpos, blockstate, blockstate, 3);
                    level.gameEvent(context.getPlayer(), GameEvent.BLOCK_CHANGE, blockpos);
                    itemstack.shrink(1);
                    return InteractionResult.CONSUME;
                }
            }
            Player player = context.getPlayer();
            long dayTime = level.getDayTime() % 24000;
            if (dayTime >= 13000 && dayTime < 23000) {
                if (!(level instanceof ServerLevel serverLevel)) {
                    return InteractionResult.SUCCESS;
                }

                BlockPos pos = context.getClickedPos();
                LightningBolt lightningBolt = EntityType.LIGHTNING_BOLT.create(serverLevel);
                if (lightningBolt != null) {
                    lightningBolt.moveTo(pos.getX() + 35, pos.getY(), pos.getZ());
                    serverLevel.addFreshEntity(lightningBolt);
                    level.playSound(null, pos, ModSounds.BOSS_SUMMON.get(), SoundSource.HOSTILE, 1.0f,1.0f);
                    player.getCooldowns().addCooldown(this, 80);
                }

                if (player == null) return InteractionResult.FAIL;

                // Спавним моба перед игроком (на 5 блоков вперед)
                Vec3 lookVec = player.getLookAngle();
                Vec3 spawnVec = player.position().add(lookVec.scale(5));

                spawnVec = spawnVec.add(30, 15, 0);
                BlockPos spawnPos = new BlockPos((int) Math.round(spawnVec.x), (int) Math.round(spawnVec.y), (int) Math.round(spawnVec.z));

                // Получаем тип сущности из яйца спавна
                EntityType<?> entityType = this.getType(itemstack.getTag());

                // Спавним моба
                if (entityType.spawn(serverLevel, itemstack, player, spawnPos, MobSpawnType.SPAWN_EGG, true, true) != null) {
                    itemstack.shrink(1);
                    level.gameEvent(player, GameEvent.ENTITY_PLACE, spawnPos);
                }

            } else {
                if (player != null) {
                    OutPut(player);
                } else {
                    System.out.println("Ошибка 1");
                }
            }
            return InteractionResult.CONSUME;

        }
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

