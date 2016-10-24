package com.finagle.customizedProtocol

import com.twitter.finagle.Service
import com.twitter.util.Await
import scodec.codecs.implicits.{ implicitIntCodec => _, _ }

object Client {
  def main(args: Array[String]): Unit = {

    implicit val intgerCodec = scodec.codecs.uint8

    //define a client
    val client: Service[Int, Int] = IntegerServerAndClient.client[Int, Int]("localhost:9191")
    //define a request
    val request = 4
    //apply request on the client
    val response = client(request)
    //print response
    response.foreach(rep => println(s"This is response $rep"))
    Await.result(response)
  }
}
