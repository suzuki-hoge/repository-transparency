package try_01

import org.scalatest.FunSuite
import org.scalatest.prop.TableDrivenPropertyChecks
import share._

class Try01Test extends FunSuite with TableDrivenPropertyChecks {
  //@formatter:off
  private val patterns = Table(
    ("order", "item", "exp"),
    (Order(Ordered,  Clothes), Item(Waiting), true),
    (Order(Ordered,  Clothes), Item(Packed),  false),
    (Order(Rejected, Food),    Item(Waiting), false)
  )
  //@formatter:on

  forAll(patterns) { (order: Order, item: Item, exp: Boolean) =>
    test(s"test ($order, $item)") {
      assert(DomainService(order, item) == exp)
    }
  }
}
