package com.wellnr.commons.play

/**
 * This package object contains the settings of the packages classes.
 *
 * @author Michael Wellner
 * @since 2013/10/17
 */
package object database extends com.wellnr.commons.config.ConfigCheckingCapabilities {

  import com.wellnr.commons.config._
  import com.wellnr.commons.config.settings._

  final val cApplicationDatabaseFile = getString("wellnr.config.database.applicationDatabaseFile", cAppDir + "/applicationDatabase")

}