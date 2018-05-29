package tk.aizydorczyk.bashcrawler

import java.util.concurrent.{ExecutorService, Executors}

import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import tk.aizydorczyk.app.DefaultProperties
import tk.aizydorczyk.auditor.Auditor
import tk.aizydorczyk.bashcrawler.ElementHelper._

import scala.collection.JavaConverters._
import scala.concurrent.{ExecutionContext, ExecutionContextExecutor, Future}
import scala.util.{Failure, Success, Try}

private[bashcrawler] class BashSourceDownloader(postsConsumer: Seq[Post] => Unit, htmlSource: String => String) {
  private val executorService: ExecutorService = Executors.newFixedThreadPool(DefaultProperties.poolSize)
  implicit private val executor: ExecutionContextExecutor = ExecutionContext.fromExecutor(executorService)

  def downloadSites(numberOfSites: Int): Unit = {
    val range = 1 to numberOfSites
    val loadedPages = range.map(loadPage)
    Future.foldLeft(loadedPages)(Seq[Post]())((seq1, seq2) => seq1 ++ seq2)
      .onComplete(resultHandle)
  }

  private def resultHandle(tried: Try[Seq[Post]]): Unit = try {
    tried match {
      case Success(posts) =>
        postsConsumer.apply(posts)
      case Failure(e) =>
        throw e
    }
  }
  finally {
    executorService.shutdownNow()
  }

  private def loadPage(pageNumber: Int): Future[Seq[Post]] = {
    val bashUrl = DefaultProperties.bashUrl
    val formattedBashUrl = s"$bashUrl$pageNumber"
    Future {
      Auditor.fetchObjectsAuditable(() => fetchObjects(htmlSource.apply(formattedBashUrl)))
    }
  }

  private def fetchObjects(html: String): Seq[Post] = {
    val document = Jsoup.parse(html)
    val posts: Elements = document.getElementsByClass("post")
    posts.asScala.map(Auditor.toPostObjectAuditable(() => toPostObject(_)))
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
