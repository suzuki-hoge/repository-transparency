package try_08

import org.scalatest.FunSuite
import org.scalatest.prop.TableDrivenPropertyChecks
import share.{Clothes, Food, Lazy, Ordered, Packed, Rejected, Waiting}

class Try08Test extends FunSuite with TableDrivenPropertyChecks {
  //@formatter:off
  private val patterns = Table(
    ("order", "item", "exp"),
    (Order(Ordered,  Clothes), Lazy(Item(Waiting)), None),
    (Order(Ordered,  Clothes), Lazy(Item(Packed)),  Some(WaitingOnly)),
    (Order(Rejected, Food),    Lazy.na[Item],       Some(OrderedOnly))
  )
  //@formatter:on

  forAll(patterns) { (order: Order, item: Lazy[Item], exp: Option[Error]) =>
    test(s"test ($order, $item)") {
      assert(DomainService(order, item) == exp)
    }
  }
}
