package com.github.yuitosaito.enigma;

import com.github.yuitosaito.enigma.event.ClientChatSendEvent;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

public class EnigmaModEventHandler {
    @SubscribeEvent
    public void ClientChatReceivedEvent(ClientChatReceivedEvent e) {
        if (EnigmaMOD.mode == 0) return;
        String unformattedText = e.message.getUnformattedTextForChat();
        String ft = e.message.getUnformattedText();
        String message = ft.substring(unformattedText.length());
        if (message.equals("")) return;
        if (message.charAt(0) != '=') return;
        message = message.substring(1);
        String username = unformattedText.substring(1, unformattedText.length() - 2).replaceAll('\u00a7' + ".", "");
        if (EnigmaMOD.minecraft.thePlayer.getDisplayName().replaceAll('\u00a7' + ".", "").equals(username))
            return;
        for (int j = 0; j < EnigmaMOD.key.size(); ++j) {
            Calendar cl = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            Random random = new Random(Integer.parseInt(sdf.format(cl.getTime()).replace("/", "")));
            int rv = random.nextInt(1000000);
            IvParameterSpec iv = Crypt.getIv(sdf.format(cl.getTime()) + String.format("%06d", rv));
            SecretKeySpec sk = Crypt.getKey(EnigmaMOD.key.get(j));
            if (message.length() > 9) {
                if (message.substring(message.length() - 9).matches("[0-9]{4}=END=")) {
                    String rme = message.substring(0, message.length() - 9);
                    String id = message.substring(message.length() - 9, message.length() - 5);
                    //{"USER_NAME","ID","Message"...}
                    if (EnigmaMOD.messages.size() >= 3) {
                        if (!EnigmaMOD.messages.get(0).equals(username) || !EnigmaMOD.messages.get(1).equals(id)) {
                            EnigmaMOD.messages.clear();
                            EnigmaMOD.messages.add(0, username);
                            EnigmaMOD.messages.add(1, id);
                            EnigmaMOD.messages.add(2, rme);
                        } else {
                            EnigmaMOD.messages.add(rme);
                        }
                    } else {
                        EnigmaMOD.messages.clear();
                        EnigmaMOD.messages.add(0, username);
                        EnigmaMOD.messages.add(1, id);
                        EnigmaMOD.messages.add(2, rme);
                    }
                    StringBuilder builder = new StringBuilder();
                    for (int i = 2; i < EnigmaMOD.messages.size(); ++i) {
                        builder.append(EnigmaMOD.messages.get(i));
                    }
                    String dec = Crypt.decryptToken(builder.toString(), iv, sk);
                    if (dec == null) continue;
                    ChatComponentText rawChatText = new ChatComponentText(e.message.getUnformattedText());
                    rawChatText.getChatStyle().setColor(EnumChatFormatting.GRAY);
                    EnigmaMOD.minecraft.thePlayer.addChatMessage(rawChatText);
                    e.message = new ChatComponentText(unformattedText + dec);
                    EnigmaMOD.messages.clear();
                    return;
                }
                if (message.substring(message.length() - 5).matches("[0-9]{4}=")) {
                    String rme = message.substring(0, message.length() - 5);
                    String id = message.substring(message.length() - 5, message.length() - 1);
                    //{"USER_NAME","ID","Message"...}
                    if (EnigmaMOD.messages.size() >= 3) {
                        if (!EnigmaMOD.messages.get(0).equals(username) || !EnigmaMOD.messages.get(1).equals(id)) {
                            EnigmaMOD.messages.clear();
                            EnigmaMOD.messages.add(0, username);
                            EnigmaMOD.messages.add(1, id);
                            EnigmaMOD.messages.add(2, rme);
                        } else {
                            EnigmaMOD.messages.add(rme);
                        }
                    } else {
                        EnigmaMOD.messages.clear();
                        EnigmaMOD.messages.add(0, username);
                        EnigmaMOD.messages.add(1, id);
                        EnigmaMOD.messages.add(2, rme);
                    }
                    e.message.getChatStyle().setColor(EnumChatFormatting.GRAY);
                    return;
                }
            }
            String dec = Crypt.decryptToken(message, iv, sk);
            if (dec == null) continue;
            ChatComponentText cct = new ChatComponentText(message);
            cct.getChatStyle().setColor(EnumChatFormatting.GRAY);
            EnigmaMOD.minecraft.thePlayer.addChatMessage(cct);
            e.message = new ChatComponentText(unformattedText + dec);
        }
    }

