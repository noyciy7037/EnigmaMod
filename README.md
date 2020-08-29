# EnigmaMod
Encrypt the Minecraft chat.  
Minecraftのチャットを暗号化することが出来ます。

## 使用法
暗号方式はAES-128です。  
暗号キーの追加:  
`/enigma addkey KEYKEYKEYKEYKEYK`

暗号キーの確認:  
`/enigma getkey`

暗号キーの削除:  
`/enigma removekey index(Integer)`

メインの暗号キーの選択(暗号化用):  
`/enigma setmain index(Integer)`

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
### isDisplayGui
初期値: `false`  
`true`に設定すると、画面上にEnigmaの動作モードが表示されるようになります。
### textPosition
初期値: `8`  
動作モードの表示位置を指定します。
- `0`
  - 左上
- `1`
  - 中央上
- `2`
  - 右上
- `3`
  - 左中央
- `4`
  - 中央
- `5`
  - 右中央
- `6`
  - 左下
- `7`
  - 中央下
- `8`
  - 右下
### xGap, yGap
初期値: `0`  
テキストの位置のズレを指定します。左上を0,0として、`textPosition`で指定された位置からそれぞれx,y分ずらすことが出来ます。
## ライセンス
[CC0 1.0 Universal](https://creativecommons.org/publicdomain/zero/1.0/deed.ja "CC0 1.0 Universal")
