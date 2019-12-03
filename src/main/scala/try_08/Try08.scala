package try_08

import share._

// order

case class Order(status: OrderStatus, kind: OrderKind) {
  def validate: Option[OrderError] = (status, kind) match {
    case (Ordered, Clothes) => None
    case (Ordered, Food) => Some(ClothesOnly)
    case (Rejected, _) => Some(OrderedOnly)
  }
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
  def validate: Option[ItemError] = status match {
    case Waiting => None
    case _ => Some(WaitingOnly)
  }
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

// order error

sealed trait OrderError extends Error

object OrderedOnly extends OrderError

object ClothesOnly extends OrderError

// item error

sealed trait ItemError extends Error

object WaitingOnly extends ItemError

// domain service

object DomainService {
  def apply(order: Order, item: Lazy[Item]): Option[Error] = {
    if (order.validate.isEmpty)
      item().validate

    else
      order.validate
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
