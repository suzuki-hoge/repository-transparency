## try 01
手に入らないなら例外なんだろう、という発想

+ :white_check_mark: 一番簡単
+ :x: 言い換えると一番安易、うっかりやってしまう可能性も高い
+ :x: `DomainService`で例外が出るわけではないので、正しく`catch`を書くのはかなり難しい
+ :x: 嘘のテストが書ける
  + `Ordered, None`や`Rejected, Some`は`DomainService`までこない
