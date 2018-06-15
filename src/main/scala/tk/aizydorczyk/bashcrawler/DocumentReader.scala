package tk.aizydorczyk.bashcrawler

import java.net.URL

import org.jsoup.Jsoup
import org.jsoup.nodes.Document

class DocumentReader {
  def read(url: String): Document = Jsoup.parse(new URL(url), 1000)
}
