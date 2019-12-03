package try_07

import share._


// order

case class Order(status: OrderStatus, kind: OrderKind) {
  def assertOrdered: Boolean = status == Ordered

  def assertClothes: Boolean = kind == Clothes
}

// repository

trait OrderRepository {
  def findOne(userId: Int): Order
}

// repository implementation

object OrderRepositoryImpl extends OrderRepository {
  override def findOne(userId: Int): Order = ???
}

// item

case class Item(status: ItemStatus) {
  def assertWaiting: Boolean = status == Waiting
}

// repository

trait ItemRepository {
  def findOne(userId: Int): Item
}

// repository implementation

object ItemRepositoryImpl extends ItemRepository {
  override def findOne(userId: Int): Item = ???
}

// error

sealed trait Error

object OrderedOnly extends Error

object ClothesOnly extends Error

object WaitingOnly extends Error

// domain service

object DomainService {
  def apply(order: Order, item: Lazy[Item]): Option[Error] = {
    if (!order.assertOrdered)
      Some(OrderedOnly)

    else if (!order.assertClothes)
      Some(ClothesOnly)

    else if (!item().assertWaiting)
      Some(WaitingOnly)

    else
      None
  }
}

// application service

object ApplicationService {
  private val orders = OrderRepositoryImpl
  private val items = ItemRepositoryImpl

  def apply(userId: Int): Option[Error] = {
    val order = orders.findOne(userId)
    val item = Lazy(() => items.findOne(userId))

    DomainService(order, item)
  }
}
