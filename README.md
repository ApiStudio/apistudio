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

##License

```
 Copyright 2016 egreen

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 ```