package controllers

import play.api.mvc.Security._
import play.api.mvc._
import models.Rappel

/**
 * Rappels controller
 */
object Rappels extends Controller {
  def active = Authenticated { user:String =>
    Action {
      Rappel.listActiveRappelForUser(user).map {
        rappels => Ok(Rappel.toPrettyJSONArray(rappels)).as(JSON)
      }.getOrElse {
        NotFound("No active rappels found")
      }
    }
  }
}
