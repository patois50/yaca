package com.pmcgeever.yaca

import com.pmcgeever.yaca.domain.{Unavailable, Available, Availability, AvailabilityStatus}
import spray.json._

package object route {

  object AvailabilityMarshallers extends DefaultJsonProtocol {
    implicit object AvailabilityStatusFormat extends RootJsonFormat[AvailabilityStatus] {
      def write(s: AvailabilityStatus) = JsObject("text" -> JsString(s.text))

      def read(value: JsValue) = {
        value.asJsObject.getFields("text") match {
          case Seq(JsString(text)) =>
            text match {
              case "available" => Available
              case "unavailable" => Unavailable
              case _ => throw new DeserializationException("Unrecognised availability")
            }
          case _ => throw new DeserializationException("Availability expected")
        }
      }
    }

    implicit val availabilityFormat = jsonFormat3(Availability)
  }

}
