package com.pmcgeever.routes

import akka.actor.{ActorRefFactory, ActorContext, ActorRef}
import spray.http.MediaTypes._
import spray.json.JsObject
import spray.routing.HttpService

class AvailabilityRoute(availablityActor: ActorRef)(implicit context: ActorContext) extends HttpService {

  def actorRefFactory: ActorRefFactory = context

  val route =
  path("availability" / Segment) { userName =>
      put {
        entity(as[JsObject]) { entity =>
          availablityActor ! entity
        }


        respondWithMediaType(`text/html`) { // XML is marshalled to `text/xml` by default, so we simply override here
          complete {
            <html>
              <body>
                <h1>Say hello to<i>spray-routing</i>on<i>spray-can</i>!</h1>
              </body>
            </html>
          }
        }
      }
    }
}
