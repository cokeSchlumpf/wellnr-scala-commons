package com.wellnr.commons.i18n

import c10n.annotations.{ De, En }
import c10n.C10NMessages

/**
 * Copyright 2013. Michael Wellner.
 *
 * This trait defines all messages used by the logging package.
 *
 * @author Michael Wellner
 * @since Nov 19, 2013
 */
@C10NMessages
trait LoggingMessages extends Messages {

  @En("Enter {0}{1}.")
  @De("Starte {0}{1}.")
  def enter(methodname: String, argumentlist: String): String

  def enter(methodname: String): String = enter(methodname, "")

  @En("Exit {0} with result {1}.")
  @De("Beende {0} mit dem Ergebnis {1}.")
  def exit(methodname: String, result: Any): String

  @En("Exit {0}.")
  @De("Beende {0}.")
  def exit(methodname: String): String

  @En("Logger initialized on file {0} with log level {1}.")
  @De("Der Logger wurde erfolgreich initialisiert. Die Logfile ist {0}, das Log-Level {1}.")
  def initialized(filename: String, loglevel: String): String

}