package com.scouter.wherearemytms.setup;


import com.mojang.logging.LogUtils;
import com.scouter.wherearemytms.WAMTMenuType;
import com.scouter.wherearemytms.WhereAreMyTMs;
import com.scouter.wherearemytms.blocks.TMMachineScreen;
import com.scouter.wherearemytms.items.WAMTItemProperties;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.slf4j.Logger;

@Mod.EventBusSubscriber(modid = WhereAreMyTMs.MODID, value = Dist.CLIENT,bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetup {
    private static final Logger LOGGER = LogUtils.getLogger();
    public static void init(FMLClientSetupEvent event){
        MenuScreens.register(WAMTMenuType.SCREEN.get(), TMMachineScreen::new);
        WAMTItemProperties.createTypes();
    }
}

