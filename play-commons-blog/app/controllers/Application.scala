package controllers

import scala.language.{implicitConversions, postfixOps}
import com.wellnr.commons.Development
import com.wellnr.commons.play.controllers.{CRUDController, AbstractLoginController, CRUDCapabilities, EntityCRUDCapabilities}
import com.wellnr.commons.play.controllers.stackable.TransactionCapabilities
import jp.t2v.lab.play2.stackc.RequestWithAttributes
import models.{BlogEntries, Topics, User, Users}
import play.api.mvc.{Action, Controller}
import play.api.templates.Html
import viewmodels._
import jp.t2v.lab.play2.stackc.StackableController

/**
 * Copyright 2014. Michael Wellner.
 *
 * The applications main controller.
 *
 * @author info@michaelwellner.de
 * @since Jan 30, 2014
 */
object FrontendController
  extends Controller
  with TwitterCapabilities
  with TransactionCapabilities
  with CRUDCapabilities
  with StackableController 
  with BlogAuthenticationCapabilities {

  val PostCRUD = CRUD(BlogEntries)

  /**
   * Creates the blogs homepage.
   */
  def index = StackAction { implicit r =>
    Ok(views.html.blog.index(BlogViewModel(BlogEntries.list, latestTweet, Topics.list))(r))
  }

  /**
   * Displays a single blog entry.
   *
   * @param id
   */
  def entry(implicit id: Long) = StackAction { implicit r =>
    Ok(views.html.blog.show(BlogEntryViewModel(PostCRUD.load), Topics.list))
  }
  
  def legal = StackAction { implicit r =>
  	Ok(views.html.legal(r))
  }

  def topic(topic: Long) = StackAction { implicit r =>
    Ok(views.html.blog.topic(ShowTopicViewModel(BlogEntries.findByTopic(topic).map(BlogEntryViewModel), Topics.findById(topic).get, Topics.list))(r))
  }
  
}

object AdminControllers extends EntityCRUDCapabilities {

  implicit val parenview: (String, Html) => RequestWithAttributes[_] => Html => play.api.templates.HtmlFormat.Appendable = views.html.admin.main.apply
  
  val topics = new CRUDController(Topics, TopicEditViewModel, TopicsViewModel) with BlogSecurityCapabilities
  val posts = new CRUDController(BlogEntries, BlogEntryEditViewModel, BlogEntryTableViewModel) with BlogSecurityCapabilities
  val users = new CRUDController(Users, UserEditViewModel, UsersViewModel) with BlogSecurityCapabilities

  def init = Development.DoNothing

}

object Application extends Controller with StackableController with BlogAuthenticationCapabilities {

  def javascriptroutes = Action { implicit request =>
    import routes.javascript._

    Ok(
      play.api.Routes.javascriptRouter("jsRoutes")( /* Add routes here */ )).as("text/javascript")
  }

}

object Login extends AbstractLoginController[User, Users](Users, routes.FrontendController.index, views.html.login.apply)

object Request extends HasBlogUser