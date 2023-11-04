package com.nori6272.wherearemytms.setup;

import com.mojang.logging.LogUtils;
import com.nori6272.wherearemytms.Messages;
import com.nori6272.wherearemytms.WhereAreMyTMs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import org.slf4j.Logger;

@Mod.EventBusSubscriber(modid = WhereAreMyTMs.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModSetup {
    private static final Logger LOGGER = LogUtils.getLogger();
    public static void init(FMLCommonSetupEvent event){
        Messages.register();
    }

    public static void setup(){
        IEventBus bus = MinecraftForge.EVENT_BUS;
    }
}
