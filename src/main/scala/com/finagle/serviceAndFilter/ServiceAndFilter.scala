package com.finagle.serviceAndFilter

import com.twitter.finagle.{Http, http}
import com.twitter.util.{Await, Duration, JavaTimer}

object ServiceAndFilter {
  def main(args: Array[String]): Unit = {

    val quickLookClient = Http.newService("localhost:9090")

    val timeoutFilter = new TimeoutFilter[http.Request, http.Response](Duration.fromSeconds(1), new JavaTimer(false))

    val quickLookClientWithTimeoutFilter = timeoutFilter.andThen(quickLookClient)

    val request = http.Request(http.Method.Get, "/?num=10")

    val response = quickLookClientWithTimeoutFilter(request)
    //print response
    response.foreach(rep => println(rep.getContentString()))
    Await.result(response)
  }
}



