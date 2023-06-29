package com.scouter.wherearemytms.setup;

import com.mojang.logging.LogUtils;
import com.scouter.wherearemytms.Messages;
import com.scouter.wherearemytms.WhereAreMyTMs;
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
