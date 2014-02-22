package com.wellnr.commons

package object config extends config.ConfigCheckingCapabilities {

  import scala.language.implicitConversions
  import com.typesafe.config.{ Config, ConfigFactory }
  import com.wellnr.commons.config.RichConfig

  /**
   * The "global" plain application configuration settings.
   */
  final val settings: Config = {
    val config = ConfigFactory.load
    config.checkValid(config, "ipsuite")
    config
  }

  import settings._

  /**
   * implicit conversions
   */
  implicit def config2RichConfig(config: Config) = new RichConfig(config)

  /*
   * Applicaion wide settings
   */
  final val cAppName = getString("ips.config.appname", "ipsapplication")

  final val cAppDir = getString("ips.config.home", System.getProperty("user.home") + "/." + cAppName)

}