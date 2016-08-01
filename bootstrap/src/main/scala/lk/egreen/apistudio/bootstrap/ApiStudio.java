package lk.egreen.apistudio.bootstrap;

import lk.egreen.apistudio.bootstrap.ext.SwaggerBootstrap;
import lk.egreen.apistudio.bootstrap.externalsource.database.CassandraDataSource;
import lk.egreen.apistudio.bootstrap.module.theme.ThymeleafViewProcessor;
import lk.egreen.apistudio.bootstrap.processors.JaxRsAnnotationProcessor;
import org.apache.cassandra.service.CassandraDaemon;
import org.apache.cassandra.service.EmbeddedCassandraService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.cassandraunit.CassandraUnit;
import org.cassandraunit.dataset.json.ClassPathJsonDataSet;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.servlet.ServletRegistration;
import org.glassfish.grizzly.servlet.WebappContext;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.mvc.MvcFeature;
import org.glassfish.jersey.servlet.ServletContainer;
import org.jboss.weld.environment.se.Weld;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by dewmal on 7/17/16.
 */
public class ApiStudio {

    private final static Logger LOGGER = LogManager.getLogger(ApiStudio.class);


    public static void boot(Class<?> aClass, String host, int port, String root) {

        LOGGER.info("API-STUDIO STARTED...");


        final long startTime = System.currentTimeMillis();

        Weld weld = new Weld(System.nanoTime() + "");
        weld.addPackage(true, aClass);
        weld.addPackage(true, CassandraDataSource.class);
        weld.addPackage(true, ApiStudio.class);
        weld.initialize();

        HttpServer httpServer = HttpServer.createSimpleServer(".", host, port);


        ResourceConfig resourceConfig = JaxRsAnnotationProcessor.genResourceConfig(aClass.getPackage().getName());
        // HTML5 Template Engine
        resourceConfig.register(ThymeleafViewProcessor.class);
        resourceConfig.register(MvcFeature.class);


//        EmbeddedCassandraService embeddedCassandraService = new EmbeddedCassandraService();
//
//        try {
//            embeddedCassandraService.start();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        //Swagger
        resourceConfig.register(io.swagger.jaxrs.listing.ApiListingResource.class);
        resourceConfig.register(io.swagger.jaxrs.listing.SwaggerSerializers.class);
        //Authentication
//        resourceConfig.register(AuthFilter.class);
//        resourceConfig.register(RolesAllowedDynamicFeature.class);
//        resourceConfig.packages(true, aClass.getPackage().getName());


        ServletContainer servletContainer = new ServletContainer(resourceConfig);

        WebappContext webappContext = new WebappContext("API-STUDIO", root);
        ServletRegistration servletRegistration = webappContext.addServlet("api-handler", servletContainer);
        servletRegistration.addMapping("/*");
        servletRegistration.setLoadOnStartup(1);


        ServletRegistration swagger = webappContext.addServlet("Swagger", SwaggerBootstrap.class);
        swagger.setInitParameter("host", host + ":" + port);
        swagger.setInitParameter("path", root);
        swagger.setInitParameter("res", aClass.getPackage().getName());
        swagger.setLoadOnStartup(2);


//        servletRegistration.setInitParameter("jersey.config.server.provider.packages", aClass.getPackage().getName());


        webappContext.addListener(new ServletContextListener() {
            @Override
            public void contextInitialized(ServletContextEvent sce) {

                NumberFormat formatter = new DecimalFormat("#0.00000");
                LOGGER.info("Execution time is " + formatter.format((System.currentTimeMillis() - startTime) / 1000d) + " seconds");
            }

            @Override
            public void contextDestroyed(ServletContextEvent sce) {
                NumberFormat formatter = new DecimalFormat("#0.00000");
                LOGGER.info("Execution time is " + formatter.format((System.currentTimeMillis() - startTime) / 1000d) + " seconds");
            }
        });

        webappContext.deploy(httpServer);

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                LOGGER.info("Stopping server..");
                httpServer.shutdownNow();
            }
        }, "shutdownHook"));

//        httpServer.getServerConfiguration().getMonitoringConfig().
        // run
        try {
            httpServer.start();
            LOGGER.info("Press CTRL^C to exit..");
            Thread.currentThread().join();
        } catch (Exception e) {
            LOGGER.error(
                    "There was an error while starting Grizzly HTTP server.", e);
        }

    }
}
