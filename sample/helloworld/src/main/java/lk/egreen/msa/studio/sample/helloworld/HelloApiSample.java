package lk.egreen.msa.studio.sample.helloworld;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lk.egreen.msa.studio.sample.helloworld.model.User;
import org.glassfish.jersey.server.mvc.Viewable;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

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
            value = "Say Hello to Server",
            notes = "Hello Server with Test Server DI",
            response = String.class,
            responseContainer = "List"
    )
    @RolesAllowed("ADMIN")
    @Produces(MediaType.TEXT_HTML)
    public Viewable hello() {
        helloService.sayHello("dewmal");
        return new Viewable("../hello");
    }

}
