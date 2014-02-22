package com.wellnr.commons.persistence

import scala.collection.JavaConverters._
import org.eclipse.persistence.config.{ PersistenceUnitProperties => DBProperties, TargetServer }
import javax.persistence.Persistence
import com.wellnr.commons.logging.LoggingCapabilities

/**
 * Copyright 2013. Michael Wellner.
 *
 * This object connects to the database file using eclipselink and provides
 * the EntityManagerFactory for the persistence utilities.
 *
 * @author Michael Wellner
 * @since 2013/10/12
 */
private[persistence] object PersistenceManager extends LoggingCapabilities {

  /*
   * Map containing JPA persistence properties.
   */
  val iProperties = Map(
    DBProperties.JDBC_DRIVER -> cJDBCDriver,
    DBProperties.JDBC_URL -> cJDBCUrl,
    DBProperties.JDBC_USER -> cJDBCUser,
    DBProperties.JDBC_PASSWORD -> cJDBCPassword,
    DBProperties.TARGET_SERVER -> TargetServer.None,
    DBProperties.DDL_GENERATION -> DBProperties.CREATE_OR_EXTEND,
    DBProperties.DDL_GENERATION_MODE -> DBProperties.DDL_DATABASE_GENERATION)

  /*
   * Creates a EntityManagerFactory for the default persistence unit context. 
   */
  val iEntityManagerFactory = Persistence.createEntityManagerFactory(cPersistenceUnit, iProperties.asJava);

  val iEntityManager = new ThreadLocal[javax.persistence.EntityManager]

  LOG.info("Initialized EntityManagerFactory for persistence unit %s on database %s.", cPersistenceUnit, cJDBCUrl)

}