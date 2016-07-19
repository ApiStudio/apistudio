package lk.egreen.apistudio.transpoter;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.jboss.weld.environment.se.Weld;

import java.io.IOException;
import java.net.URI;

/**
 * Created by dewmal on 7/16/16.
 */
public class HttpServerApi implements Transporter {
    @Override
    public void start(String location, int port, final ResourceConfig resConfig, final AbstractBinder abstractBinder) {

        Weld weld = new Weld();
        weld.addPackage(true, HttpServerApi.class);
        weld.addPackage(true, abstractBinder.getClass());
        weld.addBeanClass(abstractBinder.getClass());
        weld.initialize();

        resConfig.registerClasses(MyService.class);
        HttpServer httpServer = GrizzlyHttpServerFactory.createHttpServer(URI.create("http://localhost:8080/weld/"), resConfig);
        try {
            httpServer.start();
            System.in.read();
            httpServer.shutdownNow();
            weld.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void stop() {

    }
}
