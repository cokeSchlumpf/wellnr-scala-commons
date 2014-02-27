package com.wellnr.commons

package object config extends config.ConfigCheckingCapabilities {

  import scala.language.implicitConversions
  import com.typesafe.config.{ Config, ConfigFactory }

  /**
   * The "global" plain application configuration settings.
   */
  final val settings: Config = {
    val config = ConfigFactory.load
    config.checkValid(config, "wellnr")
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
  final val cAppName = getString("wellnr.config.appname", "wellnr-application")

  final val cAppDir = getString("wellnr.config.home", System.getProperty("user.home") + "/." + cAppName)

}