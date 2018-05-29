package tk.aizydorczyk.bashcrawler

import java.nio.file.{Files, Path, StandardOpenOption}

import io.circe.generic.auto._
import io.circe.syntax._

private[bashcrawler] class BashFileCreator(path: Path) {

  if (!Files.exists(path)) {
    Files.createFile(path)
  }

  def createFile(seq: Seq[Post]): Unit = {
    Files.write(path, seq.asJson.toString().getBytes())
  }
}
