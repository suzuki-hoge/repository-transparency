package try_13

import share.{Clothes, Food, ItemStatus, Lazy, OrderKind, OrderStatus, Ordered, Packed, Rejected, Waiting}

// order

case class Order(status: OrderStatus, kind: OrderKind) {
  def validate: Either[OrderError, Unit] = (status, kind) match {
    case (Ordered, Clothes) => Right(())
    case (Ordered, Food) => Left(ClothesOnly)
    case (Rejected, _) => Left(OrderedOnly)
  }
}

// repository

trait OrderRepository {
  def findOne(userId: Int): Order

  def cancel(userId: Int): Unit
}

// repository implementation

object OrderRepositoryImpl extends OrderRepository {
  override def findOne(userId: Int): Order = ???

  override def cancel(userId: Int): Unit = ???
}

// item

case class Item(status: ItemStatus) {
  def validate: Either[ItemError, Unit] = status match {
    case Waiting => Right(())
    case _ => Left(WaitingOnly)
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

// user

case class User(plan: Plan) {
  def validate: Either[UserError, Unit] = plan match {
    case Premium => Right(())
    case _ => Left(PremiumOnly)
  }
}

// user plan

sealed trait Plan

object Normal extends Plan

object Premium extends Plan

// repository

trait UserRepository {
  def findOne(userId: Int): User
}

// repository implementation

object UserRepositoryImpl extends UserRepository {
  override def findOne(userId: Int): User = ???
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

// user error

sealed trait UserError extends Error

object PremiumOnly extends UserError

// domain service

object FlowDomainService {
  def apply(order: Order, item: Lazy[Item]): Either[Error, Unit] = for {
    _ <- order.validate
    _ <- item().validate
  } yield ()
}

object UserDomainService {
  def apply(user: User): Either[Error, Unit] = user.validate
}

// response

trait Response[R]

case class Success[R](r: R) extends Response[R]

case class Failure[R](error: Error) extends Response[R]

// application service

case class ApplicationService(orders: OrderRepository, items: ItemRepository, users: UserRepository) {
  def apply(userId: Int): Response[Unit] = (
    for {
      _ <- FlowDomainService(
        orders.findOne(userId),
        Lazy(() => items.findOne(userId))
      )
      _ <- UserDomainService(
        users.findOne(userId)
      )
    } yield userId)
    .map(orders.cancel)
    .fold(Failure[Unit], Success[Unit])
}

// main

object Main extends App {
  val ordersMocker = (status: OrderStatus, kind: OrderKind) => new OrderRepository {
    override def findOne(userId: Int): Order = Order(status, kind)

    override def cancel(userId: Int): Unit = println("cancel")
  }

  val itemsMocker = (status: ItemStatus) => new ItemRepository {
    override def findOne(userId: Int): Item = {
      println("find item")
      Item(status)
    }
  }

  val usersMocker = (plan: Plan) => new UserRepository {
    override def findOne(userId: Int): User = {
      println("find user")
      User(plan)
    }
  }

  println(
    ApplicationService(ordersMocker(Ordered, Clothes), itemsMocker(Waiting), usersMocker(Premium))(1)
  ) // print [find item] | print [find user] | print [cancel] | get Success[]

  println(
    ApplicationService(ordersMocker(Rejected, Clothes), itemsMocker(Waiting), usersMocker(Premium))(1)
  ) //                   |                   |                | get Failure[OrderedOnly]

  println(
    ApplicationService(ordersMocker(Ordered, Clothes), itemsMocker(Packed), usersMocker(Premium))(1)
  ) // print [find item] |                   |                | get Failure[WaitingOnly]

  println(
    ApplicationService(ordersMocker(Ordered, Clothes), itemsMocker(Waiting), usersMocker(Normal))(1)
  ) // print [find item] | print [find user] |                | get Failure[PremiumOnly]
}
