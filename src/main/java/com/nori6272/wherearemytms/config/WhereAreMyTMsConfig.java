package com.nori6272.wherearemytms.config;

import net.minecraftforge.common.ForgeConfigSpec;
import com.nori6272.wherearemytms.WhereAreMyTMs;

public class WhereAreMyTMsConfig {

    public static final ForgeConfigSpec CONFIG_BUILDER;

    static {
        ForgeConfigSpec.Builder configBuilder = new ForgeConfigSpec.Builder();
        setupConfig(configBuilder);
        CONFIG_BUILDER = configBuilder.build();
    }

    public static ForgeConfigSpec.ConfigValue<Boolean> ALLOW_TUTOR_MOVES;
    private static void setupConfig(ForgeConfigSpec.Builder builder) {
        builder.comment(WhereAreMyTMs.MODID + " Config");

        ALLOW_TUTOR_MOVES = builder.comment("Allow Tutor Moves").define("allow_tutor_moves", false);
    }
}
