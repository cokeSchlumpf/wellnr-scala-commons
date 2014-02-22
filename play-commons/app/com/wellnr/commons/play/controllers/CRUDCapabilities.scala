package com.wellnr.commons.play.controllers

import com.wellnr.commons.Development
import com.wellnr.commons.play.database.DAOCapabilities
import com.wellnr.commons.play.database.JoinCapabilities
import jp.t2v.lab.play2.stackc.RequestWithAttributes
import com.wellnr.commons.play.database.AbstractTable
import com.wellnr.commons.model.AbstractEntity
import com.wellnr.commons.model.ID
import com.wellnr.commons.play.controllers.stackable.TransactionCapabilities

/**
 * Copyright 2014. Michael Wellner.
 *
 * tbd
 *
 * @author info@michaelwellner.de
 * @since Feb 4, 2014
 */
trait CRUDCapabilities extends EntityCRUDCapabilities {
  self: TransactionCapabilities =>

  /**
   * Fetches the entity via the DAO for simple entities.
   */
  implicit def crud2entity[I, E](helper: CRUDEntitySimple[I, E])(implicit id: Long, request: RequestWithAttributes[_]) = helper.load

  /**
   * Fetches the entity via the DAO for joined entities.
   */
//  implicit def crud2join[E <: AbstractEntity[E], A](helper: CRUDEntityJoined[E, A])(implicit id: Long, request: RequestWithAttributes[_]): A = helper.join(id)
  
  /**
   * Helper class for simple entities.
   */
  implicit class CRUDEntitySimple[I, E] private[CRUDCapabilities] (val dao: DAOCapabilities[I, E])(implicit f: Long => I) {

    def load[A](implicit id: Long, request: RequestWithAttributes[A]) = {
      dao.findById(f(id)) match {
        case Some(entity) => entity
        case None => Development.TODO
      }
    }

  }
  
//  /**
//   * Helper class for joined entities.
//   */
//  implicit class CRUDEntityJoined[E <: AbstractEntity[E], A] private[CRUDCapabilities] (override val dao: DAOCapabilities[ID[E], E] with JoinCapabilities[E, A]) extends CRUDEntitySimple(dao) {
//    
//    def join[A](id: Long)(implicit request: RequestWithAttributes[A]) = {
//      dao.join(ID(id))
//    }
//    
//  }
  
  /**
   * Creates a Helper wrapper class for joined entities
   */
//  def CRUD[I, E, A](dao: DAOCapabilities[I, E] with JoinCapabilities[E, A]) = new CRUDEntityJoined(dao)
  
  /**
   * Creates a Heloer wrapper class for simple entities.
   */
  def CRUD[I, E](dao: DAOCapabilities[I, E])(implicit f: Long => I) = new CRUDEntitySimple(dao) 

}

trait EntityCRUDCapabilities {
  
  implicit def long2ID[T <: AbstractEntity[T]](l: Long) = ID[T](l)
  
}