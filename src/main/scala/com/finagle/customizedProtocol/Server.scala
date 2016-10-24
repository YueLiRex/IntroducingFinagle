package com.finagle.customizedProtocol

import com.twitter.finagle.Service
import com.twitter.util.Await
import scodec.codecs.implicits.{ implicitIntCodec => _, _ }

object Server {
  def main(args: Array[String]): Unit = {
    implicit val intgerCodec = scodec.codecs.uint8

    val service: Service[Int, Int] = new IntegerService
//    val server = IntegerServer.serve(":9191", service)
    val server = IntegerServerAndClient.server[Int, Int](9191)(service)
    Await.ready(server)
  }
}
