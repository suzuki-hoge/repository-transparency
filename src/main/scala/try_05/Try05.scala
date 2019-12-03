package try_05

import share._

// processing flow

case class ProcessingFlow(order: Order, item: Option[Item]) {
  def isOk: Boolean = order.isOk && item.get.isOk
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
