import gedcom.{GedcomProcessor, GedcomReader, Person}
import zio._

case class GedcomService(persons:List[Person])

object GedcomService {

  def live: ZLayer[Any, Throwable, GedcomService] = ZLayer.fromZIO(
    ZIO.attempt {
      val filePath = "src/main/scala/FamilyTree/gedcom.ged"
      val filePathDemo = "src/main/scala/FamilyTree/demo.ged"
      val lines = GedcomReader.readFile(filePath)
      val persons = GedcomProcessor.processLines(lines)
      GedcomService(persons)
    }
  )
}
