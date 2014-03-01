package com.wellnr.commons.i18n

import c10n.C10NMessages
import c10n.annotations.{ De, En }

/**
 * Copyright 2013. Michael Wellner.
 *
 * This trait represents a base trait for all translations, used within the Application.
 * In this we have only two simple examples implemented to show how it works. Extend this trait to create your own messages.
 *
 * @author Michael Wellner
 * @since Nov 19, 2013
 */
@C10NMessages
trait Messages {

  @En("Hello!")
  @De("Hallo!")
  def hello: String

  @En("Hello {0}, how are you doing today?")
  @De("Hallo {0}, wie geht es dir heute?")
  def complexHello(name: String): String

}