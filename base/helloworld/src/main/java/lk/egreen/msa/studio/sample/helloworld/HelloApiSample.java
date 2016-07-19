package lk.egreen.msa.studio.sample.helloworld;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * Created by dewmal on 7/17/16.
 */
@Path("/hello")
@Api(value = "pet", authorizations = {
        @Authorization(value = "sampleoauth", scopes = {})
})
public class HelloApiSample {

    @Inject
    private HelloService helloService;


    @GET
    @ApiOperation(
            value = "Finds Pets by status",
            notes = "Multiple status values can be provided with comma seperated strings",
            response = String.class,
            responseContainer = "List"
    )
    public String hello() {
        helloService.sayHello("dewmal");
        return "Hello Done";
    }

}
