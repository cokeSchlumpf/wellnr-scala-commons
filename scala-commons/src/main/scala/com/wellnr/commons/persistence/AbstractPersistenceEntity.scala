package com.wellnr.commons.persistence

import com.wellnr.commons.Development._
import com.wellnr.commons.Text._
import com.wellnr.commons.model.{ AbstractEntity, ID }
import javax.persistence.{ MappedSuperclass, GeneratedValue, Id, GenerationType, Convert }

/**
 * Copyright 2013. Michael Wellner.
 *
 * This class defines an abstract entity within your business model which can be used with the persistence library.
 *
 * @author Michael Wellner
 * @since 2013/10/17
 */
@MappedSuperclass
class AbstractPersistenceEntity extends AbstractEntity[AbstractPersistenceEntity] {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Convert(converter = classOf[OptionIntConverter])
  override val id: Option[ID[AbstractPersistenceEntity]] = None

  /**
   * Creates a case class like toString() output.
   */
  protected def asString(pArgs: Any*) = {
    s"${this.getClass.getSimpleName}${pArgs.asArgumentList}"
  }

  def withId(id: ID[AbstractPersistenceEntity]) = NotImplemented

}

/**
 * This trait extends an AbstractPersistencyEntity with capabilities to create different versions of an entity.
 *
 * @author Michael Wellner
 * @since 2013/10/22
 */
trait VersioningCapabilities extends AbstractPersistenceEntity {

  @Convert(converter = classOf[IntConverter])
  val version: Int;

}