package com.scouter.wherearemytms.items;

import com.scouter.wherearemytms.WhereAreMyTMs;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.scouter.wherearemytms.WhereAreMyTMs.prefix;
import static com.scouter.wherearemytms.items.WAMTItems.machines;

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
        for (Item item : Registry.ITEM) {
            if (Registry.ITEM.getKey(item).getNamespace().equals(WhereAreMyTMs.MODID))
                if (item instanceof BasePokemonTM) {
                    for (Map.Entry<String, String> entry : types.entrySet()) {
                        ItemProperties.register(item, prefix(entry.getKey()), (itemStack, clientWorld, livingEntity, seed) -> {
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
