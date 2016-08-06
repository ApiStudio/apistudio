package lk.egreen.msa.sample.helloworld2;

import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.annotations.Table;
import io.swagger.annotations.Api;
import io.swagger.annotations.Authorization;
import io.egreen.apistudio.bootstrap.database.CassandraDataSource;
import org.glassfish.jersey.server.mvc.Viewable;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * Created by dewmal on 8/2/16.
 */
@Path("/app")
@Api(value = "pet", authorizations = {
        @Authorization(value = "sampleoauth", scopes = {})
})
public class HelloWorld {
    @Inject
    private CassandraDataSource cassandraDataSource;

    @Path("/hello")
    @GET
    public String hello() {


        Mapper<UserApp> mapper = cassandraDataSource.getManager().mapper(UserApp.class);
        mapper.save(new UserApp(345, "Dewmal"));

        return "hello";
    }

    @Path("/hello-ui")
    @GET
    public Viewable getHello() {
        return new Viewable("/hello");
    }

    @Table(keyspace = "api", name = "user")
    public class UserApp {


        private int user_id;
        private String username;

        public UserApp() {
        }

        public UserApp(int user_id, String username) {
            this.user_id = user_id;
            this.username = username;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }
}
