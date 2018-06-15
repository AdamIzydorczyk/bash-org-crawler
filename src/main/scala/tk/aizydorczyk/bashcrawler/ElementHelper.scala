package tk.aizydorczyk.bashcrawler

import org.jsoup.nodes.Element

private[bashcrawler] object ElementHelper {

  implicit class BashElement(element: Element) {
    val quoteIdTag = "qid"
    val points = "points"
    val bar = "bar"
    val quote = "quote"

    def fetchId(): Long = element.getElementsByClass(quoteIdTag).first().text().take(1).toLong

    def fetchPoints(): Long = element.getElementsByClass(points).first().text().toLong

    def fetchContent(): String = element.text()

    def fetchBar(): Element = element.getElementsByClass(bar).first()

    def fetchQuote(): Element = element.getElementsByClass(quote).first()
  }

}
