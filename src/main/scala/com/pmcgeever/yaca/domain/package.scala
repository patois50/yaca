package com.pmcgeever.yaca

package object domain {
  /* AVAILABILITY */
  trait AvailabilityStatus{val text: String}
  case object Available extends AvailabilityStatus {override val text = "available"}
  object Unavailable extends AvailabilityStatus {override val text = "unavailable"}
  case class Availability(userId: Long, status: AvailabilityStatus, message: String)
}
