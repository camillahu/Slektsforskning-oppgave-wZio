package API
import zio._
import zio.http.Server

object Main extends ZIOAppDefault  {
  override def run: ZIO[Environment with ZIOAppArgs with Scope, Any, Any] =
    Server.serve(GetRoutes.app)
      .provide(Server.default)
}
