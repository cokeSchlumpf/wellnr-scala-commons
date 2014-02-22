import java.util.Date
import com.wellnr.commons.play.CommonGlobal
import models._
import play.api.Application
import play.api.Play.current
import play.api.db.slick.DB
import play.api.db.slick.Session
import controllers.AdminControllers

object Global extends CommonGlobal {

  override def onStart(app: Application) {
    super.onStart(app)
    AdminControllers.init
    InitialData.insert()
  }

}

object InitialData {

  def insert() {
    if (current.configuration.getString("application.mode").getOrElse("") != "DEV") return
    
    DB.withSession { implicit s: Session =>
      val user = Users.findByUsername("admin") match {
        case Some(user) => user
        case None => Users.insert("admin", "admin@blog.de", "password")
      }

      if (BlogEntries.count == 0) {
        Seq(
          BlogEntry.create(None, "A new entry", """Duis autem vel eum iriure dolor in hendrerit in **vulputate** velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim qui blandit praesent luptatum zzril delenit augue duis dolore te feugait nulla facilisi. Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat. 

Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.
          
Ut wisi enim ad minim veniam, quis nostrud exerci tation ullamcorper suscipit lobortis nisl ut aliquip ex ea commodo consequat. Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim qui blandit praesent luptatum zzril delenit augue duis dolore te feugait nulla facilisi. """, new Date(), user.id.get, List()),

          BlogEntry.create(None, "A second entry", "Lorem ipsum dolor", new Date(), user.id.get, List()))
      }.foreach(BlogEntries.insert)
      
      if (Topics.count == 0) {
        Topics.insert(Topic(None, "Scala"))
        Topics.insert(Topic(None, "Social Business"))
      }
    }
  }

}