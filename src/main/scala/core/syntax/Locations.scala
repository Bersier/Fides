package core.syntax

trait ID[+K <: N, C <: Inp with Out, +T <: TOP_X] extends X[K, C, T]
trait Loc[+K <: N, +T <: TOP_X] extends ID[K, Inp with Out, T]

/**
  * Keys allow broadcasting.
  */
final class Key[+K <: N, +T <: X[K, D, T]] extends Loc[K, T]

/**
  * Can be used as a value, or as an O.
  * However, when used as an O, does not behave like a pattern, but rather like a Loc.
  */
final case class Broadcast[+K <: N, +T <: X[K, D, T]](address: Key[K, T]) extends V[K, T]

/**
  * Can be used as a value, or as an O.
  * However, when used as an O, does not behave like a pattern, but rather like a Loc.
  */
sealed trait Command[+K <: N, +T <: X[K, Val, T]] extends V[K, T]
final class Start[+K <: N](name: V[K, Name]) extends Command[K, U]
final class Pause[+K <: N](name: V[K, Name]) extends Command[K, U]
final class Dissolve[+K <: N](name: V[K, Name]) extends Command[K, U]
final class Kill[+K <: N](name: V[K, Name]) extends Command[K, U]
final class Move[+K <: N](name: V[K, Name]) extends Command[K, Name]
