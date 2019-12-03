package share

// lazy

case class Lazy[T](t: () => T) {
  def apply(): T = t()
}

// test support

object Lazy {
  def apply[T](t: T): Lazy[T] = Lazy(() => t)

  def na[T]: Lazy[T] = Lazy(() => throw new RuntimeException("n/a"))
}
