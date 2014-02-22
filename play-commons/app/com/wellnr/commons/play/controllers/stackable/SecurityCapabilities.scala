package com.wellnr.commons.play.controllers.stackable

import play.api.mvc.{ Controller, RequestHeader, Results, Security, SimpleResult }
import scala.concurrent.{ ExecutionContext, Future }
import jp.t2v.lab.play2.stackc.{ RequestAttributeKey, RequestWithAttributes, StackableController }
import com.wellnr.commons.logging.LoggingCapabilities

trait SecurityCapabilities extends StackableController with LoggingCapabilities {
  self: Controller with AuthenticationCapabilities =>

  /** Defines the default route which should be used, if a user is required but not logged in */
  implicit val defaultloginroute: play.api.mvc.Call

  /**
   * Around invoke of the action execution.
   */
  override def proceed[A](req: RequestWithAttributes[A])(f: RequestWithAttributes[A] => Future[SimpleResult]): Future[SimpleResult] = {
    LOG.debug("Enter AuthElement around invoke")

    loadUser[Future[SimpleResult], A](implicit newreq =>
      userOption match {
        case Some(user) =>
          LOG.debug("Execute Request with user %s (%s).", user.username, newreq.get(AuthKey))
          super.proceed(newreq)(f)
        case None =>
          LOG.debug("Stop request execution since user is not authorized.")
          implicit val ctx: ExecutionContext = StackActionExecutionContext(newreq)
          Future(onUnauthorized)(ctx)
      })(req)
  }
}