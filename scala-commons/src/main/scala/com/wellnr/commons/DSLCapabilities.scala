package com.wellnr.commons

import com.wellnr.commons.exception.ExceptionCapabilities

/**
 * Copyright 2013. Michael Wellner.
 *
 * This trait extends a class with capabilities to use the library functionalities with
 * a domain specific language.
 *
 * @author Michael Wellner
 * @since Oct 22, 2013
 */
trait DSLCapabilities {

  /* The dummies are used to work with no-parameter methods */
  sealed trait Dummy;
  object it extends Dummy;
  object and extends Dummy;

  /* This class extends the Exception functionalities with an DSL */
  case class ExceptionDSL[T <: ExceptionCapabilities](iException: T) {

    def log(dummy: Dummy) = {
      iException.log
      this
    }

    def print(dummy: Dummy) = {
      println(iException.iFormattedMessage)
      this
    }

    def escalate(dummy: it.type) = iException.escalate

  }

  /* The Create object allows to create objects and their wrappers in a DSL like way */
  object Create {

    def a[T <: ExceptionCapabilities](_e: T) = ExceptionDSL(_e)

  }
}