package com.pmcgeever.yaca.route

import spray.routing.Directives.pathPrefix

trait ApiRoute {
  val apiRoute = pathPrefix("api")
}
