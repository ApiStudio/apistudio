package lk.egreen.apistudio.console.command

import org.springframework.shell.plugin.support.DefaultHistoryFileNameProvider

/**
  * Created by dewmal on 7/14/16.
  */
class ApiStudioHistory  extends DefaultHistoryFileNameProvider {

  override def getHistoryFileName(): String = "api-studio.log"

  override def getProviderName(): String = "Api Studio history file"
}
