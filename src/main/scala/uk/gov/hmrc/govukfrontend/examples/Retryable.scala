package uk.gov.hmrc.govukfrontend.examples

import scala.annotation.tailrec
import scala.concurrent.duration.{Duration, DurationInt}


trait Retryable {

  @tailrec
  final def retryUntil(condition: => Boolean, conditionDesc: Option[String] = None, attempts: Int = 20, interval: Duration = 300.millis)(fn: => Unit): Unit =
    if (attempts > 0 && !condition) {
      fn
      Thread.sleep(interval.length)
      retryUntil(condition, conditionDesc, attempts - 1, interval)(fn)
    }
    else if (!condition)
      throw new Exception(s"Failed to achieve retry condition${conditionDesc.map(s => s" $s").getOrElse("")}.")

}
