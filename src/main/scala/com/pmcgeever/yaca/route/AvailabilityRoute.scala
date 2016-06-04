package com.pmcgeever.yaca.route

import akka.actor._
import com.pmcgeever.yaca.route.services.AvailabilityService.AvailabilityFor
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
      handleRequest(requestContext, availabilityService, AvailabilityFor(userId))
  }
}
