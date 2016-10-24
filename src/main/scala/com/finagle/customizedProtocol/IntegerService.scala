package com.finagle.customizedProtocol

import com.twitter.finagle.Service
import com.twitter.util.Future

class IntegerService extends Service[Int, Int]{
  override def apply(request: Int): Future[Int] = {
    if (request > 10) {
      Future.value(request * request)
    } else {
      Future.value(request - 1)
    }
  }
}
