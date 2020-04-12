package core

package object syntax {
  type Multiset[A] = Map[A, BigInt]

  sealed trait D
  sealed trait Inp extends D
  sealed trait Out extends D
  sealed trait Val extends Inp with Out

  sealed trait N
  sealed trait RegularK extends N
  sealed trait CodeK[+K <: N, +C <: D] extends N

  trait TOP_L
  trait L[+K <: N, +C <: D, +T <: TOP_L] extends TOP_L

  type I[+K <: N, +T <: TOP_X] = X[K, Inp, T]
  type O[+K <: N, +T <: TOP_X] = X[K, Out, T]
  type V[+K <: N, +T <: TOP_X] = X[K, Val, T]
}
