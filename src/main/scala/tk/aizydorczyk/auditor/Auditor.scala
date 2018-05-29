package tk.aizydorczyk.auditor

import scala.collection.mutable.ListBuffer

object Auditor {
  private val siteTimes: ListBuffer[Long] = ListBuffer()
  private val postsCount: ListBuffer[Int] = ListBuffer()
  private val postTimes: ListBuffer[Long] = ListBuffer()

  def fetchObjectsAuditable[T](posts: () => Seq[T]): Seq[T] = {
    val start = System.currentTimeMillis()
    val result = posts.apply()
    siteTimes += (System.currentTimeMillis() - start)
    postsCount += result.size
    result
  }

  def toPostObjectAuditable[T](toPostObject: () => T): T = {
    val start = System.currentTimeMillis()
    val result = toPostObject.apply()
    postTimes += (System.currentTimeMillis() - start)
    result
  }

  def printStatistics(): Unit ={
    val averageSiteFetchTime = siteTimes.sum / siteTimes.length
    val allPostsCount = postsCount.sum
    val averagePostFetchTime = postTimes.sum / siteTimes.length

    val statistic =
      s"""Average posts geting time: $averagePostFetchTime ms
      Posts count: $allPostsCount
      Average site geting time: $averageSiteFetchTime ms
      """
    println(statistic)
  }

}

