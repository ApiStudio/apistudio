package lk.egreen.msa.studio.sample.helloworld;


import io.egreen.apistudio.bootstrap.ApiStudio;

import javax.ws.rs.ApplicationPath;

/**
 * Created by dewmal on 7/17/16.
 */
@ApplicationPath("/")
public class HelloWorldApp {

    public static void main(String[] args) {
        ApiStudio.boot(HelloWorldApp.class, "0.0.0.0", 4545, "/api");
    }

}
