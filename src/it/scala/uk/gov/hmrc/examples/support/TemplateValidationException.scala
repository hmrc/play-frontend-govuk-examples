package uk.gov.hmrc.examples.support

class TemplateValidationException(message: String) extends IllegalArgumentException(message)

object TemplateValidationException {
  def unapply(arg: TemplateValidationException): Option[String] =
    Some(arg.getMessage)
}
