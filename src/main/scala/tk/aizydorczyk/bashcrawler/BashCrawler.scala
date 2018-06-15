package tk.aizydorczyk.bashcrawler

import java.nio.file.Paths

import tk.aizydorczyk.app.DefaultProperties

class BashCrawler {
  private val filePath = DefaultProperties.filePath
  private val creator = new FileCreator(Paths.get(filePath))
  private val reader = new DocumentReader()
  private val sitesLoader = new SitesLoader(creator.createFile, reader.read)

  def crawlToFile(numberOfSites: Int): Unit = {
    sitesLoader.loadSites(numberOfSites)
  }
}
