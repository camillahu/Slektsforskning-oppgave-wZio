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
//Routes trenger GedcomService for å fungere.
// Nothing spesifiserer at ingen feil forventes. Her kan feilhåndteringer spesifiseres, eller settes til Nothing og
// håndtere feil andre steder.F

//Routes inneholder en liste over HTTP-ruter som blir håndtert av handler.

//Forklaring av Method.GET / "persons":
//
//Method.GET / "persons" definerer at denne ruten svarer på GET-forespørsler til /persons endepunktet.
//handler brukes for å binde en funksjon til denne ruten.

//for-comprehension/for-uttrykk:
// persons <- GedcomService.getPersons henter listen over personer fra GedcomService.
// displayedPersons = ... mapper persons og formaterer hver person ved bruk av displayPerson og mkstring
// response <- ZIO.succeed putter den formaterte teksten inn i en HTTP-respons
//yield response returnerer responsen som svar på HTTP-forespørslen.

//Konsepter brukt:
//Declarative- vi beskriver hva en rute skal gjøre, ikke hvordan.
//Effectful Computations- ZIO-effektene sørger for at operasjoner som GedcomService.getPersons og responser blir
//    håndtert sikkert og asynkront.
//Reusability: Utils og GedcomService gjør koden modulær og testbar.


//(Using the ZIO.succeed method, you can create an effect that, when executed, will succeed with the specified value.
// The succeed method takes a so-called by-name parameter, which ensures that if you pass the method some code to execute,
// that this code will be stored inside the ZIO effect so that it can be managed by ZIO,
// and benefit from features like retries, timeouts, and automatic error logging.
// Using the ZIO.fail method, you can create an effect that, when executed, will fail with the specified value.)
//https://zio.dev/overview/creating-effects/



