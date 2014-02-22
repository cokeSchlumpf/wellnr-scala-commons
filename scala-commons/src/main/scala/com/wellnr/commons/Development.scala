package com.wellnr.commons

import com.wellnr.commons.logging.LoggingCapabilities

/**
 * Copyright 2014. Michael Wellner.
 *
 * This object contains markup functions for development time.
 *
 * @author info@michaelwellner.de
 * @since Jan 15, 2014
 */
object Development extends LoggingCapabilities {

  /**
   * Use this function to indicate that a function is not supported.
   *
   * @throws IllegalAccessException
   */
  def NotImplemented[T]: T = {
    val e = new IllegalAccessException("This function call is not supported.")
    LOG.error(e)
    throw e
  }

  /**
   * Use this function to indicate that a function needs to be implemented.
   *
   * @throws IllegalAccessException
   */
  def TODO[T]: T = {
    val e = new IllegalAccessException("This function is not implemented yet.")
    LOG.error(e)
    throw e
  }

  /**
   * Markup method to state that you need to do nothing. And well, this method really does nothing.
   */
  def DoNothing = {

  }

}