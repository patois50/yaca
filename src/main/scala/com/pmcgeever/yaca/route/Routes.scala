package com.pmcgeever.yaca.route

import akka.actor.{Actor, ActorRefFactory, Props}
import com.pmcgeever.yaca.route.services.AvailabilityService

class RoutesActor extends Actor with Routes {
  override val actorRefFactory: ActorRefFactory = context
  override val availabilityService = context.actorOf(Props[AvailabilityService], "availability")

  def receive = runRoute(routes)
}

trait Routes extends AvailabilityRoute {
  val routes = {
    pathPrefix("api") {
      pathEnd {
        get {
          complete("Api requested")
        }
      } ~
        availability
    }
  }
}
