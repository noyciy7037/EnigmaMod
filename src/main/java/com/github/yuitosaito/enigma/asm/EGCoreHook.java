package com.github.yuitosaito.enigma.asm;

import com.github.yuitosaito.enigma.EnigmaMOD;
import com.github.yuitosaito.enigma.event.ClientChatSendEvent;


@SuppressWarnings({"unused", "RedundantSuppression"})
public class EGCoreHook {
    public static String EGCoreStringHook(String s) {
        ClientChatSendEvent e = new ClientChatSendEvent(s);
        if (!EnigmaMOD.EVENT_BUS.post(e)){
            return e.message;
        }else {
            return null;
        }
    }
}