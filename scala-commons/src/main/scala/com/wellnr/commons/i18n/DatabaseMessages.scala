package com.wellnr.commons.i18n

import c10n.annotations.{ De, En }
import c10n.C10NMessages

/**
 * Copyright 2013. Michael Wellner.
 *
 * This trait contains all database messages.
 *
 * @author Michael Wellner
 * @since Nov 19, 2013
 */
@C10NMessages
trait DatabaseMessages extends Messages {

  @En("Application database initialized with file {0}.")
  @De("Die Datenbank der Anwendung wurde initialisiert. Die Datenbank liegt in der Datei {0}.")
  def initialized(filename: String): String

  @En("Start working with database session {0}.")
  @De("Es wurde eine neue Datanbank-Session gestartet: {0}.")
  def startSession(sessionname: String): String

  @En("Finish working with session {0}.")
  @De("Datenbank-Session {0} wird nicht mehr verwendet.")
  def closeSession(sessionname: String): String

  @En("Start transaction in session {0}.")
  @De("Starte Transaktion in Session {0}.")
  def startTransaction(sessionname: String): String

  @En("Finish transaction in session {0}.")
  @De("Beende Transaktion in Session {0}.")
  def closeTransaction(sessionname: String): String

}