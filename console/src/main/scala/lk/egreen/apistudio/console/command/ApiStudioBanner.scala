package lk.egreen.apistudio.console.command

import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.shell.plugin.support.DefaultBannerProvider
import org.springframework.shell.support.util.OsUtils
import org.springframework.stereotype.Component

/**
  * Created by dewmal on 7/14/16.
  */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
class ApiStudioBanner extends DefaultBannerProvider {

  override def getBanner(): String = {
    val buf = new StringBuffer()
    buf.append("=======================================" + OsUtils.LINE_SEPARATOR)
    buf.append("*                                     *" + OsUtils.LINE_SEPARATOR)
    buf.append("*            HelloWorld               *" + OsUtils.LINE_SEPARATOR)
    buf.append("*                                     *" + OsUtils.LINE_SEPARATOR)
    buf.append("=======================================" + OsUtils.LINE_SEPARATOR)
    buf.append("Version:" + this.getVersion)
    buf.toString
  }

  override def getVersion(): String = "1.2.3"

  override def getWelcomeMessage(): String = "Welcome to HelloWorld CLI"

  override def getProviderName(): String = "Hello World Banner"
}