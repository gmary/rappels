package models

import play.api.Play.current
import com.novus.salat.dao._
import com.mongodb.casbah.Imports._
import se.radley.plugin.salat._
import org.joda.time.{Period, DateTime}
import com.novus.salat.annotations._
import com.novus.salat.global._


case class Rappel(
                   @Key("_id") id: ObjectId = new ObjectId,
                   name: String,
                   phoneNumber:String,
                   message:String,
                   @Key("user_id") userId:ObjectId,
                   period:String,
                   startDate:DateTime = new DateTime(Long.MinValue),
                   endDate:DateTime =  new DateTime(Long.MaxValue),
                   added:DateTime = new DateTime(),
                   sent:Option[DateTime] = None,
                   updated:Option[DateTime] = None,
                   deleted:Option[DateTime] = None
                   )

object Rappel extends ModelCompanion[Rappel,ObjectId] {
  val collection = mongoCollection("rappels")

  val dao = new SalatDAO[Rappel, ObjectId](collection = collection) {}

  def listActiveRappelForUser(userId:ObjectId, searchDate:DateTime = new DateTime()):Seq[Rappel] = {
    val equalUserId = "user_id" -> userId
    val isNotDeleted = "deleted" -> None
    val isNotEnded = "endDate" $gt searchDate
    val isStarted = "startDate" $lte searchDate

    val where = isStarted ++ isNotEnded ++ equalUserId ++ isNotDeleted

    dao.find(where).toSeq
  }
}
