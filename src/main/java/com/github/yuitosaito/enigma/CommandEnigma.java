package com.github.yuitosaito.enigma;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.StatCollector;

import java.util.List;

@SuppressWarnings("SpellCheckingInspection")
public class CommandEnigma extends CommandBase {

    @Override
    public String getCommandName() {
        return "enigma";
    }

    @Override
    public String getCommandUsage(ICommandSender p_71518_1_) {
        return "COMMAND USAGE";
    }

    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public void processCommand(ICommandSender var1, String[] var2) {
        if (var2.length == 0) {
            if (EnigmaMOD.mode == 2 || EnigmaModConfigCore.isNeutral) {
                var1.addChatMessage(new ChatComponentTranslation(StatCollector.translateToLocal("chat.log.enigma.mode.listenenabled")));
            } else if (EnigmaMOD.mode == 1) {
                var1.addChatMessage(new ChatComponentTranslation(StatCollector.translateToLocal("chat.log.enigma.mode.enabled")));
            } else {
                var1.addChatMessage(new ChatComponentTranslation(StatCollector.translateToLocal("chat.log.enigma.mode.disabled")));
            }
        }
        if (var2.length >= 1) {
            switch (var2[0]) {
                case "getkey":
                    if (EnigmaMOD.minecraft.func_147104_D() != null) {
                        List<String> key = KeyController.getKey(EnigmaMOD.minecraft.func_147104_D().serverIP, EnigmaMOD.minecraft.func_147104_D().serverName);
                        if (key != null && key.size() > 0) {
                            for (int i = 0; i < key.size(); ++i)
                                var1.addChatMessage(new ChatComponentTranslation(
                                        StatCollector.translateToLocal("chat.log.enigma.key.get")
                                                .replace("%1", "" + i).replace("%2", key.get(i)) +
                                                (i == 0 ? StatCollector.translateToLocal("chat.log.enigma.key.get.main") : "")));
                        } else {
                            var1.addChatMessage(new ChatComponentTranslation(StatCollector.translateToLocal("chat.log.enigma.key.get.not")));
                        }
                    } else {
                        PacketHandler.INSTANCE.sendToServer(new MessageKey((byte) 2));
                    }
                    return;
                case "enable":
                    if (EnigmaMOD.key != null) {
                        if (!EnigmaModConfigCore.isNeutral) {
                            EnigmaMOD.mode = 1;
                            var1.addChatMessage(new ChatComponentTranslation(StatCollector.translateToLocal("chat.log.enigma.mode.enabled")));
                        } else {
                            EnigmaMOD.mode = 2;
                            var1.addChatMessage(new ChatComponentTranslation(StatCollector.translateToLocal("chat.log.enigma.mode.listenenabled")));
                        }
                    } else {
                        var1.addChatMessage(new ChatComponentTranslation(StatCollector.translateToLocal("chat.log.enigma.key.get.not")));
                    }
                    break;
                case "enablelisten":
                    if (EnigmaMOD.key != null) {
                        EnigmaMOD.mode = 2;
                        var1.addChatMessage(new ChatComponentTranslation(StatCollector.translateToLocal("chat.log.enigma.mode.listenenabled")));
                    } else {
                        var1.addChatMessage(new ChatComponentTranslation(StatCollector.translateToLocal("chat.log.enigma.key.get.not")));
                    }
                    break;
                case "disable":
                    EnigmaMOD.mode = 0;
                    var1.addChatMessage(new ChatComponentTranslation(StatCollector.translateToLocal("chat.log.enigma.mode.disabled")));
                    break;
            }
        }
        if (var2.length >= 2) {
            switch (var2[0]) {
                case "addkey":
                    if (var2[1].length() != 16) {
                        var1.addChatMessage(new ChatComponentTranslation(StatCollector.translateToLocal("chat.log.enigma.key.add.len")));
                        return;
                    }
                    if (EnigmaMOD.minecraft.func_147104_D() != null) {
                        KeyController.saveKey(EnigmaMOD.minecraft.func_147104_D().serverIP,
                                EnigmaMOD.minecraft.func_147104_D().serverName,
                                var2[1]);
                        var1.addChatMessage(new ChatComponentTranslation(StatCollector.translateToLocal("chat.log.enigma.key.add.added")));
                    } else {
                        PacketHandler.INSTANCE.sendToServer(new MessageKey((byte) 0, var2[1]));
                    }
                    break;
                case "removekey":
                    if (!IntegerUtils.isInteger(var2[1])) {
                        var1.addChatMessage(new ChatComponentTranslation(StatCollector.translateToLocal("chat.log.enigma.key.index")));
                        return;
                    }
                    if (EnigmaMOD.minecraft.func_147104_D() != null) {
                        KeyController.removeKey(EnigmaMOD.minecraft.func_147104_D().serverIP,
                                EnigmaMOD.minecraft.func_147104_D().serverName,
                                Integer.parseInt(var2[1]));
                        var1.addChatMessage(new ChatComponentTranslation(StatCollector.translateToLocal("chat.log.enigma.key.remove.removed")));
                    } else {
                        PacketHandler.INSTANCE.sendToServer(new MessageKey((byte) 6, var2[1]));
                    }
                    break;
                case "setmain":
                    if (!IntegerUtils.isInteger(var2[1])) {
                        var1.addChatMessage(new ChatComponentTranslation(StatCollector.translateToLocal("chat.log.enigma.key.index")));
                        return;
                    }
                    if (EnigmaMOD.minecraft.func_147104_D() != null) {
                        KeyController.setMainKey(EnigmaMOD.minecraft.func_147104_D().serverIP,
                                EnigmaMOD.minecraft.func_147104_D().serverName,
                                Integer.parseInt(var2[1]));
                        var1.addChatMessage(new ChatComponentTranslation(StatCollector.translateToLocal("chat.log.enigma.key.setmain")));
                    } else {
                        PacketHandler.INSTANCE.sendToServer(new MessageKey((byte) 8, var2[1]));
                    }
                    break;
            }
        }
    }

    @SuppressWarnings("rawtypes")
    @Override
    public List addTabCompletionOptions(ICommandSender sender, String[] args) {
        if (args.length == 1) {
            return getListOfStringsMatchingLastWord(args, "enable", "disable", "enablelisten", "addkey", "getkey", "removekey", "setmain");
        }
        return null;
    }
}