package com.wellnr.commons.play.controllers.stackable

import scala.concurrent.{ ExecutionContext, Future }
import play.api.Play.current
import jp.t2v.lab.play2.stackc.{ RequestAttributeKey, RequestWithAttributes, StackableController }
import play.api.mvc.{ Controller, RequestHeader, Results, Security, SimpleResult }
import play.api.db.slick.{ DBAction, dbSessionRequestAsSession, DB, Session }
import com.wellnr.commons.logging.LoggingCapabilities

/**
 * Copyright 2014. Michael Wellner.
 *
 * Stackable database transaction capabilities.
 *
 * @author info@michaelwellner.de
 * @since Feb 4, 2014
 */

/** key to identify the the request key */
private[stackable] object DBKey extends RequestAttributeKey[Session]

trait TransactionCapabilities extends StackableController with LoggingCapabilities with HasSession {
  self: Controller =>

  /**
   * Around invoke of the action execution.
   */
  override def proceed[A](req: RequestWithAttributes[A])(f: RequestWithAttributes[A] => Future[SimpleResult]): Future[SimpleResult] = {
    LOG.debug("Enter DBTransactionElement around invoke")

    loadTransaction[Future[SimpleResult], A](req => super.proceed(req)(f))(req)
  }

  def loadTransaction[T, A](f: RequestWithAttributes[A] => T)(implicit req: RequestWithAttributes[A]) = {
    req.get(DBKey) match {
      case Some(session) => f(req.set(DBKey, session))
      case None => DB.withTransaction { session: Session => f(req.set(DBKey, session)) }
    }
  }

}

trait HasSession {

  /**
   * Returns the session of the current database transaction.
   */
  implicit def databaseSession(implicit req: RequestWithAttributes[_]): Session = req.get(DBKey).get

}

