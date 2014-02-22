package models

import java.util.Date

import com.wellnr.commons.Text._

import play.api.db.slick.Config.driver.simple.Session
import com.wellnr.commons.model.ID

/**
 *  This class represents an User
 */
case class User(

  /** technical id **/
  id: Option[ID[User]],

  /** The nickname of the user **/
  username: String,

  /** The email address of the user **/
  email: String,

  /** The md5 encoded password of the user **/
  password: String,

  /** Last login **/
  lastlogin: Option[Date])

  extends AbstractUser[User] {

  def withId(id: ID[User]) = this.copy(Some(id))

}

class Users extends AbstractUsers[User]("USER") {

  def lastlogin = column[Date]("lastlogin", O.Nullable)

  def * = id.? ~ username ~ email ~ password ~ lastlogin.? <> (User.apply _, User.unapply _)

}

object Users extends UserDAOCapabilities[User, Users] {

  /**
   * Creates a new user in the database. The password will be stored encoded as MD5 hash.
   *
   * @param username
   * @param email
   * @param password
   */
  def insert(username: String, email: String, password: String)(implicit s: Session) = {
    val user = User(None, username, email, password.md5hash, None)
    super.insert(user)
  }

  override def insert(user: User)(implicit s: Session): User = {
    user.copy(password = user.password.md5hash)
    super.insert(user)
  }

  override def update(user: User)(implicit s: Session) = {
    Users.findById(user.id.get) match {
      case Some(old) =>
        val newuser =
          if (user.password == "") user.copy(password = old.password)
          else if (!old.password.equals(user.password)) user.copy(password = user.password.md5hash)
          else user
        super.update(newuser)
      case None =>
        super.update(user)
    }
  }
}



