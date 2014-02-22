package com.wellnr.commons.play.database

import com.wellnr.commons.model.AbstractEntity
import play.api.db.slick.Config.driver.simple._
import com.wellnr.commons.logging.LoggingCapabilities
import com.wellnr.commons.logging.LoggingCapabilities
import com.wellnr.commons.play.C10NInitCapabilities
import com.wellnr.commons.model.ID

/**
 * Copyright 2014. Michael Wellner.
 *
 * Interface for database DAOs.
 *
 * @author info@michaelwellner.de
 * @since Feb 14, 2014
 */
trait DAOCapabilities[I, E] {

  type TId = I
  type TEntity = E

  def entityname: String

  def findById(id: TId)(implicit s: Session): Option[TEntity]

  def count(implicit s: Session): Int

  def insert(entity: TEntity)(implicit s: Session): TEntity

  /**
   * Insert a whole list of new entities.
   *
   * @param
   * 	entities
   * @return
   * 	A list with the entities containing their new id.
   */
  def insertAll(entities: Seq[TEntity])(implicit s: Session): Seq[TEntity] = entities.map(insert)

  def list(implicit s: Session): Seq[TEntity]

  def update(entity: TEntity)(implicit s: Session): TEntity

  def delete(id: TId)(implicit s: Session)
  
  def save(entity: TEntity)(implicit s: Session): TEntity

}

/**
 * Copyright 2014. Michael Wellner.
 *
 * This abstract class represents a base class for DAOs to access database functions. It already contains common use cases.
 *
 * @author info@michaelwellner.de
 * @since Jan 15, 2014
 */
abstract class AbstractEntityDAOCapabilities[E <: AbstractEntity[E], T <: AbstractTable[E]](implicit man: Manifest[T])

  extends C10NInitCapabilities

  with LoggingCapabilities

  with DAOCapabilities[ID[E], E] {

  implicit def idMapper[I <: AbstractEntity[I]] = MappedTypeMapper.base[ID[I], Long](_.id, ID[I](_))

  /**
   * Instance of the concrete Abstract Table
   */
  lazy val entities: T = man.runtimeClass.newInstance.asInstanceOf[T]

  val entityname = entities.tablename

  /**
   * Find an entity by its id.
   *
   * @param id
   */
  def findById(id: ID[E])(implicit s: Session): Option[E] = entities.byId(id).firstOption

  /**
   * Count all entities.
   */
  def count(implicit s: Session) = Query(entities.length).first

  /**
   * Insert a new entity.
   *
   * @param entity
   * @returns A copy of instance with the calculated entity id.
   */
  def insert(entity: E)(implicit s: Session): E = {
    entity.id match {
      case Some(_) =>
        val illegalStateException = new IllegalArgumentException("A new entity is not allowed to have an id.")
        LOG.error(illegalStateException)
        throw illegalStateException
      case None =>
        entity.withId(entities.autoinc.insert(entity))
    }
  }

  /**
   * Returns a list of all entities stored in the database table.
   */
  def list(implicit s: Session) = Query(entities).list

  /**
   * Update an entity.
   *
   * @param id
   * @param entity
   */
  def update(entity: E)(implicit s: Session) = {
    entity.id match {
      case Some(id) =>
        entities.where(_.id === id).update(entity)
        entity
      case None =>
        val illegalArgumentException = new IllegalArgumentException("An entity to be updated is supposed to have an id.")
        LOG.error(illegalArgumentException)
        throw illegalArgumentException
    }
  }

  /**
   * Delete an entity.
   *
   * @param id
   */
  def delete(id: ID[E])(implicit s: Session) {
    entities.where(_.id === id).delete
  }
  
  def save(entity: TEntity)(implicit s: Session) = {
    entity.id match {
      case Some(_) => this.update(entity)
      case None => this.insert(entity)
    }
  }

}

/**
 *
 */
trait JoinCapabilities[E <: AbstractEntity[E], JOINED] {

  /**
   * Returns a list of entities, joined with related objects.
   */
  def join(id: ID[E])(implicit s: Session): JOINED

}

/**
 * Special abstract DAO for ManyToMany Tables.
 */
