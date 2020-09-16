package com.github.yuitosaito.enigma;

import com.github.yuitosaito.enigma.gui.EnigmaModGuiIngame;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.EventBus;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;

import java.util.ArrayList;
import java.util.List;

@Mod(modid = EnigmaMOD.MOD_ID, name = EnigmaMOD.MOD_NAME, version = EnigmaMOD.MOD_VERSION, guiFactory = "com.github.yuitosaito.enigma.EnigmaModGuiFactory")
public class EnigmaMOD {
    public static Minecraft minecraft;
    public static final String MOD_ID = "enigma";
    public static final String MOD_NAME = "Enigma MOD";
    public static final String MOD_VERSION = Constants.version;
    public static final EventBus EVENT_BUS = new EventBus();

    public static List<String> key = null;
    public static String worldName;
    public static String ipAddress;
    public static int mode = 0;
    public static EnigmaModGuiIngame enigmaModGuiIngame;

    public static List<String> messages = new ArrayList<>();

    @Mod.Instance("enigma")
    public static EnigmaMOD instance;

    @EventHandler
    public void preInit(FMLPreInitializationEvent e) {
        EnigmaModEventHandler eventHandler = new EnigmaModEventHandler();
        MinecraftForge.EVENT_BUS.register(eventHandler);
        FMLCommonHandler.instance().bus().register(eventHandler);
        EVENT_BUS.register(eventHandler);

        ClientCommandHandler.instance.registerCommand(new CommandEnigma());
        PacketHandler.init();

        EnigmaModConfigCore.loadConfig(e);
    }

    @EventHandler
    public void init(FMLInitializationEvent e){
        minecraft = Minecraft.getMinecraft();
        enigmaModGuiIngame = new EnigmaModGuiIngame(minecraft);
    }
}