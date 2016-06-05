package com.pmcgeever.yaca.route

import org.scalatest.{FlatSpec, Matchers}
import spray.routing.Directives
import spray.testkit.ScalatestRouteTest

class ApiRouteTest extends FlatSpec with ScalatestRouteTest with Matchers with Directives {

  "The Api Route" should "accept requests starting with /api" in new Context {
    Get("/api/any/continuation") ~>
      apiRoute {
      complete("some arbitrary response")
    } ~> check {
      handled should be(true)
    }
  }

  it should "not accept requests that do not begin with /api" in new Context {
    Get("/some/other/path") ~>
      apiRoute {
        complete("some arbitrary response")
      } ~> check {
      handled should be(false)
    }
  }

  class Context extends ApiRoute {}
}
