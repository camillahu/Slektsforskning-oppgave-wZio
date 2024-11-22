import gedcom.Person

  object Utils {

    def getInput(prompt: String): String = {
      println(s"$prompt :")
      val input = scala.io.StdIn.readLine()
      if (input.toLowerCase == "quit") s"Exiting program." else s"$input"
    }

    def findPersonById(persons: List[Person], id: String): Option[Person] = {
      persons.find(_.id == id)
    }

    def findPersonByName(persons: List[Person], inputName: String): List[Person] = {
      persons.filter { person =>
        person.name.exists(_.toLowerCase.startsWith(inputName.toLowerCase()))
      }
    }

    def displayPerson(person: Person): String = {
      List(s"ID: ${person.id}",
        s"Name: ${person.name.getOrElse("Unknown")}",
        s"Birth Date: ${person.birthDate.getOrElse("Unknown")}",
        s"Birth Place: ${person.birthPlace.getOrElse("Unknown")}",
        s"Death Date: ${person.deathDate.getOrElse("Unknown")}",
        s"Death Place: ${person.deathPlace.getOrElse("Unknown")}").mkString("\n")

    }
  }

