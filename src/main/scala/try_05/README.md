## try 05
実装で対応しすぎたので頭を冷やしてドメインを見直そう、依存してるものは一発で扱おう、という発想

+ :white_check_mark: 依存していることが閉じ込められる
+ :white_check_mark: `DomainService`ではなくドメインごとにテストできる
+ :x: `Option`が結局無くせてないので、ありえないインスタンスが作れる
+ :x: 参照処理の実装が難しい

