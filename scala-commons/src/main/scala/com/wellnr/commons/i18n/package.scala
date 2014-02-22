package com.wellnr.commons

import c10n.C10N
import c10n.annotations.DefaultC10NAnnotations
import java.util.Locale

/**
 * Copyright 2013. Michael Wellner.
 *
 * Global helper to access i18n translations.
 *
 * @author Michael Wellner
 * @since Nov 19, 2013
 */
package object i18n extends config.ConfigCheckingCapabilities {

  import com.wellnr.commons.config._
  import config.settings._

  final val cLocale = getString("ips.config.i18n.locale", "en")

  /*
   * Configure c10n with default local bindings.
   */
  C10N.configure(new DefaultC10NAnnotations())

  /*
   * Set default locale
   */
  Locale.setDefault(Locale.forLanguageTag(cLocale))

  val i18n = C10N.get(classOf[LibraryMessages])

}