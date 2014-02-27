package com.wellnr.commons.logging

import scala.language.implicitConversions

import org.apache.commons.lang3.StringUtils

import com.twitter.logging.{ ConsoleHandler, FileHandler, Level, Logger => TwitterLogger, LoggerFactory }
import com.twitter.logging.config.Policy
import com.wellnr.commons.Text._
import com.wellnr.commons.i18n.i18n

/**
 * Copyright 2013. Michael Wellner.
 *
 * This class wraps the twitter util-logging library and configures the logger.
 *
 * @author Michael Wellner
 * @since 2013/10/12
 */
class Logger(node: String) {

  /*
   * Handler for logging output on console.
   */
  private val iConsoleHandlerConfig = ConsoleHandler()

  /*
   * Handler for logging output on file-system.
   * Do not use Policy.SigHub since this will not work on Winodws operating systems.
   */
  private val iFileHandlerConfig = FileHandler(filename = cLogFile, rollPolicy = Policy.Daily)

  /*
   * Actual instance of the logger.
   */
  private val log = LoggerFactory(
    node = node,
    level = Some(cLogLevel),
    handlers = List(iConsoleHandlerConfig, iFileHandlerConfig),
    useParents = false)()

  log.info(i18n.logging.initialized(cLogFile, cLogLevel.asString))

  /**
   * This method returns the name of the method which called the Logger method.
   */
  private def calledFromMethod: String = {
    val stackTraceElement = 7;
    val calledFrom = Thread.currentThread.getStackTrace()(stackTraceElement)
    val method = StringUtils.substringAfterLast(calledFrom.getClassName, ".") + "." + calledFrom.getMethodName

    return method
  }

  /**
   * Prints a debug message to the log.
   */
  def debug(msg: String, items: Any*) = log.debug(msg, items: _*)

  /**
   * Call this method to indicate the beginning of a new method.
   *
   * @param pArgs The arguments of the method call.
   */
  def enter(pArgs: Any*) {
    ifTrace {
      val args = pArgs.asArgumentList
      log.trace(i18n.logging.enter(calledFromMethod, args))
    }
  }

  /**
   * Call this method to indicate the beginning of a new method.
   */
  def enter = ifTrace { trace(i18n.logging.enter(calledFromMethod)) }

  /**
   * Call this method to indicate an error.
   */
  def error(msg: String, items: Any*) = log.error(msg, items: _*)

  /**
   * Call this method to indicate an error with an catched or created exception.
   */
  def error(thrown: Throwable, msg: String, items: Any*) = log.error(thrown, msg, items: _*)

  /**
   * Call this method to indicate an error with an created exception.
   */
  def error(thrown: Throwable) = log.error(thrown, thrown.getMessage)

  /**
   * Call this method to indicate the end of an function.
   *
   * @param result The result of the function.
   */
  def exit[T](result: T): T = {
    ifTrace { trace(i18n.logging.exit(calledFromMethod, result.asString)) }
    result
  }

  /**
   * Call this method to indicate the end of a method.
   */
  def exit: Unit = ifTrace { trace(i18n.logging.exit(calledFromMethod)) }

  /**
   * f will only be executed if trace is enabled.
   */
  private def ifTrace[T](f: => T): Option[T] = {
    val myLevel = log.getLevel
    if ((myLevel eq null) || (Level.TRACE.intValue >= myLevel.intValue)) {
      Some(f)
    } else {
      None
    }
  }

  /**
   * Call this method to log an information.
   */
  def info(msg: String, items: Any*) = log.info(msg, items: _*)

  /**
   * Call this method for any tracing information.
   */
  def trace(msg: String, items: Any*) = log.trace(msg, items: _*);

  /**
   * Call this method to log a warning.
   */
  def warn(msg: String, items: Any*) = log.warning(msg, items: _*)

}

object DefaultLogger extends Logger("scala-commons")
