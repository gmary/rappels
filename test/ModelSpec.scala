package test

import org.specs2.mutable._


import play.api.test._
import play.api.test.Helpers._
import org.joda.time.{Period, Duration, DateTime}

class ModelSpec extends Specification {

  // in order to have the test case in a sequential order
  sequential

  import models._
  import com.mongodb.casbah.Imports._

  def mongoTestDatabase() = {
    Map("mongodb.default.db" -> "rappels-database-test")
  }

  val johnId = new ObjectId("4f7e12bf7f25471356f51e39")

  step {
    running(FakeApplication(additionalConfiguration = mongoTestDatabase())) {
      User.remove(MongoDBObject.empty)
      User.insert(User(johnId, "John Doe", "johnPwd","john.doe@email.com"))
    }
  }

  "User model" should {
    "be retrieved by id" in {
      running(FakeApplication(additionalConfiguration = mongoTestDatabase())) {
        val Some(john) = User.findOneById(johnId)
        john.name must equalTo("John Doe")
        john.password must equalTo("johnPwd")
        john.email must equalTo("john.doe@email.com")
      }
    }

    "be retrieved by name" in {
      running(FakeApplication(additionalConfiguration = mongoTestDatabase())) {
        val Some(john) = User.findOneByUsername("John Doe")
        john.name must equalTo("John Doe")
        john.password must equalTo("johnPwd")
        john.email must equalTo("john.doe@email.com")
      }
    }

    "be retrieved by email" in {
      running(FakeApplication(additionalConfiguration = mongoTestDatabase())) {
        val Some(john) = User.findOneByEmail("john.doe@email.com")
        john.name must equalTo("John Doe")
        john.password must equalTo("johnPwd")
        john.email must equalTo("john.doe@email.com")
      }
    }

    "be updated if needed" in {
      running(FakeApplication(additionalConfiguration = mongoTestDatabase())) {
        val Some(john) = User.findOneById(johnId)
        val updatedJohn = john.copy(name="John",password = "johnPwdUpd",email= "john@doe.com",updated=Some(new DateTime()))
        User.save(updatedJohn)
        val Some(newJohn) = User.findOneById(johnId)
        newJohn.name must equalTo("John")
        newJohn.password must equalTo("johnPwdUpd")
        newJohn.email must equalTo("john@doe.com")
        newJohn.added must equalTo(john.added)
        newJohn.updated must not beNull
      }
    }
  }

  val rappel1Id = new ObjectId("5f7e12bf7f25471356f51e39")
  val rappel2Id = new ObjectId("5f7e12bf7f25471356f51e40")
  val rappel3Id = new ObjectId("5f7e12bf7f25471356f51e41")
  val rappel4Id = new ObjectId("5f7e12bf7f25471356f51e42")

  step {
    running(FakeApplication(additionalConfiguration = mongoTestDatabase())) {
      Rappel.remove(MongoDBObject.empty)
      Rappel.insert(Rappel(rappel1Id,"Test rappel 1","+33 6 98 99 45 32","Don 't forget your shoes",johnId,"P0Y0M0W3DT0H0M0S",new DateTime("2012-10-11")))
      Rappel.insert(Rappel(rappel2Id,"Test rappel 2","+33 6 98 99 45 33","Don 't forget your head",johnId,"P0Y0M0W4DT0H0M0S"))
      Rappel.insert(Rappel(rappel3Id,"Test rappel 3","+33 6 98 99 45 34","Don 't forget your money",johnId,"P0Y0M0W5DT0H0M0S",endDate = new DateTime("2012-10-11")))
      Rappel.insert(Rappel(rappel4Id,"Test rappel 4","+33 6 98 99 45 32","Don 't forget your shoes again !!!",johnId,"P0Y0M0W3DT0H0M0S",new DateTime("2014-10-11")))
    }
  }

  "Rappel model" should {
    "be retrieved by id" in {
      running(FakeApplication(additionalConfiguration = mongoTestDatabase())) {
        val Some(rappel) = Rappel.findOneById(rappel1Id)
        rappel.name must equalTo("Test rappel 1")
        rappel.phoneNumber must equalTo("+33 6 98 99 45 32")
        rappel.message must equalTo("Don 't forget your shoes")
        rappel.userId must equalTo(johnId)
        rappel.period must equalTo("P0Y0M0W3DT0H0M0S")
        rappel.startDate must equalTo(new DateTime("2012-10-11"))
      }
    }

    "be retrieved by active rappels for one user" in {
      running(FakeApplication(additionalConfiguration = mongoTestDatabase())) {
        val rappels = Rappel.listActiveRappelForUser(johnId,new DateTime("2012-11-14"))
        rappels.size must equalTo(2)
        rappels.flatMap(r => r.id.toString) must equalTo(Seq(rappel1Id,rappel2Id).flatMap(x => x.toString))
      }
    }
  }
}
