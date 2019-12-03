package try_04

import org.scalatest.FunSuite
import org.scalatest.prop.TableDrivenPropertyChecks
import share._

class Try04Test extends FunSuite with TableDrivenPropertyChecks {
  //@formatter:off
  private val patterns = Table(
    ("order", "item", "exp"),
    (Order(Ordered,  Clothes), Lazy(Item(Waiting)), true),
    (Order(Ordered,  Clothes), Lazy(Item(Packed)),  false),
    (Order(Rejected, Food),    Lazy.na[Item],       false)
  )
  //@formatter:on

  forAll(patterns) { (order: Order, item: Lazy[Item], exp: Boolean) =>
    test(s"test ($order, $item)") {
      assert(DomainService(order, item) == exp)
    }
  }
}