    @SubscribeEvent
    public void ClientChatSendEvent(ClientChatSendEvent e) {
        if (EnigmaMOD.mode == 0 || e.message.indexOf("/") == 0) return;
        if (EnigmaMOD.mode == 2) return;
        if (EnigmaModConfigCore.isNeutral) return;
        if (EnigmaMOD.key.size() < 1) return;
        Calendar cl = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Random random = new Random(Integer.parseInt(sdf.format(cl.getTime()).replace("/", "")));
        int rv = random.nextInt(1000000);
        IvParameterSpec iv = Crypt.getIv(sdf.format(cl.getTime()) + String.format("%06d", rv));
        SecretKeySpec sk = Crypt.getKey(EnigmaMOD.key.get(0));
        String message = null;
        try {
            message = Crypt.encryptToken(e.message, iv, sk);
        } catch (InvalidAlgorithmParameterException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException noSuchPaddingException) {
            noSuchPaddingException.printStackTrace();
        }
        if (message == null) return;
        ChatComponentText mes = new ChatComponentText(e.message);
        mes.getChatStyle().setColor(EnumChatFormatting.GRAY);
        if (message.getBytes().length > 101) {
            EnigmaMOD.minecraft.thePlayer.addChatMessage(mes);
            Random r = new Random();
            int ri = r.nextInt(10000);
            for (int i = 0; i < message.length() / 60 + 1; ++i) {
                if ((i + 1) >= message.length() / 60 + 1) {
                    if (message.length() >= (i + 1) * 60)
                        //noinspection DuplicateExpressions
                        EnigmaMOD.minecraft.thePlayer.sendQueue.addToSendQueue(new C01PacketChatMessage("=" + message.substring(i * 60, (i + 1) * 60) + String.format("%04d", ri) + "=END="));
                    else
                        EnigmaMOD.minecraft.thePlayer.sendQueue.addToSendQueue(new C01PacketChatMessage("=" + message.substring(i * 60) + String.format("%04d", ri) + "=END="));
                } else {
                    if (message.length() >= (i + 1) * 60)
                        //noinspection DuplicateExpressions
                        EnigmaMOD.minecraft.thePlayer.sendQueue.addToSendQueue(new C01PacketChatMessage("=" + message.substring(i * 60, (i + 1) * 60) + String.format("%04d", ri) + "="));
                    else
                        EnigmaMOD.minecraft.thePlayer.sendQueue.addToSendQueue(new C01PacketChatMessage("=" + message.substring(i * 60) + String.format("%04d", ri) + "="));
                }
            }
            e.setCanceled(true);
        } else {
            EnigmaMOD.minecraft.thePlayer.addChatMessage(mes);
            e.message = "=" + message;

        }
    }

    @SubscribeEvent
    public void EntityJoinWorldEvent(EntityJoinWorldEvent e) {
        if (!e.world.isRemote) return;
        if (e.entity instanceof EntityPlayer) {
            if (EnigmaMOD.minecraft.thePlayer == null || ((EntityPlayer) e.entity).getDisplayName().replaceAll('\u00a7' + ".", "").equals(EnigmaMOD.minecraft.thePlayer.getDisplayName().replaceAll('\u00a7' + ".", ""))) {
                KeyController.refreshKey();
                if (EnigmaMOD.worldName == null) return;
                boolean isReset = false;
                if (EnigmaMOD.ipAddress != null) {
                    //server
                    if (EnigmaMOD.minecraft.func_147104_D() != null) {
                        if (!EnigmaMOD.ipAddress.equals(EnigmaMOD.minecraft.func_147104_D().serverIP) ||
                                !EnigmaMOD.worldName.equals(EnigmaMOD.minecraft.func_147104_D().serverName))
                            isReset = true;
                    } else {
                        PacketHandler.INSTANCE.sendToServer(new MessageKey((byte) 10, EnigmaMOD.worldName));
                        return;
                    }
                } else {
                    //client
                    if (EnigmaMOD.minecraft.func_147104_D() == null) {
                        PacketHandler.INSTANCE.sendToServer(new MessageKey((byte) 10, EnigmaMOD.worldName));
                        return;
                    } else {
                        isReset = true;
                    }
                }
                if (isReset)
                    resetEnigmaMode(EnigmaMOD.minecraft.func_147104_D().serverIP, EnigmaMOD.minecraft.func_147104_D().serverName);
            }
        }
    }

    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        // コンフィグが変更された時に呼ばれる。
        if (event.modID.equals(EnigmaMOD.MOD_ID))
            EnigmaModConfigCore.syncConfig();
    }

    @SubscribeEvent
    public void onRenderGui(RenderGameOverlayEvent.Post event)
    {
        if (event.type != RenderGameOverlayEvent.ElementType.EXPERIENCE) return;
        if (EnigmaModConfigCore.isDisplayGui)
            EnigmaMOD.enigmaModGuiIngame.renderGameOverlay();
    }

    public static void resetEnigmaMode(String ip, String name) {
        EnigmaMOD.worldName = name;
        EnigmaMOD.ipAddress = ip;
        if (EnigmaMOD.mode != 0) {
            EnigmaMOD.mode = 0;
            EnigmaMOD.minecraft.thePlayer.addChatMessage(new ChatComponentTranslation("Enigma Disabled."));
        }
    }
}
