package io.egreen.apistudio.bootstrap.swagger

import io.egreen.apistudio.bootstrap.ApiStudioContext
import io.egreen.apistudio.bootstrap.module.{ApiStudioModule, Module}

/**
  * Created by dewmal on 11/26/16.
  */
@Module(name = "swagger-ui", enableStaticFolder = true)
class SwaggerUIModule extends ApiStudioModule {

  override def init(studioContext: ApiStudioContext): Unit = {
  }
}
