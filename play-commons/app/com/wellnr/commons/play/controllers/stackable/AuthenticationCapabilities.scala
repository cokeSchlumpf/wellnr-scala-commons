package com.wellnr.commons.play.controllers.stackable

import scala.concurrent.Future
import jp.t2v.lab.play2.stackc.{ RequestAttributeKey, RequestWithAttributes, StackableController }
import models._
import play.api.mvc.{ Controller, RequestHeader, Results, Security, SimpleResult }
import com.wellnr.commons.logging.LoggingCapabilities

/**
 * Copyright 2014. Michael Wellner.
 *
 * Stackable controller trait to add authentication methods.
 *
 * @author info@michaelwellner.de
 * @since Feb 4, 2014
 */


/** key to identify the the request key */
private[stackable] object AuthKey extends RequestAttributeKey[Option[_ <: AbstractUser[_]]]

trait AuthenticationCapabilities extends StackableController with LoggingCapabilities with HasAuthentication {
  self: Controller with TransactionCapabilities =>

  /** abstract value for the concrete DAO */
  val usersDao: UserDAOCapabilities[USER, _]

  /**
   * Returns the users name of the current session object.
   */
  private def username(request: RequestHeader) = request.session.get(Security.username)

  /**
   * Around invoke of the action execution.
   */
  override def proceed[A](req: RequestWithAttributes[A])(f: RequestWithAttributes[A] => Future[SimpleResult]): Future[SimpleResult] = {
    LOG.debug("Enter AuthElement around invoke")

    loadUser[Future[SimpleResult], A](req => super.proceed(req)(f))(req)
  }

  def loadUser[T, A](f: RequestWithAttributes[A] => T)(implicit req: RequestWithAttributes[A]) = {
    req.get(AuthKey) match {
      case Some(key) => f(req.set(AuthKey, key))
      case None =>
        username(req) match {
          case Some(username) =>
            loadTransaction[T, A]({ req =>
              usersDao.find(username)(AuthenticationCapabilities.this.databaseSession(req)) match {
                case Some(user) => f(req.set(AuthKey, Some(user)))
                case None => f(req.set(AuthKey, None))
              }
            })(req)
          case None => f(req.set(AuthKey, None))
        }
    }
  }

}

trait HasAuthentication extends LoggingCapabilities {

  /** The concrete type of users */
  type USER <: AbstractUser[USER]

  /**
   *  Returns the user associated with the current request.
   */
  implicit def userOption(implicit req: RequestWithAttributes[_]): Option[USER] = {
    try {
      LOG.debug("%s %s", req.get(AuthKey), req)
      req.get(AuthKey).get.asInstanceOf[Option[USER]]
    } catch {
      case e: Throwable =>
        // TODO
        val err = new Exception("Did you extend your controller with AuthenticationCapabilities?", e)
        LOG.error(err)
        throw err
    }

  }
  implicit def user(implicit req: RequestWithAttributes[_]): USER = userOption.get

}


