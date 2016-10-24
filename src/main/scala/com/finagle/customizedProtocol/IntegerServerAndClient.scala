package com.finagle.customizedProtocol

import java.net.InetSocketAddress

import com.twitter.conversions.time._
import com.twitter.finagle.Service
import com.twitter.finagle.builder.{ClientBuilder, ServerBuilder}
import com.twitter.util.{Duration, Future}
import scodec.Codec

object IntegerServerAndClient extends Factories with CodecConversions {


  /**
    * Creates a Finagle server from a function given that we have scodec codecs
    * for both the input and output types.
    */
  def server[I, O](port: Int)(f: I => Future[O])(implicit ic: Codec[I], oc: Codec[O]) =
    ServerBuilder()
      .name("server")
      .codec(codecFactory[I, O])
      .bindTo(new InetSocketAddress(port))
      .build(new Service[I, O] {
        def apply(i: I) = f(i)
      })

  /**
    * Creates a Finagle client given input and output types with scodec codecs.
    */
  def client[I, O](host: String, timeout: Duration = 1.second)
                  (implicit ic: Codec[I], oc: Codec[O]) =
    ClientBuilder()
      .name("client")
      .codec(codecFactory[I, O])
      .hosts(host)
      .hostConnectionLimit(1)
      .timeout(timeout)
      .build()
}