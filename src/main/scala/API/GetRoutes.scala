package API
import zio._
import zio.http._

object GetRoutes {
  val app: Http[Any, Throwable, Request, Response] =
    Http.collectZIO[Request] {
      case Method.GET -> Root / "persons" =>
        for  {
          service <- ZIO.service[GedcomService]
          response <- ZIO.succeed(Response.json(service.persons.toJson))
        } yield response
    }
}
