package tk.aizydorczyk.app

import java.lang.ArrayIndexOutOfBoundsException

import tk.aizydorczyk.auditor.Auditor
import tk.aizydorczyk.bashcrawler.BashCrawlerFacade

object Main extends App {
  val crawlerFacade = new BashCrawlerFacade

  try {
    crawlerFacade.crawlToFile(args(0).toInt)
  } catch {
    case _: ArrayIndexOutOfBoundsException  => throw new IllegalArgumentException("Lack of site number argument")
  }

  Runtime.getRuntime.addShutdownHook(new Thread(() => {
    Auditor.printStatistics()
  }))
}
