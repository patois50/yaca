package com.pmcgeever.yaca.route

import akka.actor._
import com.pmcgeever.yaca.domain.Availability
import com.pmcgeever.yaca.service.{NotFound, Retrieve, Retrieved}
import spray.routing.{HttpService, RequestContext, Route}

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
      handleRequest(
        new AvailabilityResponseHandler(requestContext) {},
        availabilityService,
        Retrieve(userId))
  }
}

abstract class AvailabilityResponseHandler(override val request: RequestContext) extends ResponseHandler {
  import com.pmcgeever.yaca.route.AvailabilityMarshallers._
  import spray.http.StatusCodes.{InternalServerError, NotFound => NotFoundCode, OK}
  import spray.httpx.SprayJsonSupport._


  override def handleResponse: PartialFunction[AnyRef, Unit] = {
    case Retrieved(e) => request.complete(OK, e.asInstanceOf[Availability])
    case NotFound => request.complete(NotFoundCode)
    case _ => {
      // TODO LOG HERE
      request.complete(InternalServerError, "There was an error handling the response from the Availability service.")
    }
  }
}
