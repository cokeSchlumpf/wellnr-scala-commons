package com.wellnr.commons.persistence

import com.wellnr.commons.logging.LoggingCapabilities
import scala.collection.JavaConverters._

/**
 * Copyright 2013. Michael Wellner.
 *
 * This trait can be used to access persistence functionalities.
 *
 * @author Michael Wellner
 * @since 2013/10/12
 */
trait PersistenceCapabilities extends LoggingCapabilities {

  /**
   * Use this to create a transaction. The transaction will be committed if pCodeBlock
   * finishes successfully.
   */
  def transaction[T](pCodeBlock: => T): T = {
    LOG.enter

    val em = PersistenceManager.iEntityManagerFactory.createEntityManager
    em.getTransaction.begin
    PersistenceManager.iEntityManager.set(em)

    val result = pCodeBlock

    PersistenceManager.iEntityManager.remove
    em.getTransaction.commit
    em.close

    LOG.exit(result)
  }

  /**
   * Executes a SELECT * FROM `EntityTable`.
   */
  def fetchAll[T <: AbstractPersistenceEntity](pEntityClass: Class[T]): List[T] = {
    LOG.enter(pEntityClass)

    val em = getEntityManager
    val qb = em.getCriteriaBuilder
    val query = qb.createQuery
    val selectfrom = query.select(query.from(pEntityClass))
    val typedQuery = em.createQuery(query)
    val result = typedQuery.getResultList.asInstanceOf[java.util.List[T]].asScala.toList

    LOG.exit(result)
  }

  /**
   * Use this method to read an entity from the database. This method can only be called
   * within a transaction. The transaction is only available for he current thread.
   */
  def find[T <: AbstractPersistenceEntity](pEntityClass: Class[T], pPrimaryKey: Int): Option[T] = {
    LOG.enter(pEntityClass, pPrimaryKey)

    val result = getEntityManager.find(pEntityClass, Some(pPrimaryKey)) match {
      case null => None
      case result => Some(result)
    }

    LOG.exit(result)
  }

  /**
   * Gets the entity manager for the current transaction/ thread.
   */
  private def getEntityManager = {
    LOG.enter

    val result = PersistenceManager.iEntityManager.get
    if (result == null) {
      val illegalStateException = new IllegalStateException("No entity manager created. Please make sure that your call is within a transaction.")
      LOG.error(illegalStateException)
      throw illegalStateException
    }

    LOG.exit(result)
  }

  /**
   * Use this method to store or update an entity. This method can only be called within
   * an transaction.
   */
  def store[T <: AbstractPersistenceEntity](pEntity: T) {
    LOG.enter(pEntity)

    pEntity.id match {
      case Some(id) => getEntityManager.merge(pEntity)
      case None => getEntityManager.persist(pEntity)
    }

    LOG.exit
  }

}