package fides.syntax.signatures

import fides.syntax.identifiers.{Identifier, IdentifierKey}
import fides.syntax.code.{Code, Expr, Ptrn, Val, ValQ, ValType}
import fides.syntax.values.Integer

/**
  * Signed values are guaranteed to have been created using a key corresponding to @signature.
  *
  * @param document  the signed value
  * @param signature the identifier corresponding to the key that was used to sign the document
  * @tparam T the type of the signed value
  */
final case class Signed[+T <: ValType] private(document: Val[T], signature: Identifier) extends ValQ[Signed[T]]
object Signed:
  /**
    * Signed values can only be created from keys, but only reveal the corresponding identifier.
    */
  def apply[T <: ValType](document: Val[T], signatory: IdentifierKey): Signed[T] =
    new Signed(document, signatory.identifier)
end Signed
// todo should we also keep track of the (singleton) type of the signatory? Or not?

// todo what if we disallowed matching Signed in patterns? But what about when it's in code? In the concrete syntax,
//  we could try to use only symbol for Sign, Signed, Unsign, and SignedMatcher(any level).
//  The parser would then have to disambiguate.

/**
  * Since Signed values cannot be created freely, a different type of value is needed for matching.
  * To match the latter, yet a different one is needed, and so forth.
  * This is solved by having a level, effectively introducing a hierarchy of matchers.
  * This is similar to having to use a backslash in a regex to escape another backslash
  * (except that the number of backslashes needed grows exponentially with how meta the regex is).
  *
  * SignedMatcher(0, m, s) matches Signed(m, s).
  * For level > 0, SignedMatcher(level, m, s) matches SignedMatcher(level - 1, m, s).
  *
  * @tparam T keeps track of the value type
  */
final case class SignedMatcher[T <: ValType]
(level: Code[Val[Integer]], document: Code[Val[T]], signature: Code[Val[Identifier]]) extends ValQ[Signed[T]]
// todo should only be allowed in code patterns (although maybe it's not such a big deal if it can be used elsewhere)
// todo level should be >= 0

/**
  * Primitive to sign messages
  */
final case class Sign[T <: ValType]
(contents: Code[Expr[T]], signatory: Code[Expr[IdentifierKey]]) extends Expr[Signed[T]]

/**
  * Primitive to unsign messages
  */
final case class UnSign[P <: N, N <: ValType]
(contents: Code[Ptrn[P, N]], signatory: Code[Ptrn[Identifier, Identifier]]) extends Ptrn[Signed[P], Signed[N]]
