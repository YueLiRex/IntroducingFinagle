package com.finagle.quiickstart

import com.twitter.finagle.{Service, Http}
import com.twitter.finagle.http
import com.twitter.util.Await

object QuickLookClient {
  def main(args: Array[String]): Unit = {
    //define a client
    val client: Service[http.Request, http.Response] = Http.newService("localhost:9090")
    //define a request
    val request = http.Request(http.Method.Get, "/?num=5")
    //apply request on the client
    val response = client(request)
    //print response
    response.foreach(rep => println(rep.getContentString()))
    Await.result(response)
  }
}
