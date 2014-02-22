package com.wellnr.commons.exception

/**
 * Copyright 2013. Michael Wellner.
 *
 * Runtime exceptions with a technical reason can extend this class.
 *
 * @author Michael Wellner
 * @since Nov 19, 2013
 */
abstract class ApplicationRuntimeException(val iThrowable: Option[Throwable], val iArguments: Any*) extends RuntimeException with ExceptionCapabilities {

  /**
   * {@inheritDoc}
   */
  val iBaseExceptionClass = classOf[ApplicationRuntimeException]

}