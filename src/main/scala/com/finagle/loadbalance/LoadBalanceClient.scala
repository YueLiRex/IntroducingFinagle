package com.finagle.loadbalance

import com.twitter.finagle.{Address, Http, Name, Service, http}
import com.twitter.util.Await

object LoadBalanceClient {
  def main(args: Array[String]): Unit = {
    val name: Name = Name.bound(Address("localhost", 10010), Address("localhost", 10011), Address("localhost", 10012))
    //define a client
    val client: Service[http.Request, http.Response] = Http.newService(name, "client")
    //define a request
    val request = http.Request(http.Method.Get, "/?num=5")
    //apply request on the client
    val response = client(request)
    //print response
    response.foreach(rep => println(rep.getContentString()))
    Await.result(response)
  }
}
