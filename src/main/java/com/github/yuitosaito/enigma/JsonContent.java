package com.github.yuitosaito.enigma;

import java.util.ArrayList;
import java.util.List;

class Single {
    public List<String> key;
    public String version;

    public Single(List<String> key) {
        this.key = key;
        this.version = EnigmaMOD.MOD_VERSION;
    }
}

class Multi {
    public List<AMulti> datas;
    public String version;

    public Multi() {
        this.datas = new ArrayList<>();
        this.version = EnigmaMOD.MOD_VERSION;
    }
}

class AMulti {
    public String name;
    public String ip;
    public List <String> key;

    public AMulti(String name, String ip, List<String> keys) {
        this.name = name;
        this.ip = ip;
        this.key = keys;
    }
}