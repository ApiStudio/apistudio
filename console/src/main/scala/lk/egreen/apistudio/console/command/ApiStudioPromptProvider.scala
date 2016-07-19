package lk.egreen.apistudio.console.command

import org.springframework.core.annotation.Order
import org.springframework.core.Ordered
import org.springframework.shell.plugin.support.DefaultPromptProvider
import org.springframework.stereotype.Component

/**
  * Created by dewmal on 7/14/16.
  */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
class ApiStudioPromptProvider extends DefaultPromptProvider {

  override def getPrompt(): String = "api:studio>"

  override def getProviderName(): String = "Api Studio Shell"
}
