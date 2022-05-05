package ca.skynetcloud.electricfencemod.init;


import ca.skynetcloud.electricfencemod.Electricfencemod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.lang.reflect.Field;



@Mod.EventBusSubscriber(modid = Electricfencemod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SoundInit {

    public static final SoundEvent ELECTRIC_FENCE_SPARK;
    public static final SoundEvent ELECTRIC_FENCE_IDLE;

    static {
        ELECTRIC_FENCE_SPARK = createSoundEvent("electric_fence_spark");
        ELECTRIC_FENCE_IDLE = createSoundEvent("electric_fence_idle");
    }

    private static SoundEvent createSoundEvent(final String soundName) {
        final ResourceLocation soundID = new ResourceLocation(Electricfencemod.MODID, soundName);
        return new SoundEvent(soundID).setRegistryName(soundID);
    }

    @SubscribeEvent
    public static void registerSoundEvents(final RegistryEvent.Register<SoundEvent> event) {
        try {
            for (Field f : SoundInit.class.getDeclaredFields()) {
                Object obj = f.get(null);
                if (obj instanceof SoundEvent) {
                    event.getRegistry().register((SoundEvent) obj);
                } else if (obj instanceof SoundEvent[]) {
                    for (SoundEvent soundEvent : (SoundEvent[]) obj) {
                        event.getRegistry().register(soundEvent);
                    }
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
