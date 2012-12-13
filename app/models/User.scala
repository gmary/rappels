package models

import play.api.Play.current
import com.novus.salat.dao._
import com.mongodb.casbah.Imports._
import se.radley.plugin.salat._
import org.joda.time.DateTime
import com.novus.salat.annotations._
import com.novus.salat.global._

/**
 * User model class
 * @param id user id
 * @param name user name
 * @param password user password
 * @param email user email
 * @param added timestamp when the user is added
 * @param updated timestamp when the user is updated
 * @param deleted timestamp when the user is deleted
 */
case class User(
                 @Key("_id") id: ObjectId = new ObjectId,
                 name: String,
                 password: String,
                 email: String,
                 added: DateTime = new DateTime(),
                 updated: Option[DateTime] = None,
                 deleted: Option[DateTime] = None
                 )

/**
 * User companion
 */
object User extends ModelCompanion[User, ObjectId] {
  /**
   * User collection
   */
  val collection = mongoCollection("users")

  /**
   * DAO
   */
  val dao = new SalatDAO[User, ObjectId](collection = collection) {}

  /**
   * Find user by name
   * @param username the user name
   * @return user found
   */
  def findOneByUsername(username: String): Option[User] = dao.findOne(MongoDBObject("name" -> username))

  /**
   * Find user by email
   * @param email the user email
   * @return user found
   */
  def findOneByEmail(email: String): Option[User] = dao.findOne(MongoDBObject("email" -> email))
}