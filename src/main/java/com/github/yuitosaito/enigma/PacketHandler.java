package com.github.yuitosaito.enigma;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;


public class PacketHandler {

    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel("enigma");

    public static void init() {
        INSTANCE.registerMessage(MessageKeyHandler.class, MessageKey.class, 0, Side.SERVER);
        INSTANCE.registerMessage(MessageKeyHandler.class, MessageKey.class, 1, Side.CLIENT);
    }
}