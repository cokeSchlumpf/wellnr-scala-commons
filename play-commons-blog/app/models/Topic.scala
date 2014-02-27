package models

import com.wellnr.commons.model._
import scala.slick.lifted.{MappedProjection, Projection}
import com.wellnr.commons.play.database.AbstractTable
import com.wellnr.commons.play.database.AbstractEntityDAOCapabilities

/**
 * Copyright 2014. Michael Wellner.
 *
 * This class represents a blog topic.
 *
 * @author info@michaelwellner.de
 * @since Jan 31, 2014
 */
case class Topic(

  /** technical id */
  id: Option[ID[Topic]],

  /** title of the topic */
  title: String,

  /** a description of the topic */
  description: String = "") extends AbstractEntity[Topic] {

  def withId(id: ID[Topic]) = this.copy(Some(id))

}

/**
 * Table definition for Topics
 */
private[models] class Topics extends AbstractTable[Topic]("TOPIC") {

  def title = column[String]("title", O.NotNull)
  def description = column[String]("description", O.DBType("text"))

  def * = id.? ~ title ~ description <> (Topic.apply _, Topic.unapply _)

  /**
   * @inheritdoc
   */
  override def forInsert = title ~ description <> ({
    (title, description) => Topic(None, title, description)
  }, {
    topic: Topic => Some((topic.title, topic.description))
  })
}

/**
 * The DAO for Topics
 */
object Topics extends AbstractEntityDAOCapabilities[Topic, Topics]


