package com.wellnr.commons.exception

/**
 * All exceptions which have a technical reason should extend this.
 *
 * @author Michael Wellner
 * @since 2013/10/22
 */
abstract class ApplicationBusinessException(val iThrowable: Option[Throwable], val iArguments: Any*) extends Exception with ExceptionCapabilities {

  val iBaseExceptionClass = classOf[ApplicationBusinessException]

  override def getMessage() = this.iFormattedMessage

}