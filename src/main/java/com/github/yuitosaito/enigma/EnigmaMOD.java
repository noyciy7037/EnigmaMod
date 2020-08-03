package com.github.yuitosaito.enigma;

import com.github.yuitosaito.enigma.event.ClientChatSendEvent;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.EventBus;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

@Mod(modid = EnigmaMOD.MOD_ID, name = EnigmaMOD.MOD_NAME, version = EnigmaMOD.MOD_VERSION, guiFactory = "com.github.yuitosaito.enigma.EnigmaModGuiFactory")
public class EnigmaMOD {

    public static Minecraft minecraft;
    public static final String MOD_ID = "enigma";
    public static final String MOD_NAME = "Enigma MOD";
    public static final String MOD_VERSION = Constants.version;
    public static final EventBus EVENT_BUS = new EventBus();

    public static String key = null;
    public static int mode = 0;

    public static List<String> messages = new ArrayList<>();

    @Mod.Instance("enigma")
    public static EnigmaMOD instance;

    @EventHandler
    public void preInit(FMLPreInitializationEvent e) {
        EnigmaModEventHandler eventHandler = new EnigmaModEventHandler();
        MinecraftForge.EVENT_BUS.register(eventHandler);
        FMLCommonHandler.instance().bus().register(eventHandler);
        EVENT_BUS.register(eventHandler);

        minecraft = Minecraft.getMinecraft();
        ClientCommandHandler.instance.registerCommand(new CommandEnigma());
        PacketHandler.init();

        EnigmaModConfigCore.loadConfig(e);
    }
}