package com.wellnr.commons.exception

/**
 * All exceptions which have a technical reason should extend this.
 *
 * @author Michael Wellner
 * @since 2013/10/22
 */
abstract class ApplicationBusinessException(protected val iThrowable: Option[Throwable], protected val iMessage: String) extends Exception with ExceptionCapabilities {

  protected[exception] val iBaseExceptionClass = classOf[ApplicationBusinessException]

}