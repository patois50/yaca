package com.pmcgeever

import akka.actor.{ActorSystem, Props}
import akka.io.IO
import com.pmcgeever.actors.RouteActor
import spray.can.Http
import akka.pattern.ask
import akka.util.Timeout
import scala.concurrent.duration._

object Yaca extends App {
  implicit val system = ActorSystem("yacaSystem")
  val routeActor = system.actorOf(Props[RouteActor], "route")
  implicit val timeout = Timeout(5.seconds)

  // start a new HTTP server on port 8080 with our service actor as the handler
  IO(Http) ? Http.Bind(routeActor, interface = "localhost", port = 8080)
}
