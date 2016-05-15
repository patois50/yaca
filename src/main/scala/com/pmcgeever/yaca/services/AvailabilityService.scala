package com.pmcgeever.yaca.routes.services

import akka.actor.Actor
import akka.actor.Status.Failure

object AvailabilityService {
  case class AvailabilityFor(userId: Long)
  val data = Map(1L -> "available", 2L -> "unavailable")
}

class AvailabilityService extends Actor {
  import com.pmcgeever.yaca.routes.RequestResponse
  import com.pmcgeever.yaca.routes.services.AvailabilityService._

  override def receive = {
    case AvailabilityFor(uid) =>
      sender !
        data.get(uid)
          .map(a => RequestResponse(raw"""{availability: "$a"}"""))
          .getOrElse(Failure(new NoSuchElementException(s"User id $uid does not exist")))
  }
}
