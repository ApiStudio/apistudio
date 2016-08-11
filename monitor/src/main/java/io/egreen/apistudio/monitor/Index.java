package io.egreen.apistudio.monitor;

import org.glassfish.jersey.server.mvc.Viewable;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * Created by dewmal on 8/11/16.
 */
@Path("/monitor")
public class Index {

    @GET
    public Viewable getIndex() {
        return new Viewable("/index");
    }
}
