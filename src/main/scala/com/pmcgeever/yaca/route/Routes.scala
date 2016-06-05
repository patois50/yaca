package com.pmcgeever.yaca.route

import akka.actor.{Actor, Props}
import com.pmcgeever.yaca.route.services.AvailabilityService
import spray.routing.HttpService

class RoutesActor extends Actor with HttpService
with ApiRoute
with AvailabilityRoute {
  override val actorRefFactory = context.system
  override val availabilityService = context.actorOf(Props[AvailabilityService], "availability")

  val route = apiRoute {
    availabilityRoute
  }

  def receive = runRoute(route)
}