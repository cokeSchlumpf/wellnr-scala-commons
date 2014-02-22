package com.wellnr.commons.play.controllers

import play.core.Router
import play.api.mvc.Handler
import play.api.mvc.RequestHeader
import com.wellnr.commons.Development
import scala.runtime.AbstractPartialFunction
import com.wellnr.commons.logging.LoggingCapabilities
import com.wellnr.commons.model.AbstractEntity
import com.wellnr.commons.play.database.AbstractTable
import play.api.mvc.PathBindable
import play.api.mvc.Call

/**
 * Copyright 2014. IBM Germany.
 *
 * The CRUD Router module to create
 *
 * @author info@michaelwellner.de
 * @since Feb 6, 2014
 */
trait CRUDRouter extends Router.Routes {

  private var _prefix = "/"

  private val GET = "GET"
  private val POST = "POST"

  private val list = "/([a-zA-Z]+)/?".r
  private val edit = "/([a-zA-Z]+)/(\\d+)/edit/?".r
  private val delete = "/([a-zA-Z]+)/(\\d+)/delete/?".r
  private val update = "/([a-zA-Z]+)/update/?".r
  private val add = "/([a-zA-Z]+)/add/?".r
  
  case class URLs(controllername: String) {
    def prefix = if (!CRUDRouter.this.prefix.endsWith("/")) CRUDRouter.this.prefix + "/" else CRUDRouter.this.prefix
    
    private def editURL(id: Long) = prefix + controllername + "/" + implicitly[PathBindable[Long]].unbind("id", id) + "/edit"
    private val addURL = prefix + Call(GET, controllername + "/add") 
    private val updateURL = prefix + Call(POST, controllername + "/update")
    
    def list = Call(GET, prefix + controllername)
    def edit(id: Long) = Call(GET, editURL(id))
    def delete(id: Long) = Call(GET, prefix + controllername + "/" + implicitly[PathBindable[Long]].unbind("id", id) + "/delete")
    def add = Call(GET, addURL)
    def update = Call(POST, updateURL)
    def insert = Call(POST, addURL)
  }

  def routes: PartialFunction[RequestHeader, Handler] = {
    case Header(GET, list(controllername)) if Registry.controllers.contains(controllername) =>
      Registry.controllers.get(controllername).get.list
    case Header(GET, edit(controllername, id)) if Registry.controllers.contains(controllername) =>
      Registry.controllers.get(controllername).get.edit(id.toLong)
    case Header(POST, update(controllername)) if Registry.controllers.contains(controllername) =>
      Registry.controllers.get(controllername).get.update
    case Header(GET, add(controllername)) if Registry.controllers.contains(controllername) =>
      Registry.controllers.get(controllername).get.add
    case Header(POST, add(controllername)) if Registry.controllers.contains(controllername) =>
      Registry.controllers.get(controllername).get.insert
    case Header(GET, delete(controllername, id)) if Registry.controllers.contains(controllername) =>
      Registry.controllers.get(controllername).get.delete(id.toLong)
    case Header(method, path) =>
      val e = new IllegalAccessException(s"There is now CRUD controller registered for $path with method $method. Registered controllers are ${Registry.controllers.mkString(", ")}")
      e.printStackTrace
      throw e
  }

  object Header {

    def unapply(rh: RequestHeader): Option[(String, String)] = if (rh.path.startsWith(prefix)) {
      Some((rh.method, rh.path.drop(prefix.length)))
    } else {
      None
    }

  }

  object Registry {

    var controllers: Map[String, CRUDController[_, _]] = Map()

    def register[I, E](controller: CRUDController[I, E]) = {
      val name = controller.name.toLowerCase

      if (controllers.contains(name)) {
        throw new IllegalArgumentException("A controller with this name already exists. Please chose another one by override `val name`")
      } else {
        controllers = controllers + (name -> controller)
      }
    }

  }
  
  def register[I, E](controller: CRUDController[I, E]) = Registry.register(controller)

  def documentation: Seq[(String, String, String)] = Development.NotImplemented

  def setPrefix(prefix: String) = this._prefix = prefix

  def prefix: String = this._prefix

}

object CRUDRouter extends CRUDRouter