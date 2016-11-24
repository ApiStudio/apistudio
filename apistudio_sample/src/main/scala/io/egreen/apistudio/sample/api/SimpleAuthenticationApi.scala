package io.egreen.apistudio.sample.api

import javax.ws.rs.{GET, Path}
import javax.ws.rs.core.Response

import io.swagger.annotations.{Api, ApiOperation}

/**
  * Created by dewmal on 11/24/16.
  */
@Path("/api")
@Api("/api")
class SimpleAuthenticationApi {

  @Path("/version")
  @ApiOperation("/version")
  @GET
  def authGetVersion(): Response = {
    return Response.ok().build()
  }

}
