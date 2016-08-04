package lk.egreen.msa.studio.sample.helloworld;

import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.annotations.Table;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lk.egreen.apistudio.bootstrap.externalsource.database.CassandraDataSource;
import lk.egreen.msa.studio.sample.helloworld.data.dao.OrderDAOController;
import lk.egreen.msa.studio.sample.helloworld.data.entity.Order;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.jersey.server.mvc.Viewable;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Random;
import java.util.UUID;

/**
 * Created by dewmal on 7/17/16.
 */
@Path("/hello")
@Api(value = "pet", authorizations = {
        @Authorization(value = "sampleoauth", scopes = {})
})
public class HelloApiSample {

    private static final Logger LOGGER = LogManager.getLogger(HelloApiSample.class);


    @Inject
    private HelloService helloService;


    @Inject
    private OrderDAOController orderDAOController;

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

        Order order = new Order();
        order.setAmount(2345);
        order.setCode(UUID.randomUUID());
//        order.setOrder_id(new Random().nextLong());
        order.setCustomer("User Name");

        orderDAOController.create(order);


        helloService.sayHello("dewmal");
        return new Viewable("/hello", new SampleModel("Good morning", "my friends"));
    }

    @Table(
            keyspace = "apiStudio",
            name = "sample"
    )
    public static class SampleModel {

        private int sample_id = new Random().nextInt();
        private String greeting;
        private String name;

        public SampleModel() {
        }

        public SampleModel(String greeting, String name) {
            this.greeting = greeting;
            this.name = name;
        }

        public int getSample_id() {
            return sample_id;
        }

        public void setSample_id(int sample_id) {
            this.sample_id = sample_id;
        }

        public String getGreeting() {
            return greeting;
        }

        public void setGreeting(String greeting) {
            this.greeting = greeting;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
