package com.finagle.serviceAndFilter

import com.twitter.finagle.http.{Request, Response}
import com.twitter.finagle.{Http, Service, http}
import com.twitter.util.{Await, Future}

class CountService extends Service[http.Request, http.Response] {
  var count = 0

  override def apply(request: Request): Future[Response] = {
    Future {
      val num = request.getIntParam("count")

      count = count + num
      println(count)
      http.Response(request.version, http.Status.Ok)
    }
  }
}

object CountServer {
  def main(args: Array[String]): Unit = {
    val service: Service[http.Request, http.Response] = new CountService
    val server = Http.serve(":9010", service)
    Await.ready(server)
  }
}

