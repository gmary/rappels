package controllers

import play.api.mvc._
import models.User


/**
 * User controller
 */
object Users extends Controller {

  def authenticate = Action(parse.json) { request =>
    val maybeEmail = (request.body \ "email").asOpt[String]
    val maybePassword = (request.body \ "password").asOpt[String]
    (maybeEmail,maybePassword) match {
      case (Some(email),Some(password)) =>  User.findOneByEmailAndPassword(email,password ).map { user =>
        Ok(User.toPrettyJson(user)).as(JSON).withSession(Security.username -> user.id.toString)
      }.getOrElse {
        BadRequest("Errors during authentication")
      }
      case (_,_) => BadRequest("Missing parameter")
    }

  }
}
