import API.GetRoutes
import gedcom.GedcomService
import zio._
import zio.http._

object Main extends ZIOAppDefault {
  override val run =
    Server.serve(GetRoutes.routes).provide(Server.default, GedcomService.live)
}

// ZIOAppDefauls er en baseklasse for ZIO-applikasjoner.
// Den definerer hvor programmet skal starte ved å override run.

//Server.serve(GetRoutes.routes) starter en HTTP-server med routes som er definert i GetRoutes.

//.provide gir dependencies til serveren som den trenger. Her er de:

// Server.default: default server konfigurasjoner- starter serveren på port 8080.
// Kan overrides med .defaultWithPort(portnummer)
// det finnes også andre properties som man kan endre på: https://zio.dev/zio-http/reference/server/

//GedcomService.live er et ZLayer som setter opp GedcomService.
//GedcomService er en egendefinert case class som prosesserer GEDCOM filer til en liste av Person-objekter.
