package com.github.yuitosaito.enigma;

import java.util.ArrayList;
import java.util.List;

public class JsonContent {
}

class Single {
    public String key;

    public Single(String key) {
        this.key = key;
    }
}

class Multi {
    public List<AMulti> datas;

    public Multi() {
        this.datas = new ArrayList<AMulti>();
    }
}

class AMulti {
    public String name;
    public String ip;
    public String key;

    public AMulti(String name, String ip, String key) {
        this.name = name;
        this.ip = ip;
        this.key = key;
    }
}