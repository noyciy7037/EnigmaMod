package com.github.yuitosaito.enigma;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;

public class MessageKey implements IMessage {

    public byte type;
    public String name;
    public String key;

    public MessageKey(){}

    public MessageKey(byte type) {
        this.type = type;
    }

    public MessageKey(byte type, String keyorname) {
        this.type = type;
        if(type == 0 || type == 1)
        this.key = keyorname;
        if(type == 2 || type == 3 || type == 5)
            this.name = keyorname;
    }

    public MessageKey(byte type, String key, String name) {
        this.type = type;
        this.name = name;
        this.key = key;
    }

    @Override//IMessageのメソッド。ByteBufからデータを読み取る。
    public void fromBytes(ByteBuf buf) {
        this.type = buf.readByte();
        if (this.type == 0 || this.type == 1)
            this.key = ByteBufUtils.readUTF8String(buf);
        if (this.type == 1 || this.type == 3 || this.type == 5)
            this.name = ByteBufUtils.readUTF8String(buf);
    }

    @Override//IMessageのメソッド。ByteBufにデータを書き込む。
    public void toBytes(ByteBuf buf) {
        buf.writeByte(this.type);
        if (this.type == 0 || this.type == 1)
            ByteBufUtils.writeUTF8String(buf, this.key);
        if (this.type == 1 || this.type == 3 || this.type == 5)
            ByteBufUtils.writeUTF8String(buf, this.name);
    }
}