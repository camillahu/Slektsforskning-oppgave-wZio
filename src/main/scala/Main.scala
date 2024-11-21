import API.GetRoutes
import gedcom.GedcomService
import zio._
import zio.http._

object Main extends ZIOAppDefault {
  override val run =
    Server.serve(GetRoutes.routes)
      .provide(
        Server.default,
        GedcomService.live
      )
}
