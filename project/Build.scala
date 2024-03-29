import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "rappels"
    val appVersion      = "1.0-SNAPSHOT"

    val appDependencies = Seq(
      // Add your project dependencies here,
      "se.radley" %% "play-plugins-salat" % "1.2-SNAPSHOT"
    )

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = SCALA).settings(
      // Add your own project settings here
      routesImport += "se.radley.plugin.salat.Binders._",
     templatesImport += "org.bson.types.ObjectId",
     resolvers += "OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/",
     resolvers += "OSS Releases" at "https://oss.sonatype.org/content/repositories/releases/"
    )

}
