package com.finagle.quiickstart

import com.twitter.finagle.Service
import com.twitter.util.Future
import com.twitter.finagle.http

// This is a plus 10 service
class PlusTenService extends Service[http.Request, http.Response] {

  override def apply(request: http.Request): Future[http.Response] = {
    Future {
//      Thread.sleep(3000)
      val input = request.getIntParam("num")
      val output = input + 10
      val response = http.Response(request.version, http.Status.Ok)
      response.setContentString(output.toString)
      response
    }
  }

}
