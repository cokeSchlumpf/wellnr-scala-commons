package com.wellnr.commons.play.database

import play.api.db.slick.Config.driver.simple._
import Database.threadLocalSession
import com.wellnr.commons.logging.LoggingCapabilities
import c10n.C10N
import com.wellnr.commons.i18n.i18n

/**
 * Copyright 2013. Michael Wellner.
 *
 * Use this trait if you want to access the a database.
 *
 * @author Michael Wellner
 * @since 2013/10/12
 */
@deprecated("Use play capabilities instead.", "1.0")
trait DatabaseCapabilities extends LoggingCapabilities {

  /*
   * References the application database.
   */
  private[DatabaseCapabilities] val database = Database.forURL(s"jdbc:h2:$cApplicationDatabaseFile", driver = "org.h2.Driver")

  LOG.info(i18n.database.initialized(cApplicationDatabaseFile))

  /**
   * Work with the database within a session. It will be closed automatically at the end.
   */
  def withDatabaseSession[T](f: => T): T = withDatabaseSession { _ => f }

  /**
   * Work with the database within a session. It will be closed automatically at the end.
   */
  def withDatabaseSession[T](f: Session => T) = database withSession { session: Session =>
    LOG.trace(i18n.database.startSession(session.toString))
    val result = f(session)
    LOG.trace(i18n.database.closeSession(session.toString))
    result
  }

  /**
   * Work with the database within a transaction. It will be closed and committed automatically at the end.
   */
  def withDatabaseTransaction[T](f: => T): T = withDatabaseTransaction { _ => f }

  /**
   * Work with the database within a transaction. It will be closed and committed automatically at the end.
   */
  def withDatabaseTransaction[T](f: Session => T) = database withTransaction { session: Session =>
    LOG.trace(i18n.database.startSession(session.toString))
    val result = f(session)
    LOG.trace(i18n.database.closeTransaction(session.toString))
    result
  }

}