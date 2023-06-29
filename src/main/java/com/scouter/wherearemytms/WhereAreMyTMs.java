package com.scouter.wherearemytms;

import com.mojang.logging.LogUtils;
import com.scouter.wherearemytms.config.WhereAreMyTMsConfig;
import com.scouter.wherearemytms.setup.ClientSetup;
import com.scouter.wherearemytms.setup.ModSetup;
import com.scouter.wherearemytms.setup.Registration;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
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