abstract class AbstractManyToManyDAO[E1 <: AbstractEntity[E1], T1 <: AbstractTable[E1], E2 <: AbstractEntity[E2], T2 <: AbstractTable[E2], T <: AbstractManyToManyTable[E1, T1, E2, T2]](

  /**  The DAO of the `from` entity */
  val from: AbstractEntityDAOCapabilities[E1, T1],

  /** The DAO of the `to` entity */
  val to: AbstractEntityDAOCapabilities[E2, T2],

  /** The abstract many to many table. This is only used to infer type T */
  val tableclass: Class[T])(implicit m: Manifest[T]) extends AbstractEntityDAOCapabilities[ManyToMany[E1, E2], T] {

  /** Returns a list of all entries of FROM and their related TO entities. */
  def allByFrom(implicit s: Session) = {
    (for {
      (froms, tos) <- from.entities innerJoin to.entities
    } yield (froms, tos)).list
  }

  /**
   * Find all related items from the FROM entity.
   *
   * @param entity The FROM entity.
   */
  def findByFrom(entity: E1)(implicit s: Session) = {
    (for {
      relation <- this.entities.where(_.fromColumn === entity.id)
      to <- this.to.entities if relation.toColumn === to.id
    } yield (to)).list
  }

  /**
   * Find all related items from the TO entity.
   *
   * @param entity The TO entity.
   */
  def findByTo(entity: E2)(implicit s: Session) = {
    (for {
      relation <- this.entities.where(_.toColumn === entity.id)
      from <- this.from.entities if relation.fromColumn === from.id
    } yield (from)).list
  }

  /**
   * Find all related items from the FROM entity.
   *
   * @param id The id of the FROM entity
   */
  def findByFromId(id: ID[E1])(implicit s: Session) = {
    from.findById(id) match {
      case Some(fromEntity) => Some((fromEntity, findByFrom(fromEntity)))
      case None => None
    }
  }

  /**
   * Find all related items from the TO entity.
   *
   * @param id
   * 	The id of the TO entity
   */
  def findByToId(id: ID[E2])(implicit s: Session) = {
    to.findById(id) match {
      case Some(fromEntity) => Some((fromEntity, findByTo(fromEntity)))
      case None => None
    }
  }

  /**
   * Insert a new relationship.
   *
   * @param from
   * 	The id of the FROM entity
   *  @param to
   *  	The id of the TO entity
   */
  def insert(from: ID[E1], to: ID[E2])(implicit s: Session): ManyToMany[E1, E2] = {
    this.insert(ManyToMany(None, from, to))
  }

  /**
   * Insert a new relationship.
   *
   * @param from
   * 	The FROM entity
   *  @param to
   *  	The TO entity
   */
  def insert(from: E1, to: E2)(implicit s: Session): ManyToMany[E1, E2] = {
    // TODO: Need to check whether id are not None.
    this.insert(from.id.get, to.id.get)
  }

  /**
   * Insert a list of to relations.
   *
   * @param from
   * 	The FROM entity
   * @param to
   * 	List of TO entities
   */
  def insertAll(from: E1, to: List[E2])(implicit s: Session) = {
    to.foreach { to => insert(from, to) }
  }

  /**
   * Insert a list of from relations.
   *
   * @param from
   * 	List of FROM entities
   * @param to
   * 	FROM entity
   */
  def insertAll(from: List[E1], to: E2)(implicit s: Session) = {
    from.foreach { from => insert(from, to) }
  }

  /**
   * Stores all relations between FROM and TO. Old relations will be deleted.
   *
   * @param from
   * 	The id of the FROM entity.
   * @param to
   * 	The list ids of the TO entity.
   */
  def updateAll(from: ID[E1], to: Iterable[ID[E2]])(implicit s: Session) = {
    this.entities.where(_.fromColumn === from).delete
    to.map(to => insert(from, to))
  }

  /**
   * Deletes all entries related to the FROM entity
   *
   * @param from
   * 	The FROM id
   */
  def deleteByFrom(from: ID[E1])(implicit s: Session) = {
    (for (
      e <- entities.where(_.fromColumn === from)
    ) yield (e)).delete
  }

  /**
   * Delete all entries related to the TO entity
   * 
   * @param to
   * 	The TO id
   */
  def deleteByTo(to: ID[E2])(implicit s: Session) = {
    (for (
      e <- entities.where(_.toColumn === to)
    ) yield (e)).delete
  }

}
