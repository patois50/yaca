package com.pmcgeever.yaca

package object service {
  /* ACTOR EVENTS */
  case class Retrieve(id: Long)
  case class Retrieved[E](entity: E)
  object NotFound
}
