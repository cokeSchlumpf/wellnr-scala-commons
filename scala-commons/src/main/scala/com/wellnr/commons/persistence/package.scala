package com.wellnr.commons

import scala.annotation.meta.field
import javax.persistence._

/**
 * This package object contains the settings of the packages classes.
 *
 * @author Michael Wellner
 * @since 2013/10/18
 */
package object persistence extends config.ConfigCheckingCapabilities {

  import com.wellnr.commons.config._
  import config.settings._

  final val cJDBCDriver = getString("wellnr.config.persistence.jdbc-driver", "org.h2.Driver")
  final val cJDBCUrl = getString("wellnr.config.persistence.jdbc-url", s"jdbc:h2:$cAppDir/applicationDb")
  final val cJDBCUser = getString("wellnr.config.persistence.jdbc-user", "ips")
  final val cJDBCPassword = getString("wellnr.config.persistence.jdbc-password", "")
  final val cPersistenceUnit = getString("wellnr.config.persistence.persistence-unit", "ips-model")

  /*
   * Define convenience types for annotations which can be used on (constructor) fields.
   */
  type convert = Convert @field
  type joinColumn = JoinColumn @field
  type oneToMany = OneToMany @field
  type onToOne = OneToOne @field
  type temporal = Temporal @field

}