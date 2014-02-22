package com.wellnr.commons.play.controllers

import com.wellnr.commons.Text.StringHelper
import models.{ AbstractUser, AbstractUsers, UserDAOCapabilities }
import play.api.Play.current
import play.api.data.Form
import play.api.data.Forms.{ email, mapping, text }
import play.api.db.slick.Config.driver.simple.Session
import play.api.mvc.{ Action, Controller, Security }
import jp.t2v.lab.play2.stackc.RequestWithAttributes
import play.templates.Appendable
import jp.t2v.lab.play2.stackc.StackableController
import com.wellnr.commons.play.controllers.stackable.HasAuthentication
import com.wellnr.commons.play.controllers.stackable.AuthenticationCapabilities
import com.wellnr.commons.play.controllers.stackable.TransactionCapabilities

/**
 * Copyright 2013. Michael Wellner.
 *
 * Abstract controller for login controllers.
 *
 * @author Michael Wellner
 * @since Dec 1, 2013
 */

/**
 * Defines the data set of the login form.
 */
case class UserLoginForm(email: String, password: String)
abstract class AbstractLoginController[E <: AbstractUser[E], T <: AbstractUsers[E]](

  val usersDao: UserDAOCapabilities[E, T],

  /** Defines the route which should be used after login was successful. */
  redirectTo: play.api.mvc.Call,

  loginview: (Form[UserLoginForm], RequestWithAttributes[_]) => play.api.templates.HtmlFormat.Appendable) 
  
  extends Controller 
  
  with StackableController 
  
  with TransactionCapabilities
  
  with AuthenticationCapabilities {
  
  type USER = E

  /**
   * Defines the login form.
   */
  val userForm = Form(
    mapping(
      "email" -> email,
      "password" -> text)(UserLoginForm.apply)(UserLoginForm.unapply) verifying ("User authentication failed!", fields => fields match {
        case UserLoginForm(email, password) => authenticate(email, password)
      }))

  /**
   * The default action which is called to show the login view.
   */
  def login = StackAction { r =>
    Ok(loginview(userForm, r))
  }

  def authenticate(username: String, password: String) = play.api.db.slick.DB.withSession { implicit session: Session =>
    def authenticate(user: E) = password.md5hash.equals(user.password)

    usersDao.findByUsername(username) match {
      case Some(user) => authenticate(user)
      case None =>
        usersDao.findByEmail(username) match {
          case Some(user) => authenticate(user)
          case None => false
        }
    }
  }

  /**
   * The action which should be called when submitting the form.
   */
  def loginPost = StackAction { implicit request =>
    userForm.bindFromRequest.fold(
      errors => {
        BadRequest(loginview(errors, request))
      },
      label => {
        Redirect(redirectTo).withSession(Security.username -> label.email)
      })
  }
  
  def logout = StackAction { implicit request =>
  	Redirect(redirectTo).withSession(session - Security.username)
  }

}