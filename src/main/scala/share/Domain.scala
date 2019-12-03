package share

// order

case class Order(status: OrderStatus, kind: OrderKind) {
  def isOk: Boolean = status == Ordered && kind == Clothes
}

// order status

sealed trait OrderStatus

object Ordered extends OrderStatus

object Rejected extends OrderStatus

// order kind

sealed trait OrderKind

object Clothes extends OrderKind

object Food extends OrderKind

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
  def isOk: Boolean = status == Waiting
}

// item status

sealed trait ItemStatus

object Waiting extends ItemStatus

object Packed extends ItemStatus

object Delivering extends ItemStatus

object Delivered extends ItemStatus

// repository

trait ItemRepository {
  def findOne(userId: Int): Item
}

// repository implementation

object ItemRepositoryImpl extends ItemRepository {
  override def findOne(userId: Int): Item = ???
}
