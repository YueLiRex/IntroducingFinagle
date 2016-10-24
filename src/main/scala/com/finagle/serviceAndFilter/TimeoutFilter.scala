package com.finagle.serviceAndFilter

import com.twitter.finagle.{SimpleFilter, Service}
import com.twitter.util.{Duration, Timer, Future}

class TimeoutFilter[Req, Rep](timeout: Duration, timer: Timer)
  extends SimpleFilter[Req, Rep] {

  def apply(request: Req, service: Service[Req, Rep]): Future[Rep] = {
    val res = service(request)
    res.within(timer, timeout)
  }
}
