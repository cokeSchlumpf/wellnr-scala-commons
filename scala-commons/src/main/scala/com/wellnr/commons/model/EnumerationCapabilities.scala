package com.wellnr.commons.model

import com.wellnr.commons.exception.ScalaCommonsException
import com.wellnr.commons.i18n.i18n
import scala.language.existentials

/**
 * Copyright 2013. Michael Wellner.
 *
 * This class defines an abstract entity within your business model.
 *
 * @author Michael Wellner
 * @since 2014/03/15
 */
trait EnumerationCapabilities {

  type ENUM_TYPE

  val asString: ENUM_TYPE => String

  /**
   * A Sequence of all enumeration values.
   */
  val values: Seq[ENUM_TYPE]

  /**
   * Returns the enumeration value which is associated with the string.
   *
   * @param value
   * The value which should be transformed to the enumeration type.
   * @return
   * The related enumeration value.
   * @throws EnumerationValueNotFoundException
   * If the value cannot be transformed to the enumeration type.
   */
  def forString(value: String): ENUM_TYPE = values.find(asString(_) == value) match {
    case Some(enumerationValue) => enumerationValue
    case _ => throw EnumerationValueNotFoundException(this.getClass, value)
  }

}

abstract class AbstractEnumeration[T](val asString: T => String) extends EnumerationCapabilities {

  type ENUM_TYPE = T

}

/**
 * Helper for simplified usage of [[EnumerationCapabilities]].
 *
 * Extend your enumeration type base class with [[EnumerationWithName.BaseType]] and use
 * [[EnumerationWithName.EnumerationCapabilities]] for your enumeration object.
 */
object EnumerationWithName {
  trait BaseType {
    val name: String
  }

  abstract class EnumerationCapabilities[T <: BaseType] extends AbstractEnumeration[T](_.name)
}

/**
 * Helper for simplified usage of [[EnumerationCapabilities]].
 *
 * Extend your enumeration type base class with [[EnumerationWithValue.BaseType]] and use
 * [[EnumerationWithValue.EnumerationCapabilities]] for your enumeration object.
 */
object EnumerationWithValue {
  trait BaseType {
    val value: String
  }

  abstract class EnumerationCapabilities[T <: BaseType] extends AbstractEnumeration[T](_.value)
}

/**
 * Exception which is thrown when a string value can't be transformed to an enumeration value.
 *
 * @param enumType
 * The type of the enumeration.
 * @param value
 * The value which should be transformed to the enumeration type.
 */
case class EnumerationValueNotFoundException(enumType: Class[_], value: String) extends ScalaCommonsException(None, i18n.model.enumerationValueNotFound(enumType, value))
