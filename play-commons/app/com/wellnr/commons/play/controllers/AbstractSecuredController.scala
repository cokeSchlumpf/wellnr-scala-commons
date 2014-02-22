package com.wellnr.commons.play.controllers

import models.{AbstractUser, AbstractUsers, UserDAOCapabilities}
import play.api.Play.current
import play.api.mvc.{Action, AnyContent, Controller, Request, RequestHeader, Result, Results, Security}
import play.api.db.slick.{ DBAction, dbSessionRequestAsSession, DB, Session }
import play.api.mvc.EssentialAction

/**
 * Copyright 2013. Michael Wellner.
 *
 * This trait extends a class with Security capabilities.
 *
 * @author Michael Wellner
 * @since Dec 1, 2013
 */
@deprecated("Use stackable controller actions instead.", "2014-02-04")
abstract class AbstractSecuredController[E <: AbstractUser[E], T <: AbstractUsers[E]](users: UserDAOCapabilities[E, T]) extends Controller {

  /**
   * Returns the users name of the current session object.
   */
  def usernameDeprecated(request: RequestHeader) = request.session.get(Security.username)

  /**
   * Redirects to the default login form.
   */
  def onUnauthorized(request: RequestHeader)(implicit route: play.api.mvc.Call) = Results.Redirect(route)

  /**
   * Use this wrapper to secure an action.
   */
  def withAuth(f: => String => Request[AnyContent] => Result)(implicit alternativeRout: play.api.mvc.Call) = {
    Security.Authenticated(usernameDeprecated, onUnauthorized) { user =>
      Action(request => f(user)(request))
    }
  }

  /**
   * This method shows how you could wrap the withAuth method to also fetch your user
   * You will need to implement UserDAO.findOneByUsername
   */
  def withUser(f: E => Request[AnyContent] => Result)(implicit alternativeRout: play.api.mvc.Call) = withAuth { username =>
    implicit request =>
      findUser(username).map { user =>
        f(user)(request)
      }.getOrElse(onUnauthorized(request))
  }
  
  def withAuthSession(f: Session => Result)(implicit alternativeRoute: play.api.mvc.Call): EssentialAction = withUserSession {
    user => f
  }
  
  def withAuthRequestSession(f: Request[AnyContent] => Session => Result)(implicit alternativeRoute: play.api.mvc.Call): EssentialAction = withUserRequestSession {
    user => f 
  }
  
  def withUserSession(f: E => Session => Result)(implicit alternativeRoute: play.api.mvc.Call) = withUserRequestSession {
    user => request => session => f(user)(session)
  }
  
  def withUserRequest(f: E => Request[AnyContent] => Result)(implicit alternativeRoute: play.api.mvc.Call) = withUserRequestSession {
    user => request => session => f(user)(request)
  }
  
  def withUserRequestSession(f: E => Request[AnyContent] => Session => Result)(implicit alternativeRoute: play.api.mvc.Call)  = withUser { user =>
    implicit request =>
      DB.withSession { s: Session => f(user)(request)(s) }
  }
  
  /**
   * Looks for the user by comparing the searchstring with usernames and email addresses.
   * 
   * @param search
   */
  def findUser(search: String) = play.api.db.slick.DB.withSession { implicit session: Session =>
    users.findByUsername(search) match {
      case None => users.findByEmail(search)
      case e => e
    }
  }
  
}