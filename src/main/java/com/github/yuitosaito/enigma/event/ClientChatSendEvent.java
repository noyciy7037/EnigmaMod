package com.github.yuitosaito.enigma.event;

import cpw.mods.fml.common.eventhandler.Cancelable;
import cpw.mods.fml.common.eventhandler.Event;

@Cancelable
public class ClientChatSendEvent extends Event {
    public String message;
    public ClientChatSendEvent(String message) {
        this.message = message;
    }
}