package com.wellnr.commons.i18n

import c10n.annotations.{ De, En }
import c10n.C10NMessages

/**
 * Created by michael on 01/03/14.
 */
@C10NMessages
trait BaseMessages extends Messages {

  @En("MMMM dd, yyyy")
  @De("dd.MM.yyyy")
  def dateFormatLong: String

  @En("MM/dd/yyyy")
  @De("dd.MM.yyyy")
  def dateFormat: String

  @En("MM/dd/yyyy HH:mm")
  @De("dd.MM.yyyy HH:mm")
  def dateTime: String

}
