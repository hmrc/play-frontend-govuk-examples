package uk.gov.hmrc.support

import com.google.inject.{Binder, Module}
import play.api.Configuration
import uk.gov.hmrc.hmrcfrontend.config.TudorCrownConfig

case class TudorCrownModule() extends Module {
  override def configure(binder: Binder): Unit =
    binder.bind(classOf[TudorCrownConfig]).toInstance(TudorCrownConfig(Configuration.empty))
}
