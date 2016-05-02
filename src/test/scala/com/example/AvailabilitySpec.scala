package com.example

import com.pmcgeever.routes.AvailabilityRoute
import org.specs2.mutable.Specification
import spray.http.StatusCodes._
import spray.testkit.Specs2RouteTest


// TODO
// what level of test is this, route, actor???
// do i need mocking and what for???


class AvailabilitySpec extends Specification with Specs2RouteTest with AvailabilityRoute {
//  def actorRefFactory = system
  
//  "AvailabilityService" should {
//
//    "respond with 204 for PUT request updating the availability of existing user" in {
//      Put("api/v1/availability/patrick", """{status:available}""") ~> myRoute ~> check {
//        status mustEqual NoContent
//      }
//    }
//
//    "respond with 404 for PUT request updating availability of non-existing user" in {
//      Put("api/v1/availability/invalid_user", """{status:available}""") ~> myRoute ~> check {
//        status mustEqual NotFound
//      }
//    }

//    "respond with 405 for GET request for specific availability resource" in {
//      Put("api/v1/availability/patrick", """{status:available}""") ~> myRoute ~> check {
//        status mustEqual StatusCodes.MethodNotAllowed
//      }
//    }
//
//    "respond with 404 for PUT request to all users" in {
//      Put("api/v1/availability/patrick", """{status:available}""") ~> myRoute ~> check {
//        status mustEqual StatusCodes.NotFound
//      }
//    }





//    "respond with HTTP Status 201 for available POST" in {
//      Post("api/v1/available", """{username:patrick}""") ~> myRoute ~> check {
//        status mustEqual Created
//      }
//    }
//
//    "leave GET requests to other paths unhandled" in {
//      Get("/kermit") ~> myRoute ~> check {
//        handled must beFalse
//      }
//    }
//
//    "return a MethodNotAllowed error for PUT requests to the root path" in {
//      Put() ~> sealRoute(myRoute) ~> check {
//        status === MethodNotAllowed
//        responseAs[String] === "HTTP method not allowed, supported methods: GET"
//      }
//    }
//  }
}
