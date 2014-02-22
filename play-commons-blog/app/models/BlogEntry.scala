package models

import java.util.Date
import play.api.Play.current
import play.api.db.slick.Config.driver.simple._
import slick.lifted.{ Join, MappedTypeMapper }
import scala.slick.lifted.ForeignKeyAction
import views.html.common.content
import com.wellnr.commons.model.{ AbstractEntity, ID }
import com.wellnr.commons.play.database.AbstractTable
import com.wellnr.commons.play.database.AbstractEntityDAOCapabilities
import com.wellnr.commons.play.database.JoinCapabilities
import com.wellnr.commons.play.database.AbstractManyToManyTable
import com.wellnr.commons.play.database.AbstractManyToManyDAO
import com.wellnr.commons.Development
import com.wellnr.commons.play.database.DAOCapabilities
import com.wellnr.commons.logging.LoggingCapabilities
import org.apache.commons.lang3.builder.ToStringBuilder

/**
 * This class represents a blog entry database entity.
 */
class BlogEntryDE(
  /** technical id **/
  val id: Option[ID[BlogEntryDE]], 

  /** The title as usual string **/
  val title: String, 

  /** The content as Markdown string **/
  val content: String, 

  /** The date of creation of the entry **/
  val created: Date, 

  /** The user: TODO - Turn into object **/
  val authorid: ID[User]) extends AbstractEntity[BlogEntryDE] {

  def withId(id: ID[BlogEntryDE]) = new BlogEntryDE(Some(id), title, content, created, authorid)
  
  override def toString() = new ToStringBuilder(this)
  	.append("id", id)
  	.append("title", title)
  	.append("content", content.split("\n")(0) + "...")
  	.append("created", created)
  	.append("authorid", authorid)
  	.toString
  
}

trait BlogEntry extends BlogEntryDE {
  self: BlogEntryDE =>
    
  def entry = self
  
  def author: User
  
  def topics: List[Topic]
  
  override def toString() = new ToStringBuilder(this)
  	.append("id", id)
  	.append("title", title)
  	.append("content", content.split("\n")(0) + "...")
  	.append("created", created)
  	.append("authorid", authorid)
  	.append("author", author)
  	.append("topics", topics)
  	.toString
  
}

object BlogEntry {
  
  def apply(id: Option[ID[BlogEntryDE]], title: String, content: String, created: Date, author: ID[User]) = {
    new BlogEntryDE(id, title, content, created, author)
  }
  
  def create(id: Option[ID[BlogEntryDE]], title: String, content: String, created: Date, pAuthor: ID[User], pTopics: List[ID[Topic]])(implicit s: Session) = {
    join(new BlogEntryDE(id, title, content, created, pAuthor), Users.findById(pAuthor).get, pTopics.map(Topics.findById(_).get))
  }
  
  def join(e: BlogEntryDE, authorf: => User, topicsf: => List[Topic]) = {
    new BlogEntryDE(e.id, e.title, e.content, e.created, e.authorid) with BlogEntry {
      
      def author = authorf
      
      def topics = topicsf
      
    }
  }
  
  def unapply(e: BlogEntryDE) = {
    Some((e.id, e.title, e.content, e.created, e.authorid))
  }
  
  def unapply(e: BlogEntry) = {
    Some((e.entry.id, e.entry.title, e.entry.content, e.entry.created, e.entry.authorid, e.author, e.topics))
  }
  
  def unapplyIds(e: BlogEntry) = {
    Some((e.entry.id, e.entry.title, e.entry.content, e.entry.created, e.entry.authorid, e.topics.map(_.id.get)))
  }
  
}

/**
 * Table class which represents the database table for BlogEnties.
 */
private[models] class BlogEntries extends AbstractTable[BlogEntryDE]("BLOG_ENTRY") {
  
  def title = column[String]("title", O.NotNull)
  def content = column[String]("content", O.NotNull)
  def created = column[Date]("date", O.NotNull)
  def author = column[ID[User]]("author", O.NotNull)

  def * = id.? ~ title ~ content ~ created ~ author <> (BlogEntry.apply _, { e: BlogEntryDE => BlogEntry.unapply(e) })
  
}

/**
 * The DAO for BlogEntries
 */
private[models] object BlogEntriesDAO extends AbstractEntityDAOCapabilities[BlogEntryDE, BlogEntries] with JoinCapabilities[BlogEntryDE, (BlogEntryDE, User)] {
  
  def blogentries(implicit s: Session) = {
    (for {
      e <- this.entities
      u <- Users.entities if u.id === e.author
    } yield (e, u)).list
  }
  
  def join(id: ID[BlogEntryDE])(implicit s: Session) = {
    this.findById(id) match {
      case Some(entry) => (entry, Users.findById(entry.authorid).get)
      case None => Development.TODO
    }
  }
  
}

object BlogEntries extends DAOCapabilities[ID[BlogEntryDE], BlogEntry] with LoggingCapabilities {
  
  def entityname = BlogEntriesDAO.entityname
  
  def findUser(id: ID[User], s: Session)() = {
    Users.findById(id)(s).get
  }
  
  def findTopics(id: TId, s: Session)() = {
    BlogEntryTopics.findByFromId(id)(s) match {
      case Some((_, list)) => list
      case None => List()
    }
  }
  
  def availableTopics(implicit s: Session) = {
    Topics.list.map(t => (t.ID, t.title))
  }
  
  def join(e: BlogEntryDE)(implicit s: Session) =  BlogEntry.join(e, findUser(e.authorid, s), findTopics(e.id.get, s))
  
  def findById(id: TId)(implicit s: Session): Option[BlogEntry] = {
    BlogEntriesDAO.findById(id).map(join)
  }
  
  def findByTopic(id: ID[Topic])(implicit s: Session) = {
    BlogEntryTopics.findByToId(id).map(_._2).getOrElse(List()).map(join)
  }
  
  def count(implicit s: Session) = BlogEntriesDAO.count
  
  def insert(entity: TEntity)(implicit s: Session) = {
    LOG.enter(entity)
    val newentry = BlogEntriesDAO.insert(entity.entry)
    BlogEntryTopics.insertAll(newentry, entity.topics)
    join(newentry)
  }
 
  def list(implicit s: Session): Seq[TEntity] = {
    BlogEntriesDAO.list.map(join)
  }
  
  def update(entity: TEntity)(implicit s: Session) = {
    LOG.enter(entity)
    val newentry = BlogEntriesDAO.update(entity.entry)
    BlogEntryTopics.updateAll(newentry.id.get, entity.topics.map(_.id.get))
    join(newentry)
  }
  
  def delete(id: TId)(implicit s: Session) = {
    findById(id) match {
      case Some(entity) =>
        BlogEntriesDAO.delete(id)
        BlogEntryTopics.deleteByFrom(id)
      case None => Development.DoNothing
    }
  }
  
  def save(entity: TEntity)(implicit s: Session) = {
    entity.id match {
      case Some(_) => update(entity)
      case _ => insert(entity)
    }
  }
  
}

/**
 * Many-To-Many Relation from entries to topics.
 */
private[models] class BlogEntryTopicsTable extends AbstractManyToManyTable(BlogEntriesDAO, Topics)

/**
 * DAO for blog entry topics.
 */
object BlogEntryTopics extends AbstractManyToManyDAO(BlogEntriesDAO, Topics, classOf[BlogEntryTopicsTable])

private[models] class BlogEntryCommentsTable extends AbstractManyToManyTable(BlogEntriesDAO, Comments)
object BlogEntryComments extends AbstractManyToManyDAO(BlogEntriesDAO, Comments, classOf[BlogEntryCommentsTable])


