package try_06

import share._

// processing flow

sealed trait ProcessingFlow {
  def isOk: Boolean
}

case class OrderedFlow(order: Order, item: Item) extends ProcessingFlow {
  override def isOk: Boolean = order.isOk && item.isOk
}

case class RejectedFlow(order: Order) extends ProcessingFlow {
  override def isOk: Boolean = order.isOk
}

trait ProcessingFlowRepository {
  def findOne(userId: Int): ProcessingFlow
}

object ProcessingFlowRepositoryImpl extends ProcessingFlowRepository {
  def findOne(userId: Int): ProcessingFlow = ???
}

// domain service

object DomainService {
  def apply(flow: ProcessingFlow): Boolean = {
    flow.isOk
  }
}

// application service

object ApplicationService {
  private val flows = ProcessingFlowRepositoryImpl

  def apply(userId: Int): Boolean = {
    val flow = flows.findOne(userId)

    DomainService(flow)
  }
}
