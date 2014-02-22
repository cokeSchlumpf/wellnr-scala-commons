package models

import com.wellnr.commons.model._
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
  title: String) extends AbstractEntity[Topic] {

  def withId(id: ID[Topic]) = this.copy(Some(id))

}

/**
 * Table definition for Topics
 */
private[models] class Topics extends AbstractTable[Topic]("TOPIC") {

  def title = column[String]("title", O.NotNull)

  def * = id.? ~ title <> (Topic.apply _, Topic.unapply _)

}

/**
 * The DAO for Topics
 */
object Topics extends AbstractEntityDAOCapabilities[Topic, Topics]


