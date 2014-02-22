package com.wellnr.commons.persistence

import javax.persistence.Converter
import javax.persistence.AttributeConverter
import com.wellnr.commons.logging.LoggingCapabilities

/**
 * Abstract converter for Types wrapped by scala.Option.
 *
 * @author Michael Wellner
 * @since 2013/10/18
 */
abstract class OptionConverter[X, Y](val iGenericConverter: AttributeConverter[X, Y]) extends AttributeConverter[Option[X], Y] with LoggingCapabilities {

  /**
   * Converts the data (wrapped by scala.Option) to the simple database type.
   */
  override def convertToDatabaseColumn(pAttribute: Option[X]) = {
    LOG.enter(pAttribute)

    val result = pAttribute match {
      case Some(attribute) => iGenericConverter.convertToDatabaseColumn(attribute)
      case None => null.asInstanceOf[Y]
    }

    LOG.exit(result)
  }

  /**
   * Converts the data to the type used by the application (wrapped by scala.Option).
   */
  override def convertToEntityAttribute(pDbData: Y) = {
    LOG.enter(pDbData)

    val result = pDbData match {
      case null => None
      case data => Some(iGenericConverter.convertToEntityAttribute(data))
    }

    LOG.exit(result)
  }

}

/**
 * Converter for scala.Option[Int] Fields.
 *
 * @author Michael Wellner
 * @since 2013/10/18
 */
@Converter(autoApply = true)
class OptionIntConverter extends OptionConverter[Int, Integer](new IntConverter)

/**
 * Converter for scala.Int fields.
 *
 * @author Michael Wellner
 * @since 2013/10/22
 */
@Converter(autoApply = true)
class IntConverter extends AttributeConverter[Int, Integer] {

  override def convertToDatabaseColumn(pAttribute: Int) = pAttribute

  override def convertToEntityAttribute(pDbData: Integer) = pDbData

}