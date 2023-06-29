package com.scouter.wherearemytms.items;

import com.scouter.wherearemytms.WhereAreMyTMs;
import com.scouter.wherearemytms.blocks.WAMTBlocks;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class WAMTItems {
    public static List<Item> machines = new ArrayList<>();
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS,
            WhereAreMyTMs.MODID);

    public static final RegistryObject<Item> TM_MACHINE_BLOCK = fromBlock(WAMTBlocks.TM_MACHINE_BLOCK);

    public static final RegistryObject<Item> BLANK_HM = registerItem("blank_hm", () -> new BasePokemonTM(new Item.Properties().stacksTo(1).tab(CreativeModeTab.TAB_MISC),"title.wherearemytms.hm",
            "description.wherearemytms.blank_hm_uses",
            true));

    public static final RegistryObject<Item> BLANK_TM = registerItem("blank_tm", () -> new BasePokemonTM(new Item.Properties().stacksTo(1).tab(CreativeModeTab.TAB_MISC),"title.wherearemytms.tm",
            "description.wherearemytms.blank_tm_uses",
            false));


    public static <B extends Block> RegistryObject<Item> fromBlock(RegistryObject<B> block) {
        return ITEMS.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    }
    public static RegistryObject<Item> registerItem(String name, final Supplier<? extends Item> sup){
        //machines.add(sup.get());
        return ITEMS.register(name , sup);
    }
}
