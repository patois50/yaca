package com.pmcgeever.yaca.routes

import akka.actor._
import com.pmcgeever.yaca.routes.services.AvailabilityService.AvailabilityFor
import spray.routing.{HttpService, Route}

trait AvailabilityRoute extends HttpService with RequestActorFactory {
  val availabilityService: ActorRef

  val availability = {
    path("availability" / LongNumber) { userId =>
      get {
        retrieveAvailabilityFor(userId)
      }
    }
  }

  def retrieveAvailabilityFor(userId: Long): Route = {
    requestContext =>
      requestActor(requestContext, availabilityService, AvailabilityFor(userId))
  }
}
