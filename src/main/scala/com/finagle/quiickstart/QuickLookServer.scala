package com.finagle.quiickstart

import com.twitter.finagle.{http, Service, Http}
import com.twitter.util.Await

object QuickLookServer {
  def main(args: Array[String]): Unit = {
    val service: Service[http.Request, http.Response] = new PlusTenService
    val server = Http.serve(":9090", service)
    Await.ready(server)
  }
}
