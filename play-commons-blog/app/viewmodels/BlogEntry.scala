package viewmodels

import java.util.Date

import org.markdown4j.Markdown4jProcessor

import com.wellnr.commons.DateTime.{date2dateutil, dateToString}
import com.wellnr.commons.Text.StringHelper
import com.wellnr.commons.model.ID
import com.wellnr.commons.play.controllers.stackable.HasSession
import com.wellnr.commons.play.viewmodels.{AbstractTableModel, EditViewModel}
import com.wellnr.commons.play.viewmodels.EditViewModel.{FormViewModel, Hidden, MultiSelect, Text, Textarea}

import controllers.AdminControllers.posts.Actions.{delete, edit}
import controllers.HasBlogUser
import jp.t2v.lab.play2.stackc.RequestWithAttributes
import models.{BlogEntries, BlogEntry, BlogEntryDE, Topic, User}
import play.api.data.Forms.{date, default, list, optional, text}

/**
 * Copyright 2014. Michael Wellner.
 *
 * This file adds seevral functions to the model which are used by the view templates.
 *
 * @author info@michaelwellner.de
 * @since Jan 30, 2014
 */
case class BlogEntryViewModel(entry: BlogEntry) {
  
  import com.wellnr.commons.DateTime._

  /** Returns the whole content, transformed to HTML. */
  lazy val htmlContent = toHTML(entry.entry.content.paragraphs.tail.mkString("\n\n"))

  /** Returns the first paragraph, transformed to HTML. */
  lazy val htmlPreview = toHTML(entry.entry.content.paragraphs(0))
  
  /** Returns the authors username */
  lazy val author = entry.author.username
  
  /** Returns the date of creation, formatted as string */
  lazy val created = entry.entry.created.asDaetString
  
  lazy val topics = entry.topics

  /**
   * Transforms a markdown string to HTML.
   *
   * @param s - The string to transform.
   */
  private def toHTML(s: String) = new Markdown4jProcessor().process(s)
  
}

/**
 * A trait which enables a class to hold a list of BlogEntry View Models ...
 */
trait BlogEntriesViewModel {
  
  def headentry: Option[BlogEntryViewModel]
  
  def entries: List[BlogEntryViewModel]
  
}

class DefaultBlogEntriesViewModel(pEntries: List[BlogEntryViewModel]) {
  
  
  def headentry = pEntries.headOption
  
  def entries = pEntries match {
    case head :: tail => tail
    case _ => List()
  }
  
}

/**
 * View model to display a table of blog entries.
 */
case class BlogEntryTableViewModel(data: Seq[BlogEntry], s: RequestWithAttributes[_]) extends AbstractTableModel[BlogEntry](data) {
  
  import com.wellnr.commons.DateTime.dateToString
  
  def eid = column[Option[ID[BlogEntryDE]]]("#", width = Some(30))
  def title = column[String]("Title")
  def content = column[String]("content", false)
  def created = column[Date]("Created")
  def authorid = column[ID[User]]("Author", false)
  def author = column[User]("Author")(_.username)
  def topics = column[List[Topic]]("Topics", false)(_.toString)
  
  def * = ((eid ~ title ~ content ~ created ~ authorid ~ author ~ topics) <> ({e: BlogEntry => BlogEntry.unapply(e) })) ~ delete ~ edit
  
}

case class BlogEntryEditViewModel(request: RequestWithAttributes[_]) extends EditViewModel[BlogEntry] 

		with HasSession 
		
		with HasBlogUser {
  
  import EditViewModel._
  
  implicit val r = request
  
  val t = id[User]
  
  lazy val formviewmodel = (FormViewModel(
      Hidden("id", optional(id[BlogEntryDE])),
      Text("Title", "title", text),
      Textarea("Content", "content", text),
      Hidden("date", default(date, new Date())),
      Hidden("user", default(id[User], user.id.get)),
      MultiSelect[ID[Topic]]("Topics", "topic", list(id[Topic]), BlogEntries.availableTopics))(BlogEntry.create _)({e: BlogEntry => BlogEntry.unapplyIds(e) }))   
  
}



