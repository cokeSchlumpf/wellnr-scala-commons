package com.wellnr.commons.i18n

import c10n.annotations.{ De, En }

/**
 * Copyright 2013. Michael Wellner.
 *
 * Messages used in the package com.wellnr.commons.model.
 *
 * @author Michael Wellner
 * @since 2014/03/15
 */
trait ModelMessages extends Messages {

  @En("Enumeration value {1} not found for type {0}.")
  @De("Der Wert {1} ist kein gültiger Wert für die Enumeration {0}.")
  def enumerationValueNotFound(enumType: Class[_], enumValue: String): String

}
