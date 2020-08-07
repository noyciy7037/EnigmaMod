package com.github.yuitosaito.enigma.event;

import cpw.mods.fml.common.eventhandler.Cancelable;
import cpw.mods.fml.common.eventhandler.Event;
import javafx.scene.input.KeyCode;

@Cancelable
public class ClientChatSendEvent extends Event {
    public String message;
    public ClientChatSendEvent(String message) {
        this.message = message;
    }
}