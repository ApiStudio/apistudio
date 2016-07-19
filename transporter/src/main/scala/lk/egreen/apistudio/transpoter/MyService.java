package lk.egreen.apistudio.transpoter;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * Created by dewmal on 7/17/16.
 */

@Path("/hello")
public class MyService {
    @Inject
    private HelloServiceInterface helloServiceInterface;

    @GET
    @Produces("application/json")
    public String getHello() {
        helloServiceInterface.hello("Hi Im original");
        return "Hello ";
    }

}
