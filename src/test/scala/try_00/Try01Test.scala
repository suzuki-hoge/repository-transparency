package try_00

import org.scalatest.FunSuite
import org.scalatest.prop.TableDrivenPropertyChecks
import share._

class Try01Test extends FunSuite with TableDrivenPropertyChecks {
  //@formatter:off
  private val patterns = Table(
    ("orders", "items", "exp"),
    (
      new OrderRepository {override def findOne(userId:Int): Order = Order(Ordered, Clothes)},
      new ItemRepository {override def findOne(userId: Int): Item = Item(Waiting)},
      true
    ),
    (
      new OrderRepository {override def findOne(userId:Int): Order = Order(Ordered, Clothes)},
      new ItemRepository {override def findOne(userId: Int): Item = Item(Packed)},
      false
    ),
    (
      new OrderRepository {override def findOne(userId:Int): Order = Order(Rejected, Food)},
      new ItemRepository {override def findOne(userId: Int): Item = throw new RuntimeException("n/a")},
      false
    )
  )
  //@formatter:on

  forAll(patterns) { (orders: OrderRepository, items: ItemRepository, exp: Boolean) =>
    test(s"test ($orders, $items)") {
      assert(DomainService(userId = 1, orders, items) == exp)
    }
  }
}
