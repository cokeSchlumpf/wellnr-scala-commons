package com.wellnr.commons.i18n

import c10n.C10NMessages

/**
 * Copyright 2013. Michael Wellner.
 *
 * This trait collects all traits for library messages.
 *
 * @author Michael Wellner
 * @since Nov 19, 2013
 */
@C10NMessages
trait LibraryMessages extends Messages {

  def database: DatabaseMessages
  def logging: LoggingMessages
  def base: BaseMessages
  def model: ModelMessages

}