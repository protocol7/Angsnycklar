import sbt._


class AngsnycklarProject(info: ProjectInfo) extends DefaultProject(info) with AkkaProject with IdeaProject {

  val akkaRemote = akkaModule("remote")

  val codec = "commons-codec" % "commons-codec" % "1.4"


  override def mainClass = Some("com.protocol7.angsnycklar.Angsnycklar")
}