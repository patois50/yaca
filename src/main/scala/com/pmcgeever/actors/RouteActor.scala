package com.pmcgeever.actors

import akka.actor.{ActorRefFactory, Actor}
import com.pmcgeever.routes.AvailabilityRoute
import spray.routing.HttpService

// we don't implement our route structure directly in the service actor because
// we want to be able to test it independently, without having to spin up an actor
class RouteActor extends Actor with HttpService {

  def actorRefFactory: ActorRefFactory = context

  val availabilityActor = context.actorOf(AvailabilityActor.props, "availability")

  // this actor only runs our route, but you could add
  // other things here, like request stream processing
  // or timeout handling
  def receive = runRoute(new AvailabilityRoute(availabilityActor).route)

}