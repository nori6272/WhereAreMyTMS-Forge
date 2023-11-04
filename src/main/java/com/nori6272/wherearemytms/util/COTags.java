package com.nori6272.wherearemytms.util;

import com.nori6272.wherearemytms.WhereAreMyTMs;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class COTags {
    public static class Blocks {


        private static TagKey<Block> tag(String name){
            return BlockTags.create(WhereAreMyTMs.prefix(name));

        }
        private static TagKey<Block> forgeTag(String name){
            return BlockTags.create(new ResourceLocation("forge", name));

        }
    }

    public static class Items {

        public static final TagKey<Item> COBBLEMON_ITEMS = tag("cobblemon_items");

        private static TagKey<Item> tag(String name){
            return ItemTags.create(WhereAreMyTMs.prefix(name));

        }
        private static TagKey<Item> forgeTag(String name){
            return ItemTags.create(new ResourceLocation("forge", name));

        }
    }
}
