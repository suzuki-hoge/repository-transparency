# think domain
[DomainService の Repository 排除とエラー表現のパターン](https://www.slideshare.net/secret/sZqfk76RwUBeI8) のコード集

## コードサンプル
### お断り
+ 1 ファイルに複数クラスが書けるので Scala にする
+ 名付け、英語、UML、scala 規約等は全て適当
+ 雰囲気重視

### お題
+ 受注と発送物があり、受注が成立した場合発送物が存在する
+ 受注と発送物それぞれの状態に基づいて、キャンセルが成立するか判断する

これ以上は深くは言及しない、絵と実装からどこまで仕様がわかるか ~~（という手抜き）~~

### try00
`DomainService`で参照する例

### try01 - try06
なんとか`DomainService`で参照しない様に考慮する例

### try07 - try08
エラーコードをどう管理するか考慮する例

### try09 - try12
エラーコードの管理を複数 UC での共通化と合わせて考慮する例

### try13
総合した結果に軽いリファクタリングをすると最終的にどうなるかの一案
+ フォーマット等
+ スライドリンク

## 絵の再生成
```
$ java -jar ~/path/to/plantuml.jar src/main/scala/**/*.puml
```
