package try_02

import org.scalatest.FunSuite
import org.scalatest.prop.TableDrivenPropertyChecks
import share._

class Try02Test extends FunSuite with TableDrivenPropertyChecks {
  //@formatter:off
  private val patterns = Table(
    ("order", "item", "exp"),
    (Order(Ordered,  Clothes), Some(Item(Waiting)), true),
    (Order(Ordered,  Clothes), Some(Item(Packed)),  false),
    (Order(Rejected, Food),    None,                false)
  )
  //@formatter:on

  forAll(patterns) { (order: Order, item: Option[Item], exp: Boolean) =>
    test(s"test ($order, $item)") {
      assert(DomainService(order, item) == exp)
    }
  }
}
