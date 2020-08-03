package com.github.yuitosaito.enigma;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.config.Configuration;


public class EnigmaModConfigCore {
    public static final String MODE = "Mode";
    public static Configuration cfg;
    public static boolean isNeutral = false;


    public static void loadConfig(FMLPreInitializationEvent event) {
        cfg = new Configuration(event.getSuggestedConfigurationFile(), EnigmaMOD.MOD_VERSION, true);
        initConfig();
        syncConfig();
    }

    private static void initConfig() {
        cfg.addCustomCategoryComment(MODE, "A settings of Enigma Mode.");
        cfg.setCategoryLanguageKey(MODE, "config.enigma.category.mode");
    }

    public static void syncConfig() {
        isNeutral = cfg.getBoolean("enabledNeutralMode", MODE, isNeutral, "If true, you will not be able to send ciphertext.", "config.enigma.prop.enabledNeutralMode");
        cfg.save();
    }
}