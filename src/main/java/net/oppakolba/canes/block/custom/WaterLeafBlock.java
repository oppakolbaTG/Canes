package net.oppakolba.canes.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.gameevent.GameEvent;


public class WaterLeafBlock extends BushBlock implements BonemealableBlock {
    public static final IntegerProperty AGE = IntegerProperty.create("age", 0, 3);

    public WaterLeafBlock(Properties properties) {
        super(Properties.copy(Blocks.SWEET_BERRY_BUSH));
        this.registerDefaultState(this.stateDefinition.any().setValue(AGE, 0));
    }

    @Override
    protected boolean mayPlaceOn(BlockState p_51042_, BlockGetter p_51043_, BlockPos p_51044_) {
        return p_51042_.is(BlockTags.SAND) || p_51042_.is(Blocks.FARMLAND);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    @Override
    public boolean isValidBonemealTarget(BlockGetter p_50897_, BlockPos p_50898_, BlockState p_50899_, boolean p_50900_) {
        return p_50899_.getValue(AGE) < 3;
    }

    @Override
    public boolean isBonemealSuccess(Level p_220878_, RandomSource p_220879_, BlockPos p_220880_, BlockState p_220881_) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel server, RandomSource p_220875_, BlockPos pos, BlockState state
    ) {
        if (state.getValue(AGE) < 3) {
            server.setBlockAndUpdate(pos, state.setValue(AGE, state.getValue(AGE) + 1));
        } else {
            System.out.println("Ошибка: неверное состояние блока " + state);
        }
    }

    @Override
    public boolean isRandomlyTicking(BlockState p_49921_) {
        return p_49921_.getValue(AGE) < 3;
    }

    @Deprecated
    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        int age = state.getValue(AGE);
        if (age < 3) {
            boolean isHumid = level.getBiome(pos).value().getBaseTemperature() > 0.8f; // Теплый и влажный биом?
            int chance = isHumid ? 3 : 5; // Влажные биомы ускоряют рост

            if (level.getRawBrightness(pos.above(), 0) >= 9 && random.nextInt(chance) == 0) {
                level.setBlock(pos, state.setValue(AGE, age + 1), 2);
                level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(state));
            }
        }
    }

    //
//    public void randomTick(BlockState p_222563_, ServerLevel p_222564_, BlockPos p_222565_, RandomSource p_222566_) {
//        int i = p_222563_.getValue(AGE);
//        if (i < 3 && p_222564_.getRawBrightness(p_222565_.above(), 0) >= 9 && net.minecraftforge.common.ForgeHooks.onCropsGrowPre(p_222564_, p_222565_, p_222563_, p_222566_.nextInt(5) == 0)) {
//            BlockState blockstate = p_222563_.setValue(AGE, Integer.valueOf(i + 1));
//            p_222564_.setBlock(p_222565_, blockstate, 2);
//            p_222564_.gameEvent(GameEvent.BLOCK_CHANGE, p_222565_, GameEvent.Context.of(blockstate));
//            net.minecraftforge.common.ForgeHooks.onCropsGrowPost(p_222564_, p_222565_, p_222563_);
//        }
//    }
}


