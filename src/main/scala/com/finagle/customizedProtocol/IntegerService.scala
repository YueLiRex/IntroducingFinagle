package com.finagle.customizedProtocol

import com.twitter.finagle.Service
import com.twitter.util.Future

class IntegerService extends Service[Int, Int]{
  var count = 0
  override def apply(request: Int): Future[Int] = {
    Future.value(count + request)
  }
}
