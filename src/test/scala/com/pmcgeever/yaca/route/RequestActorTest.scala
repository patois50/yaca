package com.pmcgeever.yaca.route

import akka.actor.{Terminated, ActorRef, ActorSystem}
import akka.testkit.{TestActor, TestKit, TestProbe}
import org.mockito.Mockito.{verify, when}
import org.scalatest.FlatSpec
import org.scalatest.mock.MockitoSugar

class RequestActorTest extends FlatSpec with MockitoSugar {

  "The Request Actor" should "pass any response it receives the to response handler" in new Context {
    val actorRef = handleRequest(handlerMock, targetActorRef, TestEvent)

    // Does this test have the potential to verify that the function was called before it actually is called
    verify(functionMock).apply(TestResponse)
  }

  "The Request Actor" should "terminate itself when it has passed a response onto the response handler" in new Context {
    val actorRef = handleRequest(handlerMock, targetActorRef, TestEvent)
    watch(actorRef)
    expectMsg(Terminated(actorRef)(false, false))
  }

  class Context extends TestKit(ActorSystem("Test")) with RequestActorFactory {
    object TestEvent
    object TestResponse

    override val actorRefFactory: ActorSystem = system

    val handlerMock = mock[ResponseHandler]
    val functionMock = mock[PartialFunction[AnyRef, Unit]]
    when(handlerMock.handleResponse).thenReturn(functionMock)

    val probe = TestProbe()
    val targetActorRef = probe.ref
    probe.setAutoPilot {
      new TestActor.AutoPilot {
        def run(sender: ActorRef, msg: Any) = msg match {
          case TestEvent =>
            sender ! TestResponse
            TestActor.NoAutoPilot
        }
      }
    }
  }
}
