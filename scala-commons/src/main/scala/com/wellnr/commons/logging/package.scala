package com.wellnr.commons

/**
 * This package object contains the settings of the packages classes.
 *
 * @author Michael Wellner
 * @since 2013/10/12
 */
package object logging extends config.ConfigCheckingCapabilities {

  import com.twitter.logging.{ Logger => TwitterLogger }
  import com.wellnr.commons.config._
  import config.settings._

  final val cLogFile = getString("wellnr.config.logging.logfile", cAppDir + "/application.log")

  final val cLogLevel = getString("wellnr.config.logging.level", "TRACE").toUpperCase match {
    case "CRITICAL" => TwitterLogger.CRITICAL
    case "DEBUG" => TwitterLogger.DEBUG
    case "ERROR" => TwitterLogger.ERROR
    case "FATAL" => TwitterLogger.FATAL
    case "INFO" => TwitterLogger.INFO
    case "TRACE" => TwitterLogger.TRACE
    case "WARNING" => TwitterLogger.WARNING
    case _ => TwitterLogger.ALL
  }

}