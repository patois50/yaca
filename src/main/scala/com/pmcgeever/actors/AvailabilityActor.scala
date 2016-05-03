package com.pmcgeever.actors

import akka.actor.{Actor, Props}
import spray.json._

// TODO Need to make some changes in here.....
// I will be sending a JSON object like so... {status:"available"}
// So I will need to create a case class to represent this json object
// to make things easier I can make the case class have have a status
// object rather than a string, then in the json formatter convert the
// string into the case object

sealed trait Status
case object Available extends Status {
  toString => "available"
}

object Offline extends Status {
  toString => "available"
}

case class Availability(userID: String, status: Status)

object StatusJsonProtocol extends DefaultJsonProtocol {
  implicit object ColorJsonFormat extends RootJsonFormat[Status] {

    val statuses = Seq(Available, Offline)

    def write(s: Status) = JsString(s.toString.toLowerCase)

    def read(value: JsValue) = value match {
      case JsString(s) => {
        statuses.find(_.toString.toLowerCase.equals(s.toLowerCase)) match {
          case Some(st) => st
          case None => throw new DeserializationException("Invalid availability status")
        }
      }
    }
  }
}

case class UpdateAvailability(username: String, obj: JsObject)

object AvailabilityActor {
  val props = Props.apply(new AvailabilityActor)
  var data = scala.collection.mutable.Map[String, Availability]()
}

class AvailabilityActor extends Actor {

  import AvailabilityActor._
  import StatusJsonProtocol._

  override def receive = {
    case UpdateAvailability(u, s) =>
      val status = s.toJson.convertTo[Status]
      data += (u -> s)
      println(s"$u is $s")
  }
}
