package com.github.yuitosaito.enigma;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraftforge.common.DimensionManager;

public class MessageKeyHandler implements IMessageHandler<MessageKey, IMessage> {

    @Override//IMessageHandlerのメソッド
    public IMessage onMessage(MessageKey message, MessageContext ctx) {
        if (ctx.side == Side.SERVER) {
            if (message.type == 0) {
                return new MessageKey((byte) 1, message.key, DimensionManager.getCurrentSaveRootDirectory().getAbsolutePath());
            } else if (message.type == 2) {
                return new MessageKey((byte) 3, DimensionManager.getCurrentSaveRootDirectory().getAbsolutePath());
            } else if (message.type == 4) {
                return new MessageKey((byte) 5, DimensionManager.getCurrentSaveRootDirectory().getAbsolutePath());
            }
        } else {
            if (message.type == 1) {
                KeyController.saveKey(message.name, message.key);
                EnigmaMOD.minecraft.thePlayer.addChatMessage(new ChatComponentTranslation("Enigma KEY Set."));
            }
            if (message.type == 3) {
                String key = KeyController.getKey(message.name);
                if(key != null){
                EnigmaMOD.minecraft.thePlayer.addChatMessage(new ChatComponentTranslation("Enigma KEY :" + key));
                }else{
                    EnigmaMOD.minecraft.thePlayer.addChatMessage(new ChatComponentTranslation("Enigma KEY Not Set."));
                }
            }
            if (message.type == 5){EnigmaMOD.key = KeyController.getKey(message.name);}
        }
        return null;
    }
}