package models

import com.wellnr.commons.model.ID
import com.wellnr.commons.model.AbstractEntity
import com.wellnr.commons.play.database.AbstractTable
import java.util.Date
import com.wellnr.commons.play.database.AbstractEntityDAOCapabilities

/**
 * Copyright 2014. Michael Wellner.
 *
 *
 *
 * @author info@michaelwellner.de
 * @since Feb 17, 2014
 */
case class Comment(

  /** technical id */
  id: Option[ID[Comment]],

  /** The name of the author of the comment */
  author: String,

  /** The email of the author of the comment */
  email: Option[String],
  
  /** The date of the comment */
  date: Date,
  
  /** The comment itself */
  comment: String) extends AbstractEntity[Comment] {
  
  def withId(id: ID[Comment]) = this.copy(Some(id))
  
}
		
private[models] class Comments extends AbstractTable[Comment]("COMMENT") {
  
  def author = column[String]("author", O.NotNull)
  def email = column[String]("email")
  def date = column[Date]("date")
  def comment = column[String]("comment")
  
  def * = id.? ~ author ~ email.? ~ date ~ comment <> (Comment.apply _, Comment.unapply _)
  
}		

object Comments extends AbstractEntityDAOCapabilities[Comment, Comments]

		