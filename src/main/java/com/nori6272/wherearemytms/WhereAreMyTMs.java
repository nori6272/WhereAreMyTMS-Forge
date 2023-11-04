package com.nori6272.wherearemytms;

import com.mojang.logging.LogUtils;
import com.nori6272.wherearemytms.config.WhereAreMyTMsConfig;
import com.nori6272.wherearemytms.setup.ClientSetup;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import com.nori6272.wherearemytms.setup.ModSetup;
import com.nori6272.wherearemytms.setup.Registration;
import org.slf4j.Logger;

import java.util.Locale;

@Mod(WhereAreMyTMs.MODID)
public class WhereAreMyTMs {
    public static final String MODID = "wherearemytms";
    private static final Logger LOGGER = LogUtils.getLogger();

    public WhereAreMyTMs() {
        Registration.init();
        ModSetup.setup();

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, WhereAreMyTMsConfig.CONFIG_BUILDER);
        IEventBus forgeBus = MinecraftForge.EVENT_BUS;
        IEventBus modbus = FMLJavaModLoadingContext.get().getModEventBus();
        modbus.addListener(ModSetup::init);
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> modbus.addListener(ClientSetup::init));
    }

    public static ResourceLocation prefix(String name) {
        return new ResourceLocation(MODID, name.toLowerCase(Locale.ROOT));
    }


}
