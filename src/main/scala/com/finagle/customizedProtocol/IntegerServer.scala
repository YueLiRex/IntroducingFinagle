package com.finagle.customizedProtocol

import java.net.SocketAddress

import com.twitter.finagle.{ListeningServer, Server, ServiceFactory}
import com.twitter.util._

object IntegerServer extends Server[Int, Int] {
  override def serve(addr: SocketAddress, service: ServiceFactory[Int, Int]): ListeningServer = {
    new ListeningServer with CloseAwaitably {
      override def boundAddress: SocketAddress = addr

      override protected def closeServer(deadline: Time): Future[Unit] = Future.Done
    }
  }
}
