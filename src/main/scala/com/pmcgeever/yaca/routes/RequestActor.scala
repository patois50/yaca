package com.pmcgeever.yaca.routes

import akka.actor.Status.Failure
import akka.actor.{Actor, ActorRef, Props}
import spray.http.{StatusCodes, StatusCode}
import spray.routing.{HttpService, RequestContext}

case class RequestResponse(response: String)

trait RequestActor extends Actor {

  def requestContext: RequestContext
  def target: ActorRef
  def message: Object

  target ! message

  def receive = {
    case RequestResponse(r) => complete(r)
    case Failure(e) => completeWithError(e)
  }

  def complete(response: String) = {
    requestContext.complete(response)
    context.stop(self)
  }

  // TODO this needs to be updated so that it can respond with different error codes
  def completeWithError(e: Throwable) = {
    requestContext.complete(StatusCodes.NotFound, e.getMessage)
    context.stop(self)
  }
}

object RequestActor {
  case class Concrete(requestContext: RequestContext, target: ActorRef, message: Object) extends RequestActor
}

trait RequestActorFactory {
  this: HttpService =>

  import com.pmcgeever.yaca.routes.RequestActor.Concrete

  def requestActor(requestContext: RequestContext, target: ActorRef, message: Object): ActorRef =
    actorRefFactory.actorOf(Props(Concrete(requestContext, target, message)))
}
