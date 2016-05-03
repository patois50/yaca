package com.pmcgeever.routes

import akka.actor.ActorRef
import spray.routing.Route

trait SimpleRoute {

  import spray.routing.directives.MethodDirectives._
  import spray.routing.directives.PathDirectives._
  import spray.routing.directives.RouteDirectives._

  def actor: ActorRef

  def route(): Route = {
    (pathPrefix("something") & get) {
      complete {
        "hi"
      }
    }
  }
}
