package com.wellnr.commons.config

/**
 * Copyright 2013. Michael Wellner.
 *
 * Use this trait for package objects which are loading settings from the configuration.
 *
 * @author Michael Wellner
 * @since 2013/10/12
 */
trait ConfigCheckingCapabilities

  extends DelayedInit {

  override def delayedInit(body: ⇒ Unit): Unit = {
    try {
      body
    } catch {
      case e: Throwable ⇒ handleError(e)
    }
  }

  // TODO: Handle Error
  def handleError(e: Throwable) {
    println(e)
    throw e
    System.exit(1)
  }

}