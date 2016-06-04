package com.pmcgeever.yaca.route

import spray.routing.RequestContext

trait ResponseHandler {

  val request: RequestContext

  def handleResponse: PartialFunction[AnyRef, Unit]
}
