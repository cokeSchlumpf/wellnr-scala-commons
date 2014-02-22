package com.wellnr.commons.exception

import com.wellnr.commons.Reflection._
import com.wellnr.commons.Text._
import com.wellnr.commons.logging.LoggingCapabilities
import scala.util.Try

/**
 * This trait is the base trait for all exceptions thrown by the application.
 *
 * @author Michael Wellner
 * @since 2013/10/22
 */
protected[exception] trait ExceptionCapabilities extends LoggingCapabilities { self: Exception =>

  /**
   * The root exception type.
   */
  protected[exception] val iBaseExceptionClass: Class[_ <: ExceptionCapabilities]

  /**
   * A throwable which caused this exception.
   */
  val iThrowable: Option[Throwable]

  /**
   * The arguments which are used to format the translated message.
   */
  val iArguments: Seq[Any];

  /**
   * The identifier contains the name of the exception appended to the name of all parent exceptions.
   */
  lazy val iIdentifier = this.superclasses(Some(iBaseExceptionClass.getSuperclass)).map(_.getSimpleName.toString).reverse.mkString(".")

  /**
   * Returns a human readable message, possibly translated if there is an translation.
   */
  lazy val iFormattedMessage = "%s %s" format (iIdentifier, iArguments.asArgumentList)

  /**
   * Logs this error.
   */
  def log {
    iThrowable match {
      case Some(throwable) =>
        LOG.error(throwable, iFormattedMessage)
      case None =>
        LOG.error(iFormattedMessage)
    }
  }

  /**
   * Throws this exceptions and wraps it with scala.util.Try - The Scala way of error handling.
   */
  def escalate: Try[Nothing] = Try(throw this)
}
