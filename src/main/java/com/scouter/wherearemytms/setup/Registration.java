package com.scouter.wherearemytms.setup;

import com.mojang.logging.LogUtils;
import com.scouter.wherearemytms.WAMTMenuType;
import com.scouter.wherearemytms.blocks.WAMTBlockEntity;
import com.scouter.wherearemytms.blocks.WAMTBlocks;
import com.scouter.wherearemytms.items.WAMTItems;
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
