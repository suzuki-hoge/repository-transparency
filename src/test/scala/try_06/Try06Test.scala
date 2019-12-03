package try_06

import org.scalatest.FunSuite
import org.scalatest.prop.TableDrivenPropertyChecks
import share._

class Try06Test extends FunSuite with TableDrivenPropertyChecks {
  //@formatter:off
  private val patterns = Table(
    ("flow", "exp"),
    (OrderedFlow(Order(Ordered,   Clothes), Item(Waiting)), true),
    (OrderedFlow(Order(Ordered,   Clothes), Item(Packed)),  false),
    (RejectedFlow(Order(Rejected, Food)),                   false)
  )
  //@formatter:on

  forAll(patterns) { (flow: ProcessingFlow, exp: Boolean) =>
    test(s"test ($flow)") {
      assert(flow.isOk == exp) // test flow only
    }
  }
}
