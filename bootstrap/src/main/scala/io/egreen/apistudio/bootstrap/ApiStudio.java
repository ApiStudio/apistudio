package io.egreen.apistudio.bootstrap;

import io.egreen.apistudio.bootstrap.ext.SwaggerBootstrap;
import io.egreen.apistudio.bootstrap.database.CassandraDataSource;
import io.egreen.apistudio.bootstrap.filter.AuthFilter;
import io.egreen.apistudio.bootstrap.theme.ThymeleafViewProcessor;
import io.egreen.apistudio.bootstrap.monitor.ApiMonitor;
import io.egreen.apistudio.bootstrap.processors.JaxRsAnnotationProcessor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.servlet.ServletRegistration;
import org.glassfish.grizzly.servlet.WebappContext;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.glassfish.jersey.server.mvc.MvcFeature;
import org.glassfish.jersey.servlet.ServletContainer;
import org.jboss.weld.environment.se.Weld;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
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

        final Weld weld = new Weld(System.nanoTime() + "");
        weld.addPackage(true, aClass);
        weld.addPackage(true, CassandraDataSource.class);
        weld.addPackage(true, ApiStudio.class);

        weld.initialize();

        HttpServer httpServer = HttpServer.createSimpleServer(".", host, port);


        ResourceConfig resourceConfig = JaxRsAnnotationProcessor.genResourceConfig(aClass.getPackage().getName());
        resourceConfig.property(ServerProperties.MONITORING_STATISTICS_ENABLED, true);
        // HTML5 Template Engine
        resourceConfig.register(ThymeleafViewProcessor.class);
        resourceConfig.register(MvcFeature.class);


        //Swagger
        resourceConfig.register(io.swagger.jaxrs.listing.ApiListingResource.class);
        resourceConfig.register(io.swagger.jaxrs.listing.SwaggerSerializers.class);
        //Authentication
        resourceConfig.register(RolesAllowedDynamicFeature.class);
        resourceConfig.register(AuthFilter.class);

        //Monitor
        resourceConfig.register(ApiMonitor.class);

        //
        resourceConfig.packages(true, aClass.getPackage().getName());


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
