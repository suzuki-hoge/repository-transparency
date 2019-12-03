package try_00

import share._

// domain service

object DomainService {
  def apply(userId: Int, orders: OrderRepository, items: ItemRepository): Boolean = {
    orders.findOne(userId).isOk && items.findOne(userId).isOk
  }
}

// application service

object ApplicationService {
  private val orders = OrderRepositoryImpl
  private val items = ItemRepositoryImpl

  def apply(userId: Int): Boolean = {
    DomainService(userId, orders, items)
  }
}
