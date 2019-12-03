package try_02

import share._

// item

trait ItemRepository {
  def findMaybe(userId: Int): Option[Item]
}

object ItemRepositoryImpl extends ItemRepository {
  override def findMaybe(userId: Int): Option[Item] = ???
}

// domain service

object DomainService {
  def apply(order: Order, item: Option[Item]): Boolean = {
    order.isOk && item.get.isOk
  }
}

// application service

object ApplicationService {
  private val orders = OrderRepositoryImpl
  private val items = ItemRepositoryImpl

  def apply(userId: Int): Boolean = {
    val order = orders.findOne(userId)
    val item = items.findMaybe(userId)

    DomainService(order, item)
  }
}
