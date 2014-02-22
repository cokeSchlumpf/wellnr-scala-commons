package controllers

import com.wellnr.commons.play.controllers.stackable.{ AuthenticationCapabilities, TransactionCapabilities }
import play.api.mvc.Controller
import models.Users
import models.User
import com.wellnr.commons.play.controllers.stackable.HasAuthentication
import com.wellnr.commons.play.controllers.stackable.SecurityCapabilities

/**
 * Copyright 2014. Michael Wellner
 *
 * Blog specific capabilities.
 *
 * @author info@michaelwellner.de
 * @since Feb 14, 2014
 */
trait BlogAuthenticationCapabilities extends Controller 

	with TransactionCapabilities 
	with AuthenticationCapabilities 
	with HasBlogUser {

  implicit val defaultloginroute = controllers.routes.Login.login
  val usersDao = Users

}

trait HasBlogUser extends HasAuthentication {
  
  type USER = User
  
}

trait BlogSecurityCapabilities extends Controller with SecurityCapabilities with BlogAuthenticationCapabilities