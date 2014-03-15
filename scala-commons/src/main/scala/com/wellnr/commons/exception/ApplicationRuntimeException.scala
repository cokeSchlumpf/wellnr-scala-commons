package com.wellnr.commons.exception

/**
 * Copyright 2013. Michael Wellner.
 *
 * Runtime exceptions with a technical reason can extend this class.
 *
 * @author Michael Wellner
 * @since Nov 19, 2013
 */
abstract class ApplicationRuntimeException(protected val iThrowable: Option[Throwable], protected val iMessage: String) extends RuntimeException with ExceptionCapabilities {

  /**
   * See [[ExceptionCapabilities.iBaseExceptionClass]].
   */
  protected[exception] val iBaseExceptionClass = classOf[ApplicationRuntimeException]

}

/**
 * Base Excetpion for all Exceptions thrown in scala-commons.
 *
 * @param throwable
 *                   The throwable which may have caused the Exception.
 * @param message
 *                   The excpetions message.
 */
abstract class ScalaCommonsException(throwable: Option[Throwable], message: String) extends ApplicationRuntimeException(throwable, message)