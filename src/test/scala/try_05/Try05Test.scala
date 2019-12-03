package try_05

import org.scalatest.FunSuite
import org.scalatest.prop.TableDrivenPropertyChecks
import share._

class Try05Test extends FunSuite with TableDrivenPropertyChecks {
  //@formatter:off
  private val patterns = Table(
    ("flow", "exp"),
    (ProcessingFlow(Order(Ordered,  Clothes), Some(Item(Waiting))), true),
    (ProcessingFlow(Order(Ordered,  Clothes), Some(Item(Packed))),  false),
    (ProcessingFlow(Order(Rejected, Food),    None),                false)
  )
  //@formatter:on

  forAll(patterns) { (flow: ProcessingFlow, exp: Boolean) =>
    test(s"test ($flow)") {
      assert(flow.isOk == exp) // test flow only
    }
  }
}
