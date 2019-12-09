package uk.gov.hmrc.govukfrontend.examples

object PlayVersions {

  sealed trait PlayVersion {
    val version: Int
    override def toString: String = version.toString
  }

  case class Play25() extends PlayVersion {val version: Int = 25}
  case class Play26() extends PlayVersion {val version: Int = 26}

  val implementedPlayVersions: Iterable[PlayVersion] = Iterable(Play25(), Play26())
}
