## try 03
参照のタイミングだけ`DomainService`で決めよう、という発想

+ :white_check_mark: リポジトリに直接依存してない
+ :white_check_mark: テストは正しくかける
+ :x: 多少難しそう
+ :x: 実際にリポジトリの処理を呼ぶのは`DomainService`なので、IO エラーとかが出る可能性はある

