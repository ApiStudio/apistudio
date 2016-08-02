#API-STUDIO ( A Micro Service Framework )

1. Start within 2 sec
    * working to get in to 1 sec
2. All in one library
    * Cassandra For Database
    * Weld For CDI
    * Grizzly for tcp/http
    * Jersey for REST services


##**Sample Code** 

HelloWorldApp.java
```
@ApplicationPath("/")
 public class HelloWorldApp { 
     public static void main(String[] args) {
         ApiStudio.boot(HelloWorldApp.class, "0.0.0.0", 4545, "/api");
     }
 
 }
```

HelloApiSample.java
```
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
```

HelloService.java

```
public interface HelloService {
    void sayHello(String name);
}

```

.impl.HelloServiceImpl.java

```
@RequestScoped
public class HelloServiceImpl implements HelloService {
    public void sayHello(String name) {
        System.out.println(name + "Hello world");
    }
}

```

USE HTML5 View in API-STUIO

```
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
```