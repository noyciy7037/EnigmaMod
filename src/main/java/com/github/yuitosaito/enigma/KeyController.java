package com.github.yuitosaito.enigma;

import com.google.gson.Gson;

import java.io.*;

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
                    f.mkdir();
                f = new File(f.getAbsolutePath() + File.separator + "keymp.json");
                Gson gson = new Gson();
                if (f.exists()) {
                    String out = "";
                    try (BufferedReader br = new BufferedReader(new FileReader(f))) {
                        String text;
                        while ((text = br.readLine()) != null) {
                            out += text;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    int mi = getMultiindex(ip,name);
                    Multi m = gson.fromJson(out, Multi.class);
                    if(mi != -1) {m.datas.get(mi).key = key;}
                    else{m.datas.add(new AMulti(name, ip, key));}
                    out = gson.toJson(m);
                    try {
                        FileWriter fw = new FileWriter(f);
                        fw.write(out);
                        fw.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    Multi m = new Multi();
                    m.datas.add(new AMulti(name, ip, key));
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
                    f.mkdir();
                f = new File(f.getAbsolutePath() + File.separator + "key.json");
                Gson gson = new Gson();
                Single data = new Single(key);
                try {
                    FileWriter fw = new FileWriter(f);
                    fw.write(gson.toJson(data));
                    fw.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
        refreshKey();
    }

    public static String getKey(String name) {
        return getKey(null, name);
    }

    public static String getKey(String ip, String name) {
        if (ip != null) {
            //server
            File f = EnigmaMOD.minecraft.mcDataDir;
            f = new File(f.getAbsolutePath() + File.separator + "enigma" + File.separator + "keymp.json");
            String out = "";
            if (f.exists() && f.isFile()) {
                Gson gson = new Gson();
                try (BufferedReader br = new BufferedReader(new FileReader(f))) {
                    String text;
                    while ((text = br.readLine()) != null) {
                        out += text;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Multi data = gson.fromJson(out, Multi.class);
                for(int i = 0;i<data.datas.size();++i){
                    if(data.datas.get(i).ip.equals(ip)){
                        if(data.datas.get(i).name.equals(name))return data.datas.get(i).key;
                    }
                }
            }
        } else {
            //single play
            File f = new File(name + File.separator + "enigma" + File.separator + "key.json");
            String out = "";
            if (f.exists() && f.isFile()) {
                Gson gson = new Gson();
                try (BufferedReader br = new BufferedReader(new FileReader(f))) {
                    String text;
                    while ((text = br.readLine()) != null) {
                        out += text;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Single data = gson.fromJson(out, Single.class);
                return data.key;
            }
        }
        return null;
    }

    public static int getMultiindex(String ip,String name){
        File f = EnigmaMOD.minecraft.mcDataDir;
        f = new File(f.getAbsolutePath() + File.separator + "enigma" + File.separator + "keymp.json");
        String out = "";
        if (f.exists() && f.isFile()) {
            Gson gson = new Gson();
            try (BufferedReader br = new BufferedReader(new FileReader(f))) {
                String text;
                while ((text = br.readLine()) != null) {
                    out += text;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            Multi data = gson.fromJson(out, Multi.class);
            for(int i = 0;i<data.datas.size();++i){
                if(data.datas.get(i).ip.equals(ip)){
                    if(data.datas.get(i).name.equals(name))return i;
                }
            }
        }
        return -1;
    }

    public static void refreshKey(){
        if (EnigmaMOD.minecraft.func_147104_D() != null) {
            EnigmaMOD.key = KeyController.getKey(EnigmaMOD.minecraft.func_147104_D().serverIP, EnigmaMOD.minecraft.func_147104_D().serverName);
        } else {
            PacketHandler.INSTANCE.sendToServer(new MessageKey((byte) 4));
        }
    }
}
