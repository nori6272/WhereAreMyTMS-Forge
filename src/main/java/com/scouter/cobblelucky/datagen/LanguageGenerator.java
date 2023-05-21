package com.scouter.cobblelucky.datagen;

import com.mojang.logging.LogUtils;
import com.scouter.cobblelucky.CobblemonOutbreaks;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraftforge.common.data.LanguageProvider;
import org.slf4j.Logger;

import java.util.function.Supplier;

public class LanguageGenerator extends LanguageProvider {
    public LanguageGenerator(DataGenerator gen){
        super(gen, CobblemonOutbreaks.MODID, "en_us");
    }
    private static final Logger LOGGER = LogUtils.getLogger();
    @Override
    protected void addTranslations(){
        add("cobblemonoutbreaks.portal_spawn_near", "An outbreak has spawned close to you with a ");
        add("cobblemonoutbreaks.gate_finished", "The outbreak with %s has finished!");
        add("cobblemonoutbreaks.gate_failed_spawning", "An outbreak with %s has been removed since no suitable spawning points could be found");
        add("cobblemonoutbreaks.gate_time_finished", "You have failed to finish the %s outbreak in time!");
    }

    @Override
    public String getName() {
        return "Cobblemon Outbreaks Languages: en_us";
    }

    public void addTabName(CreativeModeTab key, String name){
        add(key.getDisplayName().getString(), name);
    }

    public void add(CreativeModeTab key, String name) {
        add(key.getDisplayName().getString(), name);
    }

    public void addPotion(Supplier<? extends Potion> key, String name, String regName) {
        add(key.get(), name, regName);
    }

    public void add(Potion key, String name, String regName) {
        add("item.minecraft.potion.effect." + regName, name);
        add("item.minecraft.splash_potion.effect." + regName, "Splash " + name);
        add("item.minecraft.lingering_potion.effect." + regName, "Lingering " + name);
    }
}
