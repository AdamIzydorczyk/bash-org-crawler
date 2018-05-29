package tk.aizydorczyk.bashcrawler

import java.nio.file.{Files, Path, Paths}

import org.scalatest._

class BashTest extends FlatSpec {

  private val html =
    """<html>
      <body>
      <div id="d1" class="q post">
      <div class="bar">
      <div class="right">
      12 grudzień 2012
      </div>
      <a class="qid click" href="http://bash.org.pl/1/">#1</a>
      <span class=" points" style="visibility: hidden;">1</span>
      </div>
      <div class="quote post-content post-body">
      test
      </div>
      </div>
      <div id="d2" class="q post">
      <div class="bar">
      <div class="right">
      13 grudzień 2012
      </div>
      <a class="qid click" href="http://bash.org.pl/2/">#2</a>
      <span class=" points" style="visibility: hidden;">123</span>
      </div>
      <div class="quote post-content post-body">
      test1
      </div>
      </div>
      </body>
      </html>"""

  private val correctResult = Seq(Post(1, 1, "test"), Post(2, 123, "test1"))
  private val path: Path = Paths.get("./test.json")

  "BashSourceDownloader" should "return 2 posts" in {
    new BashSourceDownloader(seq => {
      assert(seq.size === 2)
    }, s => html).downloadSites(1)
  }

  it should "return posts with correct data" in {
    new BashSourceDownloader(seq => {
      assert(seq === correctResult)
    }, s => html).downloadSites(1)
  }

  "BashFileCreator" should "create file" in {
    new BashFileCreator(path).createFile(correctResult)
    assert(Files.exists(path))
    Files.delete(path)
  }
}
