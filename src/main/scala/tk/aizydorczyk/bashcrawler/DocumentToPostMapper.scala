package tk.aizydorczyk.bashcrawler

import org.jsoup.nodes.{Document, Element}
import org.jsoup.select.Elements
import tk.aizydorczyk.bashcrawler.ElementHelper._

import scala.collection.JavaConverters._

private[bashcrawler] object DocumentToPostMapper {
  def apply(document: Document): Seq[Post] = fetchObjects(document)

  private def fetchObjects(document: Document): Seq[Post] = {
    val posts: Elements = document.getElementsByClass("post")
    posts.asScala.map(toPostObject)
  }

  private def toPostObject(post: Element): Post = {
    val bar = post.fetchBar()
    val quote = post.fetchQuote()

    val id = bar.fetchId()
    val points = bar.fetchPoints()
    val content = quote.fetchContent()

    Post(id, points, content)
  }
}
