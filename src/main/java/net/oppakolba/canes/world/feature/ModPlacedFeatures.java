package net.oppakolba.canes.world.feature;

import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.oppakolba.canes.Canes;

import java.util.List;

public class ModPlacedFeatures {
    public static final DeferredRegister<PlacedFeature> PLACED_FEATURES =
            DeferredRegister.create(Registry.PLACED_FEATURE_REGISTRY, Canes.MOD_ID);

public static final RegistryObject<PlacedFeature> PLATINUM_ORE_PLACED = PLACED_FEATURES.register("platinum_ore_placed",
        () -> new PlacedFeature(ModConfiguredFeatures.PLATINUM_ORE.getHolder().get(), commonOrePlacement(8,
                        HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-80), VerticalAnchor.belowTop(80)))));

public static final RegistryObject<PlacedFeature> WATER_LEAF_PLACED = PLACED_FEATURES.register("water_leaf_placed",
        () -> new PlacedFeature(ModConfiguredFeatures.WATER_LEAF.getHolder().get(), List.of(RarityFilter.onAverageOnceEvery(8),
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome())));

    private static List<PlacementModifier> orePlacement(PlacementModifier p_195347_, PlacementModifier p_195348_) {
        return List.of(p_195347_, InSquarePlacement.spread(), p_195348_, BiomeFilter.biome());
    }

    private static List<PlacementModifier> commonOrePlacement(int p_195344_, PlacementModifier p_195345_) {
        return orePlacement(CountPlacement.of(p_195344_), p_195345_);
    }

    private static List<PlacementModifier> rareOrePlacement(int p_195350_, PlacementModifier p_195351_) {
        return orePlacement(RarityFilter.onAverageOnceEvery(p_195350_), p_195351_);
    }

    public static void register(IEventBus eventBus){
        PLACED_FEATURES.register(eventBus);
    }
}
