package com.pmcgeever.yaca.service

import akka.actor.Actor

trait Repository[E] extends Actor {
  val data: Map[Long, E]

  override def receive = {
    case Retrieve(id) =>
      sender ! data.get(id)
          .map(Retrieved(_))
          .getOrElse(NotFound)
  }
}
