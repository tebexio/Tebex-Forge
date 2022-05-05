package ca.skynetcloud.electricfencemod.init;

import net.minecraft.world.damagesource.DamageSource;

public class DamageInit {

    public static final DamageSource ELECTRIC_FENCE;

    static {
        ELECTRIC_FENCE = new DamageSource("electricfence");
    }
}
