package com.github.yuitosaito.enigma.asm;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;

import java.util.Map;


public class EGCorePlugin implements IFMLLoadingPlugin {
    //書き換え機能を実装したクラス一覧を渡す関数。書き方はパッケージ名+クラス名。
    @Override
    public String[] getASMTransformerClass() {
        return new String[]{"com.github.yuitosaito.enigma.asm.EGCoreTransformer"};
    }


    //あとは今回は使わない為適当に。
    @Override
    public String getSetupClass() {
        return null;
    }


    @Override
    public void injectData(Map<String, Object> data) {
    }


    @Override
    public String getAccessTransformerClass() {
        return null;
    }


    @Override
    public String getModContainerClass() {
        return null;
    }
}