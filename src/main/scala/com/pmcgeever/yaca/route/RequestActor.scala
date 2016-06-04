package com.pmcgeever.yaca.route

import akka.actor.{Actor, ActorRef, Props}
import spray.routing.HttpService

trait RequestActor extends Actor {

  def responseHandler: ResponseHandler
  def target: ActorRef
  def message: AnyRef

  target ! message

  def receive = {
    case ev: AnyRef => {
      responseHandler.handleResponse(ev)
      context.stop(self)
    }
  }
}

object RequestActor {
  case class Worker(responseHandler: ResponseHandler, target: ActorRef, message: AnyRef) extends RequestActor
}

trait RequestActorFactory {
  this: HttpService =>

  // TODO setup supervision strategy for the worker actors
  def handleRequest(responseHandler: ResponseHandler, target: ActorRef, message: AnyRef): Unit =
    actorRefFactory.actorOf(Props(RequestActor.Worker(responseHandler, target, message)))
}
