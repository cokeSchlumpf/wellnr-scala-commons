package models

import com.wellnr.commons.model.AbstractEntity
import scala.slick.direct.AnnotationMapper.column
import com.wellnr.commons.play.database.AbstractTable
import com.wellnr.commons.play.database.AbstractEntityDAOCapabilities
import play.api.db.slick.Config.driver.simple.Session

/**
 * Copyright 2014. Michael Wellner
 *
 * Represents a base user, used by the security capabilities of the framework.
 *
 * @author info@michaelwellner.de
 * @since Jan 15, 2014
 */
trait AbstractUser[T <: AbstractUser[T]] extends AbstractEntity[T] {

  /** The email address of the user **/
  val username: String
  
  /** The email address of the user **/
  val email: String

  /** The md5 encoded password of the user **/
  val password: String

}

abstract class AbstractUsers[T <: AbstractUser[T]](name: String) extends AbstractTable[T](name) {

  def username = column[String]("username", O.NotNull)
  def email = column[String]("email", O.NotNull)
  def password = column[String]("password", O.NotNull)

  val byEmail = createFinderBy(_.email)
  val byUsername = createFinderBy(_.username)
  
}

trait UserDAOCapabilities[E <: AbstractUser[E], T <: AbstractUsers[E]] extends AbstractEntityDAOCapabilities[E, T] {
  
  /**
   * Find the user by email.
   *
   * @param email
   */
  def findByEmail(email: String)(implicit s: Session): Option[E] = entities.byEmail(email).firstOption
  
  /**
   * Find the user by name.
   *
   * @param username
   */
  def findByUsername(username: String)(implicit s: Session): Option[E] = entities.byUsername(username).firstOption
  
  /**
   * Find the user by email or username.
   * 
   * @param identifier
   * 	email or username
   */
  def find(identifier: String)(implicit s: Session): Option[E] = findByEmail(identifier).orElse(findByUsername(identifier))
  
}