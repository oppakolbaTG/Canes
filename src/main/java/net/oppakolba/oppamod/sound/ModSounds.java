package net.oppakolba.oppamod.sound;

import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.oppakolba.oppamod.Oppamod;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUNDS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Oppamod.MOD_ID);

    public static final RegistryObject<SoundEvent> BOSS_SUMMON =
            registerSound("boss_summon");

    public static final RegistryObject<SoundEvent> MANA_USE =
            registerSound("mana_use");

    private static RegistryObject<SoundEvent> registerSound(String name) {
        return SOUNDS.register(name, () -> new SoundEvent(new ResourceLocation(Oppamod.MOD_ID, name)));
    }
}
