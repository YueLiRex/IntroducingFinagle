package com.finagle.quiickstart

import com.twitter.finagle.{SimpleFilter, http, Service, Http}
import com.twitter.util.{Future, Await}

object QuickLookServer {
  def main(args: Array[String]): Unit = {
    val service: Service[http.Request, http.Response] = new PlusTenService

    val countClient = Http.newService("localhost:9010")

    val countFilter = new CountFilter[http.Request, http.Response](countClient)

    val serviceWithCountFilter = countFilter.andThen(service)

//    val server = Http.serve(":9090", service)
    val server = Http.serve(":9090", serviceWithCountFilter)

    Await.ready(server)
  }
}

class CountFilter[Req, Rep](countClient: Service[http.Request, http.Response]) extends SimpleFilter[Req, Rep] {
  override def apply(request: Req, service: Service[Req, Rep]): Future[Rep] = {
    val countRequest = http.Request(http.Method.Post, "/?count=5")
    countClient(countRequest)
    service(request)
  }
}
