package tk.aizydorczyk.bashcrawler

import scala.io.Source

class BashHtmlReader {
  def read(url: String): String = Source.fromURL(url).mkString
}
