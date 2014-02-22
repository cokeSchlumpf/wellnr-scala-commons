package com.wellnr.commons

import scala.language.implicitConversions
import java.util.Date
import java.text.SimpleDateFormat
import com.wellnr.commons.logging.LoggingCapabilities

/**
 * Copyright 2014. Michael Wellner.
 *
 * This object contains several often used date and time functions.
 *
 * @author info@michaelwellner.de
 * @since Jan 22, 2014
 */
object DateTime extends LoggingCapabilities {

  def NOW = new java.util.Date

  /**
   * Formats a java.util.Date to String (date only).
   *
   * @param date
   */
  implicit def dateToString(date: Date) = {
    // TODO: Read the current language settings to format the date
    val sf = new SimpleDateFormat("dd.MM.YYYY")
    sf.format(date)
  }

  /**
   * Formats a java.util.Date to String (date and time).
   *
   * @param date
   */
  implicit def dateToDateTimeString(date: Date) = {
    // TODO: Read the current language settings to format the date
    LOG.enter(date)
    val sf = new SimpleDateFormat("dd.MM.YYYY HH:mm")
    sf.format(date)
  }

  /**
   * implicit conversion to DateHelper.
   *
   * @param date
   * 	The actual date.
   */
  implicit def date2dateutil(date: Date) = DateHelper(date)

  /**
   * Wrapper class for Date utility methods.
   */
  case class DateHelper(date: java.util.Date) {

    def asDateTimeString = dateToDateTimeString(date)

    def asDaetString = dateToString(date)

  }

}