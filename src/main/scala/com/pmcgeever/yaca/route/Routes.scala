package com.pmcgeever.yaca.route

import akka.actor.{Actor, Props}
import com.pmcgeever.yaca.route.services.AvailabilityService
import spray.routing.HttpService

class RoutesActor extends Actor with HttpService with Routes {
  override val actorRefFactory = context.system
  override val availabilityService = context.actorOf(Props[AvailabilityService], "availability")

  def receive = runRoute(routes)
}

trait Routes extends AvailabilityRoute {

  import spray.routing.Directives.pathPrefix

  val routes = {
    pathPrefix("api") {
        availabilityRoute
    }
  }
}
