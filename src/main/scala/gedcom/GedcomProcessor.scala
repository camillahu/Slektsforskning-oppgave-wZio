package gedcom

// For processing, finding and displaying GEDCOM data


object GedcomProcessor {
  def processLines(lines: List[String]): List[Person] = {
    val personBlocks = lines.foldLeft(List[List[String]]()) { (blocks, line) =>
      if (line.startsWith("0 @") && line.endsWith("INDI")) {
        List(line) :: blocks
      } else {
        blocks.headOption.map(block => (line :: block) :: blocks.tail).getOrElse(blocks)
      }
    }.reverse
    personBlocks.flatMap(block => GedcomParser.parsePerson(block.reverse))
  }
  //Grupperer hver person sine linjer til en blokk.
  //Gir hver blokk til GedcomParser for å lage person-objekter.
}