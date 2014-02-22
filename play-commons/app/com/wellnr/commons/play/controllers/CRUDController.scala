package com.wellnr.commons.play.controllers

import scala.language.implicitConversions
import com.wellnr.commons.Text._
import com.wellnr.commons.model.{ AbstractEntity, ID }
import com.wellnr.commons.play.controllers.stackable.TransactionCapabilities
import com.wellnr.commons.play.database.{ AbstractTable, DAOCapabilities }
import com.wellnr.commons.play.viewmodels.AbstractTableModel
import jp.t2v.lab.play2.stackc.RequestWithAttributes
import play.api.mvc.Controller
import play.api.templates.Html
import com.wellnr.commons.play.viewmodels.EditViewModel
import play.api.db.slick.Config.driver.simple._
import com.wellnr.commons.play.database.DAOCapabilities
import com.wellnr.commons.play.controllers.stackable.AuthenticationCapabilities

/**
 * Copyright 2014. Michael Wellner.
 *
 * A simple, extendible controller for CRUD application modules.
 *
 * @author info@michaelwellner.de
 * @since Feb 5, 2014
 */
object CRUDController {

  /** The type of the edit view. */
  type EDITVIEW = (EditViewModel[_], play.api.mvc.Call) => (play.api.data.Form[_], RequestWithAttributes[_]) => play.api.templates.HtmlFormat.Appendable

  /** The list view should be a view which accepts the tableviewmodel as parameter */
  type LISTVIEW = (AbstractTableModel[_], RequestWithAttributes[_]) => play.api.templates.HtmlFormat.Appendable

  /** The parent view should accept a title, additional script tags and the body content */
  type PARENTVIEW = (String, Html) => RequestWithAttributes[_] => Html => play.api.templates.HtmlFormat.Appendable

}

abstract class CRUDController[I, E](

  /** The dao which is used to crud the data */
  val dao: DAOCapabilities[I, E],

  /** the view to edit an entity */
  val editviewmodel: RequestWithAttributes[_] => EditViewModel[E],

  /** the view model which defines the display data (table columns, etc.) */
  val viewmodel: (Seq[E], RequestWithAttributes[_]) => AbstractTableModel[E],

  /** The router module for this controller */
  val router: CRUDRouter = CRUDRouter)(

    /** parentview with 3 arguments: page title, additional scripts and body content */
    implicit val parentview: CRUDController.PARENTVIEW,

    val long2id: Long => I)

  extends Controller

  with TransactionCapabilities
  
  with AuthenticationCapabilities

  with CRUDCapabilities {

  def this(dao: DAOCapabilities[I, E], editviewmodel: EditViewModel[E], viewmodel: Seq[E] => AbstractTableModel[E])(implicit parentview: CRUDController.PARENTVIEW, long2id: Long => I) = {
    this(dao, { s: RequestWithAttributes[_] => editviewmodel }, (l: Seq[E], s: RequestWithAttributes[_]) => viewmodel(l))
  }

  def this(dao: DAOCapabilities[I, E], editviewmodel: EditViewModel[E], viewmodel: Seq[E] => AbstractTableModel[E], router: CRUDRouter)(implicit parentview: CRUDController.PARENTVIEW, long2id: Long => I) = {
    this(dao, { s: RequestWithAttributes[_] => editviewmodel }, (l: Seq[E], s: RequestWithAttributes[_]) => viewmodel(l), router)
  }

  /** The type of the entities this controller is managing. */
  type ENTITY = E

  /** The wrapped CURD Helper */
  private val Entities = CRUD(dao)

  /** Returns the name of the entity which is used to create the URL of the controller */
  lazy val name = dao.entityname.camelify.pluralize

  /** Returns the name display name of the entity **/
  lazy val displayname = dao.entityname.replace("_", " ").capitalizeFirstLetters

  /** Returns the plural display name */
  lazy val pluraldisplayname = displayname.pluralize.capitalizeFirstLetters

  /** the view to list all entities */
  val listview: CRUDController.LISTVIEW = views.html.crud.list(parentview, (pluraldisplayname, displayname), routes.add)

  /** the companion which contains an edit an edit view model factory and the validation form */
  val editview: CRUDController.EDITVIEW = views.html.crud.edit(parentview, displayname)

  /* register this CRUD controller at creation time */
  router.register(this)

  /**
   * Creates a view which lists all entities.
   */
  def list = StackAction { implicit r =>
    Ok(listview(viewmodel(dao.list, r), r))
  }

  /**
   * Creates a view to edit the entity.
   */
  def edit(implicit id: Long) = StackAction { implicit r =>
    val model = editviewmodel(r)
    Ok(editview(model, routes.update)(model.form.fill(crud2entity(Entities)), r))
  }

  /**
   * [POST] A function which evaluates a post method of a form an stores it to the database.
   */
  def update = StackAction { implicit r => handleFormAction(routes.update) }

  /**
   * Deletes an entity and redirects to the list view.
   */
  def delete(implicit id: Long) = StackAction { implicit r =>
    dao.delete(id)
    // TODO show flash
    Redirect(routes.list)
  }

  /**
   * Adds a new entity.
   */
  def add = StackAction { implicit r =>
    val model = editviewmodel(r)
    Ok(editview(model, routes.insert)(model.form, r))
  }

  /**
   * [POST] Insert a new
   */
  def insert = StackAction { implicit r => handleFormAction(routes.insert) }

  /**
   * Handles the submit action of a form.
   */
  private def handleFormAction(submitaction: play.api.mvc.Call)(implicit request: RequestWithAttributes[_]) = {
    val model = editviewmodel(request)
    model.form.bindFromRequest.fold(
      error => {
        val a = error.data
        Ok(editview(model, submitaction)(error, request))
      },
      success => {
        dao.save(success)
        // TODO show flash that it was successfully saved.
        Redirect(routes.list)
      });
  }

  object routes extends router.URLs(name)

  object Actions {
    import com.wellnr.commons.play.viewmodels.HyperlinkAction
    
    /** Returns the delete Action for the CRUD type */
    def delete = HyperlinkAction("Delete", routes.delete _)

    /** Returns the delete Action for the CRUD type */
    def edit = HyperlinkAction("Edit", routes.edit _)

  }

}