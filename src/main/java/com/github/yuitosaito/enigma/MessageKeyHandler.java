package com.github.yuitosaito.enigma;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.DimensionManager;

import java.util.List;

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
            } else if (message.type == 6) {
                return new MessageKey((byte) 7, message.key, DimensionManager.getCurrentSaveRootDirectory().getAbsolutePath());
            } else if (message.type == 8) {
                return new MessageKey((byte) 9, message.key, DimensionManager.getCurrentSaveRootDirectory().getAbsolutePath());
            } else if (message.type == 10) {
                return new MessageKey((byte) 11, message.key, DimensionManager.getCurrentSaveRootDirectory().getAbsolutePath());
            }
        } else {
            if (message.type == 1) {
                KeyController.saveKey(message.name, message.key);
                EnigmaMOD.minecraft.thePlayer.addChatMessage(new ChatComponentTranslation(StatCollector.translateToLocal("chat.log.enigma.key.add.added")));
            }
            if (message.type == 3) {
                List<String> key = KeyController.getKey(message.name);
                if (key != null && key.size() > 0) {
                    for (int i = 0; i < key.size(); ++i)
                        EnigmaMOD.minecraft.thePlayer.addChatMessage(new ChatComponentTranslation(
                                StatCollector.translateToLocal("chat.log.enigma.key.get")
                                        .replace("%1", "" + i).replace("%2", key.get(i)) +
                                        (i == 0 ? StatCollector.translateToLocal("chat.log.enigma.key.get.main") : "")));
                } else {
                    EnigmaMOD.minecraft.thePlayer.addChatMessage(new ChatComponentTranslation(StatCollector.translateToLocal("chat.log.enigma.key.get.not")));
                }
            }
            if (message.type == 5) {
                EnigmaMOD.key = KeyController.getKey(message.name);
            }
            if (message.type == 7) {
                KeyController.removeKey(message.name, Integer.parseInt(message.key));
                EnigmaMOD.minecraft.thePlayer.addChatMessage(new ChatComponentTranslation(StatCollector.translateToLocal("chat.log.enigma.key.remove.removed")));
            }
            if (message.type == 9) {
                KeyController.setMainKey(message.name, Integer.parseInt(message.key));
                EnigmaMOD.minecraft.thePlayer.addChatMessage(new ChatComponentTranslation(StatCollector.translateToLocal("chat.log.enigma.key.setmain")));
            }
            if (message.type == 11) {
                if (!message.name.equals(EnigmaMOD.worldName)) {
                    EnigmaModEventHandler.resetEnigmaMode(null, message.name);
                }
            }
        }
        return null;
    }
}