package syntax

/**
  * Will only execute once all the incoming branches are done.
  */
sealed trait Times {//how will evaluation work.?.
  def in: Times.In
}

object Times {
  def apply(out: => UnitTaker): Times = new Times.In(out)

  final class In private[Times](o: => UnitTaker) extends Times with Taker[Value] {
    val out = o

    private[this] var counter = 0

    def apply(v: Value): Unit = {
      counter -= 1
      if (counter == 0) {
        out()
      }
    }

    def in = {
      counter += 1
      this
    }
  }
}
