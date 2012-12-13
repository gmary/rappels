import play.api._

object Global extends GlobalSettings {
    override def onStart(app: Application) {
      com.mongodb.casbah.commons.conversions.scala.RegisterJodaTimeConversionHelpers()
      Logger.info("Application has started with JodaTime registered")
    }
}
