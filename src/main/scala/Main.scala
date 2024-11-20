import API.GetRoutes
import zio._
import zio.http._

object Main extends ZIOAppDefault {
  def run =
    Server.serve(GetRoutes.app)
      .provide(
        Server.default,
        GedcomService.live
      )
}
