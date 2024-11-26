import API.GetRoutes
import gedcom.GedcomService
import zio._  //ZIO er grunnlaget for funksjoell programmering og asynkron styring i applikasjonen
import zio.http._  //hjelper med å bygge HTTP-servere og håndtere HTTP-forespørsler og -svar

object Main extends ZIOAppDefault {
  override val run =
    Server.serve(GetRoutes.routes).provide(Server.default, GedcomService.live)
}

//Main er et singleton objekt- en klasse som gar nøyaktig en instans. Standaren i scala for å definere inngangspunkt

// ZIOAppDefauls er en baseklasse for ZIO-applikasjoner som definerer hvordan programmet starter.
// ved å override run. ZIOAppDefault forenkler oppsettet av et ZIO-program.

//Server.serve(GetRoutes.routes) starter en HTTP-server ved å bruke de definerte rutene GetRoutes.routes
// (/persons, /persons/id/{id}, og /persons/name/{name}).
//Server.serve kommer fra zio.http. FUnksjonen tar inn et sett med api-endepunkter.

//.provide gir dependencies til serveren som den trenger.
// Dette er viktig for funksjonell programmering og i ZIOs design.
// I FP unngår vi global tilstand og eksplisitt injiserer avhengigheter, som gjør programmet
// mer testbart og forutsigbart.

// Her er avhengighetene:
// Server.default: default server konfigurasjoner- starter serveren på port 8080.
// Kan overrides med .defaultWithPort(portnummer)
// det finnes også andre properties som man kan endre på: https://zio.dev/zio-http/reference/server/

//GedcomService.live er et ZLayer som setter opp GedcomService.
//GedcomService er en egendefinert case class som prosesserer GEDCOM filer til en liste av Person-objekter.



//FP poenger:
//1. Immutable- run og avhengighetene er immutable og beskrevet som verdier.
//    Vi beskriver hva som skal skje, ikke hvordan det skjer.
//2. Komposisjon- ZIO-programmet kombinerer Server.serve og provide, noe som gjør at vi kan bygge komplekse systemer
//    fra mindre byggesteiner.
//3. Dependency injection- Avhengighetene blir eksplissitt injisert via provide- unngår bruk av globale variabler.
