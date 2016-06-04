package com.pmcgeever.yaca

import akka.actor.{ActorSystem, Props}
import akka.io.IO
import akka.pattern.ask
import akka.util.Timeout
import com.pmcgeever.yaca.route.RoutesActor
import spray.can.Http

import scala.concurrent.duration._

object Yaca extends App {
  implicit val system = ActorSystem("yaca")
  val routeActor = system.actorOf(Props[RoutesActor], "route")

  implicit val timeout = Timeout(5.seconds)
  IO(Http) ? Http.Bind(routeActor, interface = "localhost", port = 8888)
}
