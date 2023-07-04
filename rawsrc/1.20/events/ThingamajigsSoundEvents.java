package net.rk.thingamajigs.events;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.rk.thingamajigs.Thingamajigs;

public class ThingamajigsSoundEvents {

    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Thingamajigs.MOD_ID);


    public static final RegistryObject<SoundEvent> STATIC = registerSoundEvent("static");
    public static final RegistryObject<SoundEvent> HORN = registerSoundEvent("horn");
    public static final RegistryObject<SoundEvent> CODE = registerSoundEvent("code");
    public static final RegistryObject<SoundEvent> BEEP = registerSoundEvent("beep");
    public static final RegistryObject<SoundEvent> ELECTRONIC = registerSoundEvent("electronic");
    public static final RegistryObject<SoundEvent> METALLIC = registerSoundEvent("metallic");
    public static final RegistryObject<SoundEvent> OLD = registerSoundEvent("old");
    public static final RegistryObject<SoundEvent> PLUCK = registerSoundEvent("pluck");
    public static final RegistryObject<SoundEvent> POOP = registerSoundEvent("poop");
    public static final RegistryObject<SoundEvent> AIR = registerSoundEvent("air");
    public static final RegistryObject<SoundEvent> WATER_NOISE = registerSoundEvent("water_noise");
    public static final RegistryObject<SoundEvent> POOP_BREAK = registerSoundEvent("poop_break");
    public static final RegistryObject<SoundEvent> POOP_HIT = registerSoundEvent("poop_hit");
    public static final RegistryObject<SoundEvent> POOP_STEP = registerSoundEvent("poop_step");
    public static final RegistryObject<SoundEvent> MECH_BELL_ONE = registerSoundEvent("mech_bell_one");
    public static final RegistryObject<SoundEvent> MECH_BELL_TWO = registerSoundEvent("mech_bell_two");

    // custom instrument sounds
    public static final RegistryObject<SoundEvent> LARGE_POOP = registerSoundEvent("large_poop");

    // variable range sound effects
    private static RegistryObject<SoundEvent> registerSoundEvent(String name){
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(Thingamajigs.MOD_ID,name)));
    }

    public static void register(IEventBus eventBus){
        SOUND_EVENTS.register(eventBus);
    }

}
