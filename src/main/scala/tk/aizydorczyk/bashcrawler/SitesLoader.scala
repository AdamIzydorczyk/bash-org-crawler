package tk.aizydorczyk.bashcrawler

import java.util.concurrent.{ExecutorService, Executors}

import org.jsoup.nodes.Document
import tk.aizydorczyk.app.DefaultProperties

import scala.concurrent.{ExecutionContext, ExecutionContextExecutor, Future}
import scala.util.{Failure, Success, Try}

private[bashcrawler] class SitesLoader(postsConsumer: Seq[Post] => Unit, documentSource: String => Document) {
  private val executorService: ExecutorService = Executors.newFixedThreadPool(DefaultProperties.poolSize)
  implicit private val executor: ExecutionContextExecutor = ExecutionContext.fromExecutor(executorService)


  def loadSites(numberOfSites: Int): Unit = {
    validate(numberOfSites)
    val range = 1 to numberOfSites
    val loadedPages = range.map(startSiteLoading)
    Future.foldLeft(loadedPages)(Seq[Post]())((seq1, seq2) => seq1 ++ seq2)
      .onComplete(resultHandle)
  }

  private def resultHandle(tried: Try[Seq[Post]]): Unit = try {
    tried match {
      case Success(posts) =>
        postsConsumer.apply(posts)
      case Failure(e) =>
        throw new SiteLoadingFail(e)
    }
  }
  finally {
    executorService.shutdownNow()
  }

  private def startSiteLoading(pageNumber: Int): Future[Seq[Post]] = {
    val bashUrl = DefaultProperties.bashUrl
    val formattedBashUrl = s"$bashUrl$pageNumber"
    Future {
      DocumentToPostMapper(documentSource.apply(formattedBashUrl))
    }
  }

  def validate(numberOfSites: Int): Unit = if (numberOfSites < 0) {
    throw new IllegalArgumentException(s"given number: $numberOfSites is negative")
  }

  class SiteLoadingFail(cause: Throwable) extends RuntimeException(cause)

}
