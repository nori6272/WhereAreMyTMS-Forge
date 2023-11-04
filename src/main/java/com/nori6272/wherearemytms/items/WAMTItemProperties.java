package com.nori6272.wherearemytms.items;

import com.nori6272.wherearemytms.WhereAreMyTMs;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class WAMTItemProperties {
    public static void createTypes() {
        HashMap<String, String> types = new HashMap<>();
        types.put("normal", "Normal");
        types.put("fire", "Fire");
        types.put("water", "Water");
        types.put("grass", "Grass");
        types.put("electric", "Electric");
        types.put("ice", "Ice");
        types.put("fighting", "Fighting");
        types.put("poison", "Poison");
        types.put("ground", "Ground");
        types.put("flying", "Flying");
        types.put("psychic", "Psychic");
        types.put("bug", "Bug");
        types.put("rock", "Rock");
        types.put("ghost", "Ghost");
        types.put("dragon", "Dragon");
        types.put("dark", "Dark");
        types.put("steel", "Steel");
        types.put("fairy", "Fairy");
        for (RegistryObject<Item> itemRegObj : WAMTItems.ITEMS.getEntries()) {
            Item item = itemRegObj.get();
            if (item instanceof BasePokemonTM) {
                for (Map.Entry<String, String> entry : types.entrySet()) {
                    ItemProperties.register(item, WhereAreMyTMs.prefix(entry.getKey()), (itemStack, clientWorld, livingEntity, seed) -> {
                        CompoundTag nbtCompound = itemStack.getOrCreateTag();
                        if (!nbtCompound.contains("type")) {
                            return 0;
                        }
                        return Objects.equals(nbtCompound.getString("type"), entry.getValue()) ? 1 : 0;
                    });
                }
            }
        }
    }
}
