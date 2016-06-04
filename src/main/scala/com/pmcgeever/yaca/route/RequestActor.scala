package com.pmcgeever.yaca.route

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
  case class Worker(requestContext: RequestContext, target: ActorRef, message: Object) extends RequestActor
}

trait RequestActorFactory {
  this: HttpService =>

  // TODO setup supervision strategy for the worker actors
  def handleRequest(requestContext: RequestContext, target: ActorRef, message: Object): Unit =
    actorRefFactory.actorOf(Props(RequestActor.Worker(requestContext, target, message)))
}
