# EnigmaMod
Encrypt the Minecraft chat.  
Minecraftのチャットを暗号化することが出来ます。

## 使用法
暗号方式はAES-128です。  
暗号キーの設定:  
`/enigma setkey KEYKEYKEYKEYKEYK`

暗号キーの確認:  
`/enigma getkey`

Enigma起動:  
`/enigma enable`

Enigmaを聞き専用モードで起動:  
`/enigma enablelisten`

Enigma終了:  
`/enigma disable`

## コンフィグ
### enabledNeutralMode
初期値: `false`  
`true`に設定すると`/enigma enable`でも聞き専用モードで起動します。(中立モード)

## ライセンス
[CC0 1.0 Universal](https://creativecommons.org/publicdomain/zero/1.0/deed.ja "CC0 1.0 Universal")
