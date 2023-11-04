package com.nori6272.wherearemytms.setup;

import com.mojang.logging.LogUtils;
import com.nori6272.wherearemytms.WAMTMenuType;
import com.nori6272.wherearemytms.blocks.WAMTBlockEntity;
import com.nori6272.wherearemytms.blocks.WAMTBlocks;
import com.nori6272.wherearemytms.items.WAMTItems;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;


public class Registration {
    private static final Logger LOGGER = LogUtils.getLogger();
    public static void init(){

        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        WAMTBlocks.BLOCKS.register(bus);
        WAMTItems.ITEMS.register(bus);
        WAMTMenuType.MENUS.register(bus);
        WAMTBlockEntity.BLOCK_ENTITIES.register(bus);


    }


}
