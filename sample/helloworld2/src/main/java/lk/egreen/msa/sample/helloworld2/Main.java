package lk.egreen.msa.sample.helloworld2;

import lk.egreen.apistudio.bootstrap.ApiStudio;

import javax.ws.rs.ApplicationPath;

/**
 * Created by dewmal on 8/2/16.
 */

@ApplicationPath("/")
public class Main {
    public static void main(String[] args) {
        ApiStudio.boot(Main.class, "0.0.0.0", 2525, "/");
    }
}
