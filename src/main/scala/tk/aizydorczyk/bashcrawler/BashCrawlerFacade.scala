package tk.aizydorczyk.bashcrawler

import java.nio.file.Paths

import tk.aizydorczyk.app.DefaultProperties

class BashCrawlerFacade {
  private val filePath = DefaultProperties.filePath
  private val parser = new BashFileCreator(Paths.get(filePath))
  private val reader = new BashHtmlReader()
  private val bashClient = new BashSourceDownloader(parser.createFile, reader.read)

  def crawlToFile(numberOfSites: Int): Unit = {
    bashClient.downloadSites(numberOfSites)
  }
}
