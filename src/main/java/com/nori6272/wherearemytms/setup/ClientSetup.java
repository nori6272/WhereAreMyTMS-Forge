package com.nori6272.wherearemytms.setup;


import com.mojang.logging.LogUtils;
import com.nori6272.wherearemytms.blocks.TMMachineScreen;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import com.nori6272.wherearemytms.WAMTMenuType;
import com.nori6272.wherearemytms.WhereAreMyTMs;
import com.nori6272.wherearemytms.items.WAMTItemProperties;
import org.slf4j.Logger;

@Mod.EventBusSubscriber(modid = WhereAreMyTMs.MODID, value = Dist.CLIENT,bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetup {
    private static final Logger LOGGER = LogUtils.getLogger();
    public static void init(FMLClientSetupEvent event){
        MenuScreens.register(WAMTMenuType.SCREEN.get(), TMMachineScreen::new);
        WAMTItemProperties.createTypes();
    }
}

