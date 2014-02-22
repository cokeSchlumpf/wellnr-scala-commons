package com.wellnr.commons

/**
 * This package object contains the settings of the packages classes.
 *
 * @author Michael Wellner
 * @since 2013/10/12
 */
package object logging extends config.ConfigCheckingCapabilities {

  import com.wellnr.commons.config._
  import config.settings._

  final val cLogFile = getString("wellnr.config.logging.logfile", cAppDir + "/application.log")

}