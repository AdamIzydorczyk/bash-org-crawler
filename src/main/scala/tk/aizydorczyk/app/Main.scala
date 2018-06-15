package tk.aizydorczyk.app

import tk.aizydorczyk.bashcrawler.BashCrawler

object Main extends App {
  val bashCrawler = new BashCrawler

  try {
    bashCrawler.crawlToFile(args(0).toInt)
  } catch {
    case _: ArrayIndexOutOfBoundsException => throw new IllegalArgumentException("Lack of site number argument")
  }
}
