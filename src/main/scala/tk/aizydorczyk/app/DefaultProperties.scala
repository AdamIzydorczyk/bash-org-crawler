package tk.aizydorczyk.app

import com.typesafe.config.ConfigFactory

object DefaultProperties {
  private val defaultConfig = ConfigFactory.parseResources("default.conf")

  def filePath: String = defaultConfig.getString("paths.output")
  def poolSize: Int = defaultConfig.getInt("conf.pool")
  def bashUrl: String = defaultConfig.getString("url.bash")
}
