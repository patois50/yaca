package com.pmcgeever.yaca.route.services

import com.pmcgeever.yaca.domain.{Availability, Available, Unavailable}
import com.pmcgeever.yaca.service.Repository

object AvailabilityService {
  val availabilityData = Map(
    1L -> Availability(1L, Available, "Im happy to chat"),
    2L -> Availability(2L, Unavailable, "Im happy to chat"))
}

class AvailabilityService extends Repository[Availability] {
  import com.pmcgeever.yaca.route.services.AvailabilityService._

  override val data = availabilityData
}
