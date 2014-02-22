package viewmodels
import models._
import com.wellnr.commons.play.viewmodels.EditViewModel
import com.wellnr.commons.play.viewmodels.EditViewModel._
import com.wellnr.commons.DateTime.dateToString
import com.wellnr.commons.play.viewmodels.AbstractEntityTableModel
import play.api.data.Forms._
import java.util.Date
import controllers.AdminControllers.users.Actions.{delete, edit}

case class UsersViewModel(data: Seq[User]) extends AbstractEntityTableModel(data) {

  def name = column[String]("Username")
  def email = column[String]("EMail")
  def password = column[String]("Password", false)
  def lastlogin = column[Option[Date]]("Last Login")
  
  def * = ((id ~ name ~ email ~ password ~ lastlogin) <> (User.unapply _)) ~ delete ~ edit
  
}

object UserEditViewModel extends EditViewModel[User] {
  
	lazy val formviewmodel = FormViewModel(
	    Hidden("id", optional(id[User])),
	    Text("Username", "username", text),
	    EMail("E-Mail", "email", email),
	    Password("Password", "password", text),
	    Hidden("lastlogin", optional(date)))(User.apply _)(User.unapply _)
  
}


