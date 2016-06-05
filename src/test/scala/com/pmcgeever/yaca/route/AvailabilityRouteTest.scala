package com.pmcgeever.yaca.route

import akka.actor.{ActorRef, ActorSystem}
import akka.testkit.{TestActor, TestKit, TestProbe}
import com.pmcgeever.yaca.domain.{Availability, Available}
import com.pmcgeever.yaca.service.{NotFound, Retrieve, Retrieved}
import org.scalatest.{FlatSpec, Matchers}
import spray.http.StatusCodes
import spray.routing.Directives
import spray.testkit.ScalatestRouteTest

class AvailabilityRouteTest extends FlatSpec with ScalatestRouteTest with Matchers with Directives {

  import com.pmcgeever.yaca.route.AvailabilityMarshallers._
  import spray.httpx.SprayJsonSupport._

  "The AvailablityRoute" should "return availability for GET with known id" in new Context {
    val expectedAvailability = new Availability(1L, Available, "Test")
    availabilityProbe.setAutoPilot {
      new TestActor.AutoPilot {
        def run(sender: ActorRef, msg: Any) = msg match {
          case Retrieve(1L) =>
            sender ! Retrieved(expectedAvailability)
            TestActor.NoAutoPilot
        }
      }
    }

    Get("/availability/1") ~> availabilityRoute ~> check {
      responseAs[Availability] should be(expectedAvailability)
      status shouldEqual StatusCodes.OK
    }
  }

  it should "return 404 for GET with unknown id" in new Context {
    val expectedAvailability = new Availability(1L, Available, "Test")
    availabilityProbe.setAutoPilot {
      new TestActor.AutoPilot {
        def run(sender: ActorRef, msg: Any) = msg match {
          case Retrieve(1L) =>
            sender ! NotFound
            TestActor.NoAutoPilot
        }
      }
    }

    Get("/availability/1") ~> availabilityRoute ~> check {
      status shouldEqual StatusCodes.NotFound
    }
  }

  class Context extends TestKit(ActorSystem("Test")) with AvailabilityRoute {
    override val actorRefFactory: ActorSystem = system
    val availabilityProbe = TestProbe()
    override val availabilityService: ActorRef = availabilityProbe.ref
  }

}
