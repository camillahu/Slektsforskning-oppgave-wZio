package API
import zio._
import zio.http._
import gedcom.GedcomService
import gedcom.GedcomProcessor


object GetRoutes {
  val routes: Routes[GedcomService, Nothing] = Routes(
    Method.GET / "persons" -> handler {
      for {
        persons <- GedcomService.getPersons
        displayedPersons = persons.map(GedcomProcessor.displayPerson).mkString("\n\n")
        response <- ZIO.succeed(Response.text(displayedPersons))
      } yield response
    },
    Method.GET / "persons" / "id" / string("id") -> handler { (id: String, _: Request) =>
      for {
        persons <- GedcomService.getPersons
        person = GedcomProcessor.findPersonById(persons, id)
        response <- ZIO.succeed(
          person.map(p => Response.text(GedcomProcessor.displayPerson(p)))
            .getOrElse(Response.status(Status.NotFound))
        )
      } yield response
    },

    Method.GET / "persons" /"name"/ string("name") -> handler { (name: String, _: Request) =>
    for {
      persons <- GedcomService.getPersons
      matchedPersons  = GedcomProcessor.findPersonByName(persons, name)
      response <- ZIO.succeed(
        if (matchedPersons.nonEmpty) {
          Response.text(matchedPersons.map(GedcomProcessor.displayPerson).mkString("\n\n"))
        } else {
          Response.status(Status.NotFound)
        }
      )
    } yield response
  }
  )
}
