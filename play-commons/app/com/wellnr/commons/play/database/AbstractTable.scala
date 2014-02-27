package com.wellnr.commons.play.database

import scala.slick.lifted.ForeignKeyAction
import com.wellnr.commons.Development.NotImplemented
import play.api.db.slick.Config.driver.simple._
import com.wellnr.commons.model.{ AbstractEntity, ID }
import com.wellnr.commons.logging.LoggingCapabilities
import scala.slick.lifted.{ Projection2, Projection3, ColumnBase }
import java.util.Date
import scala.slick.lifted.Projection
import play.api.db.slick.DB
import play.api.Play.current

/**
 * Copyright 2013. Michael Wellner.
 *
 * This class defines an abstract table for your persistence unit.
 *
 * @author info@michaelwellner.de
 * @since 2013/10/17
 */
abstract class AbstractTable[T <: AbstractEntity[T]](name: String) extends Table[T](name) {

  /** The name of the table in the database */
  val tablename = name

  /** Implicit transformation java.util.Date <-> java.sql.Date */
  implicit val javaUtilDateTypeMapper = MappedTypeMapper.base[java.util.Date, java.sql.Date](
    x => new java.sql.Date(x.getTime()),
    x => new java.util.Date(x.getTime()))
    
  implicit def idMapper[T <: AbstractEntity[T]] = MappedTypeMapper.base[ID[T], Long](_.id, ID[T](_))
    
  /**
   * This method should be used to add a new entity to the database
   *
   * @return
   *         The default projection.
   */
  def autoinc = forInsert returning id

  /**
   * Creates a projection for inserting entities. This is needed since some databases will not work with
   * <code>* returning id</code> for inserting.
   *
   * @return
   *         The column projection without the id column.
   */
  def forInsert: ColumnBase[T]

  /** The default technical id column */
  def id = column[ID[T]]("id", O.PrimaryKey, O.AutoInc)

  /** A finde which selects by id */
  val byId = createFinderBy(_.id)

}

/**
 * Represents a many to many relationship.
 */
case class ManyToMany[F <: AbstractEntity[F], T <: AbstractEntity[T]](
    
    /** technical id */
    id: Option[ID[ManyToMany[F, T]]], 
    
    /** The id of FROM */
    from: ID[F], 
    
    /** The id of TO */
    to: ID[T]) extends AbstractEntity[ManyToMany[F,T]] {

  def withId(id: ID[ManyToMany[F, T]]) = this.copy(Some(id))

}

/**
 * This table can be used to create a many to many relationship between to entities.
 */
class AbstractManyToManyTable[E1 <: AbstractEntity[E1], T1 <: AbstractTable[E1], E2 <: AbstractEntity[E2], T2 <: AbstractTable[E2]](

  /**
   * The DAO of the `from` entity
   */
  val from: AbstractEntityDAOCapabilities[E1, T1],

  /**
 * The DAO of the `to` entity
 */
  val to: AbstractEntityDAOCapabilities[E2, T2])

  extends AbstractTable[ManyToMany[E1, E2]](from.entities.tablename + "_" + to.entities.tablename) {

  def fromColumn = column[ID[E1]]("from", O.NotNull)
  def toColumn = column[ID[E2]]("to", O.NotNull)

  def fromFk = foreignKey(this.tablename.toLowerCase() + "_fromFk", fromColumn, from.entities)(_.id, onDelete = ForeignKeyAction.Cascade)
  def toFk = foreignKey(this.tablename.toLowerCase() + "_toFk", toColumn, to.entities)(_.id, onDelete = ForeignKeyAction.Cascade)

  def * = id.? ~ fromColumn ~ toColumn <> (ManyToMany.apply[E1, E2] _, ManyToMany.unapply[E1, E2] _)

  def byFrom = createFinderBy(_.fromColumn)
  def byTo = createFinderBy(_.toColumn)

  /**
   * @inheritdoc
   */
  override def forInsert = fromColumn ~ toColumn <> ({
    (from, to) =>
      ManyToMany(None, from, to)
  }, {
    (mtm: ManyToMany[E1, E2]) =>
      Some((mtm.from, mtm.to))
  })
}

