package viewmodels

import com.wellnr.commons.play.viewmodels.EditViewModel
import com.wellnr.commons.play.viewmodels.EditViewModel.{FormViewModel, Hidden, Text}
import controllers.AdminControllers.topics.Actions.{delete, edit}
import models.Topic
import play.api.data.Forms.{optional, text}
import scala.slick.direct.AnnotationMapper.column
import com.wellnr.commons.play.viewmodels.AbstractEntityTableModel

/**
 * Copyright 2014. Michael Wellner.
 *
 * View model for topics crud.
 *
 * @author info@michaelwellner.de
 * @since Feb 3, 2014
 */
case class TopicsViewModel(data: Seq[Topic]) extends AbstractEntityTableModel(data) {
  
  def name = column[String]("Name")
  def description = column[String]("Description", false)
  
  def * = ((id ~ name ~ description) <> (Topic.unapply _)) ~ delete ~ edit
  
}

object TopicEditViewModel extends EditViewModel[Topic] {
  
  import EditViewModel._
  
  lazy val formviewmodel = FormViewModel(
      Hidden("id", optional(id[Topic])), 
      Text("Name", "name", text),
      Textarea("Description", "description", text))(Topic.apply _)(Topic.unapply _)
  
}

case class ShowTopicViewModel(pEntries: List[BlogEntryViewModel], topic: Topic, topics: List[Topic]) extends DefaultBlogEntriesViewModel(pEntries)
