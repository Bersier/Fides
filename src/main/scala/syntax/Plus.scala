package syntax

/**
  * Only one of the outgoing branches shall be executed,
  * without control of which one (internal choice).
  */
final class Plus[InT <: Value](o: => Set[Taker[InT]]) extends Taker[InT] {
  require(o.nonEmpty)
  val out = o
}
