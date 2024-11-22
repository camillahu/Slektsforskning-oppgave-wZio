package gedcom

import zio._

case class GedcomService(persons:List[Person])

object GedcomService {

  def live: ZLayer[Any, Throwable, GedcomService] = ZLayer.fromZIO(
    ZIO.attempt {
      val filePath = "src/main/scala/FamilyTree/gedcom.ged"
      val filePathDemo = "src/main/scala/FamilyTree/demo.ged"
      val lines = GedcomReader.readFile(filePath)
      //leser GEDCOM filen jeg velger å bruke
      val persons = GedcomProcessor.processLines(lines)
      //lager filen om til en liste av person-objekter
      GedcomService(persons)
      //lager en instanse av GedcomService med lista
    }
  )

  def getPersons: ZIO[GedcomService, Nothing, List[Person]] = {
    ZIO.serviceWith[GedcomService] (_.persons)
    //en hjelpemetode som henter alle personer fra dette objektet.
  }
}
