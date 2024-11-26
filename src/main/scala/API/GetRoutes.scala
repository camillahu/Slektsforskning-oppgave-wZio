package API
import zio._
import zio.http._
import gedcom.GedcomService
import _root_.Utils

object GetRoutes {
  val routes: Routes[GedcomService, Nothing] = Routes(
    Method.GET / "persons" -> handler {
      for {
        persons <- GedcomService.getPersons
        // henter personer fra GedcomService
        displayedPersons = persons.map(Utils.displayPerson).mkString("\n\n")
        // setter hver person inn i formatet jeg har laget i GedcomProcessor.
        response <- ZIO.succeed(Response.text(displayedPersons))
      } yield response
    },

    Method.GET / "persons" / "id" / string("id") -> handler { (id: String, _: Request) =>
      for {
        persons <- GedcomService.getPersons
        person = Utils.findPersonById(persons, id)
        response <- ZIO.succeed(
          person.map(p => Response.text(Utils.displayPerson(p)))
            .getOrElse(Response.status(Status.NotFound))
        )
      } yield response
    },

    Method.GET / "persons" /"name"/ string("name") -> handler { (name: String, _: Request) =>
    for {
      persons <- GedcomService.getPersons
      matchedPersons  = Utils.findPersonByName(persons, name)
      response <- ZIO.succeed(
        if (matchedPersons.nonEmpty) {
          Response.text(matchedPersons.map(Utils.displayPerson).mkString("\n\n"))
        } else {
          Response.status(Status.NotFound)
        }
      )
    } yield response
  }
  )
}


//GetRoutes definerer et singleton-objekt som inneholder API-endepunktene.
//Routes trenger GedcomService for å fungere (dependency).
// Nothing spesifiserer at ingen feil forventes. Her kan feilhåndteringer spesifiseres.

//Routes inneholder en liste over HTTP-ruter som blir håndtert av handler.

//Forklaring av Method.GET / "persons":
//
//Method.GET / "persons" definerer at denne ruten svarer på GET-forespørsler til /persons endepunktet.
//handler brukes for å binde en funksjon til denne ruten.

//for-comprehension/for-uttrykk:
// persons <- GedcomService.getPersons henter listen over personer fra GedcomService.
//
