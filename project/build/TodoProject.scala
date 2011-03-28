import sbt._

class TodoProject(info: ProjectInfo) extends DefaultWebProject(info) {

  val cxVersion = "2.0.1"
  val jetty7 = "org.eclipse.jetty" % "jetty-webapp" % "7.0.2.v20100331" % "test"

  override def libraryDependencies = Set(
    "ru.circumflex" % "circumflex-web" % cxVersion % "compile->default",
    "ru.circumflex" % "circumflex-orm" % cxVersion % "compile->default",
    "ru.circumflex" % "circumflex-ftl" % cxVersion % "compile->default",
    "com.h2database" % "h2" % "1.3.153",
    "ch.qos.logback" % "logback-core" % "0.9.27",
    "ch.qos.logback" % "logback-classic" % "0.9.27"
  ) ++ super.libraryDependencies
  
  override def unmanagedClasspath = super.unmanagedClasspath +++ outputPath

  lazy val schema = runTask(Some("ru.fprog.todo.CreateSchema"), runClasspath)
    .dependsOn(compile, copyResources) describedAs "Create database schema"
}
