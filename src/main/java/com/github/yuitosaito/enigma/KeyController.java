package com.github.yuitosaito.enigma;

import com.google.gson.Gson;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class KeyController {
    public static void saveKey(String name, String key) {
        saveKey(null, name, key);
    }

    public static void saveKey(String ip, String name, String key) {
        if (ip != null) {
            //server
            File f = EnigmaMOD.minecraft.mcDataDir;
            if (f.exists() && f.isDirectory()) {
                f = new File(f.getAbsolutePath() + File.separator + "enigma");
                if (!f.exists())
                    if (!f.mkdir()) return;
                f = new File(f.getAbsolutePath() + File.separator + "keymp.json");
                Gson gson = new Gson();
                if (f.exists()) {
                    StringBuilder out = new StringBuilder();
                    try (BufferedReader br = new BufferedReader(new FileReader(f))) {
                        String text;
                        while ((text = br.readLine()) != null) {
                            out.append(text);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    int mi = getMultiIndex(ip, name);
                    Multi m = gson.fromJson(out.toString(), Multi.class);
                    if (mi != -1) {
                        m.datas.get(mi).key.add(key);
                    } else {
                        List<String> keys = new ArrayList<>();
                        keys.add(key);
                        m.datas.add(new AMulti(name, ip, keys));
                    }
                    m.version = EnigmaMOD.MOD_VERSION;
                    out = new StringBuilder(gson.toJson(m));
                    try {
                        FileWriter fw = new FileWriter(f);
                        fw.write(out.toString());
                        fw.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    Multi m = new Multi();
                    List<String> keys = new ArrayList<>();
                    keys.add(key);
                    m.datas.add(new AMulti(name, ip, keys));
                    m.version = EnigmaMOD.MOD_VERSION;
                    try {
                        FileWriter fw = new FileWriter(f);
                        fw.write(gson.toJson(m));
                        fw.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        } else {
            //single play
            File f = new File(name);
            if (f.exists() && f.isDirectory()) {
                f = new File(f.getAbsolutePath() + File.separator + "enigma");
                if (!f.exists())
                    if (!f.mkdir()) return;
                f = new File(f.getAbsolutePath() + File.separator + "key.json");
                if (f.exists()) {
                    StringBuilder out = new StringBuilder();
                    try (BufferedReader br = new BufferedReader(new FileReader(f))) {
                        String text;
                        while ((text = br.readLine()) != null) {
                            out.append(text);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Gson gson = new Gson();
                    Single data = gson.fromJson(out.toString(), Single.class);
                    data.key.add(key);
                    data.version = EnigmaMOD.MOD_VERSION;
                    try {
                        FileWriter fw = new FileWriter(f);
                        fw.write(gson.toJson(data));
                        fw.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    Gson gson = new Gson();
                    List<String> keys = new ArrayList<>();
                    keys.add(key);
                    Single data = new Single(keys);
                    data.version = EnigmaMOD.MOD_VERSION;
                    try {
                        FileWriter fw = new FileWriter(f);
                        fw.write(gson.toJson(data));
                        fw.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
        refreshKey();
    }

    public static void removeKey(String name, int index) {
        removeKey(null, name, index);
    }

    public static void removeKey(String ip, String name, int index) {
        if (ip != null) {
            //server
            File f = EnigmaMOD.minecraft.mcDataDir;
            if (f.exists() && f.isDirectory()) {
                f = new File(f.getAbsolutePath() + File.separator + "enigma");
                if (!f.exists())
                    if (!f.mkdir()) return;
                f = new File(f.getAbsolutePath() + File.separator + "keymp.json");
                Gson gson = new Gson();
                if (f.exists()) {
                    StringBuilder out = new StringBuilder();
                    try (BufferedReader br = new BufferedReader(new FileReader(f))) {
                        String text;
                        while ((text = br.readLine()) != null) {
                            out.append(text);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    int mi = getMultiIndex(ip, name);
                    Multi m = gson.fromJson(out.toString(), Multi.class);
                    if (mi != -1) {
                        if (m.datas.get(mi).key.size() > index)
                            m.datas.get(mi).key.remove(index);
                    }
                    m.version = EnigmaMOD.MOD_VERSION;
                    out = new StringBuilder(gson.toJson(m));
                    try {
                        FileWriter fw = new FileWriter(f);
                        fw.write(out.toString());
                        fw.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        } else {
            //single play
            File f = new File(name);
            if (f.exists() && f.isDirectory()) {
                f = new File(f.getAbsolutePath() + File.separator + "enigma");
                if (!f.exists())
                    if (!f.mkdir()) return;
                f = new File(f.getAbsolutePath() + File.separator + "key.json");
                if (f.exists()) {
                    StringBuilder out = new StringBuilder();
                    try (BufferedReader br = new BufferedReader(new FileReader(f))) {
                        String text;
                        while ((text = br.readLine()) != null) {
                            out.append(text);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Gson gson = new Gson();
                    Single data = gson.fromJson(out.toString(), Single.class);
                    if (data.key.size() > index)
                        data.key.remove(index);
                    data.version = EnigmaMOD.MOD_VERSION;
                    try {
                        FileWriter fw = new FileWriter(f);
                        fw.write(gson.toJson(data));
                        fw.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
        refreshKey();
    }

    public static void setMainKey(String name, int index) {
        setMainKey(null, name, index);
    }

    public static void setMainKey(String ip, String name, int index) {
        if (ip != null) {
            //server
            File f = EnigmaMOD.minecraft.mcDataDir;
            if (f.exists() && f.isDirectory()) {
                f = new File(f.getAbsolutePath() + File.separator + "enigma");
                if (!f.exists())
                    if (!f.mkdir()) return;
                f = new File(f.getAbsolutePath() + File.separator + "keymp.json");
                Gson gson = new Gson();
                if (f.exists()) {
                    StringBuilder out = new StringBuilder();
                    try (BufferedReader br = new BufferedReader(new FileReader(f))) {
                        String text;
                        while ((text = br.readLine()) != null) {
                            out.append(text);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    int mi = getMultiIndex(ip, name);
                    Multi m = gson.fromJson(out.toString(), Multi.class);
                    if (mi != -1) {
                        if (m.datas.get(mi).key.size() > index) {
                            List<String> keys = m.datas.get(mi).key;
                            String mainKey = keys.get(index);
                            keys.set(index, keys.get(0));
                            keys.set(0, mainKey);
                        }
                    }
                    m.version = EnigmaMOD.MOD_VERSION;
                    out = new StringBuilder(gson.toJson(m));
                    try {
                        FileWriter fw = new FileWriter(f);
                        fw.write(out.toString());
                        fw.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        } else {
            //single play
            File f = new File(name);
            if (f.exists() && f.isDirectory()) {
                f = new File(f.getAbsolutePath() + File.separator + "enigma");
                if (!f.exists())
                    if (!f.mkdir()) return;
                f = new File(f.getAbsolutePath() + File.separator + "key.json");
                if (f.exists()) {
                    StringBuilder out = new StringBuilder();
                    try (BufferedReader br = new BufferedReader(new FileReader(f))) {
                        String text;
                        while ((text = br.readLine()) != null) {
                            out.append(text);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Gson gson = new Gson();
                    Single data = gson.fromJson(out.toString(), Single.class);
                    if (data.key.size() > index) {
                        List<String> keys = data.key;
                        String mainKey = keys.get(index);
                        keys.set(index, keys.get(0));
                        keys.set(0, mainKey);
                    }
                    data.version = EnigmaMOD.MOD_VERSION;
                    try {
                        FileWriter fw = new FileWriter(f);
                        fw.write(gson.toJson(data));
                        fw.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
        refreshKey();
    }

    public static List<String> getKey(String name) {
        return getKey(null, name);
    }

    public static List<String> getKey(String ip, String name) {
        if (ip != null) {
            //server
            File f = EnigmaMOD.minecraft.mcDataDir;
            f = new File(f.getAbsolutePath() + File.separator + "enigma" + File.separator + "keymp.json");
            StringBuilder out = new StringBuilder();
            if (f.exists() && f.isFile()) {
                Gson gson = new Gson();
                try (BufferedReader br = new BufferedReader(new FileReader(f))) {
                    String text;
                    while ((text = br.readLine()) != null) {
                        out.append(text);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Multi data = gson.fromJson(out.toString(), Multi.class);
                for (int i = 0; i < data.datas.size(); ++i) {
                    if (data.datas.get(i).ip.equals(ip)) {
                        if (data.datas.get(i).name.equals(name)) return data.datas.get(i).key;
                    }
                }
            }
        } else {
            //single play
            File f = new File(name + File.separator + "enigma" + File.separator + "key.json");
            StringBuilder out = new StringBuilder();
            if (f.exists() && f.isFile()) {
                Gson gson = new Gson();
                try (BufferedReader br = new BufferedReader(new FileReader(f))) {
                    String text;
                    while ((text = br.readLine()) != null) {
                        out.append(text);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Single data = gson.fromJson(out.toString(), Single.class);
                return data.key;
            }
        }
        return null;
    }

    public static int getMultiIndex(String ip, String name) {
        File f = EnigmaMOD.minecraft.mcDataDir;
        f = new File(f.getAbsolutePath() + File.separator + "enigma" + File.separator + "keymp.json");
        StringBuilder out = new StringBuilder();
        if (f.exists() && f.isFile()) {
            Gson gson = new Gson();
            try (BufferedReader br = new BufferedReader(new FileReader(f))) {
                String text;
                while ((text = br.readLine()) != null) {
                    out.append(text);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            Multi data = gson.fromJson(out.toString(), Multi.class);
            for (int i = 0; i < data.datas.size(); ++i) {
                if (data.datas.get(i).ip.equals(ip)) {
                    if (data.datas.get(i).name.equals(name)) return i;
                }
            }
        }
        return -1;
    }

    public static void refreshKey() {
        if (EnigmaMOD.minecraft.func_147104_D() != null) {
            EnigmaMOD.key = KeyController.getKey(EnigmaMOD.minecraft.func_147104_D().serverIP, EnigmaMOD.minecraft.func_147104_D().serverName);
        } else {
            PacketHandler.INSTANCE.sendToServer(new MessageKey((byte) 4));
        }
    }
}
