package io.egreen.apistudio.bootstrap;

import io.egreen.apistudio.bootstrap.database.CassandraDataSource;
import io.egreen.apistudio.bootstrap.ext.SwaggerBootstrap;
import io.egreen.apistudio.bootstrap.filter.AuthFilter;
import io.egreen.apistudio.bootstrap.filter.CORSResponseFilter;
import io.egreen.apistudio.bootstrap.processors.JaxRsAnnotationProcessor;
import io.egreen.apistudio.bootstrap.provider.ApiStudioObjectMapperProvider;
import io.egreen.apistudio.bootstrap.theme.ThymeleafViewProcessor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.grizzly.http.server.CLStaticHttpHandler;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.NetworkListener;
import org.glassfish.grizzly.servlet.ServletRegistration;
import org.glassfish.grizzly.servlet.WebappContext;
import org.glassfish.grizzly.websockets.WebSocketAddOn;
import org.glassfish.grizzly.websockets.WebSocketApplication;
import org.glassfish.grizzly.websockets.WebSocketEngine;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.glassfish.jersey.server.mvc.MvcFeature;
import org.glassfish.jersey.servlet.ServletContainer;
import org.glassfish.tyrus.core.ComponentProvider;
import org.glassfish.tyrus.gf.cdi.CdiComponentProvider;
import org.glassfish.tyrus.server.Server;
import org.glassfish.tyrus.server.TyrusServerContainer;
import org.glassfish.tyrus.server.TyrusServerEndpointConfigurator;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import org.reflections.Reflections;

import javax.naming.NamingException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.websocket.DeploymentException;
import javax.websocket.server.ServerEndpoint;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;

/**
 * Created by dewmal on 7/17/16.
 */
public class ApiStudio {

    private final static Logger LOGGER = LogManager.getLogger(ApiStudio.class);

    private final static ApiStudioContext CONTEXT = ApiStudioContext.getINSTANCE();


    public static void boot(Class<?> aClass, String host, int port, String root, Class<?>... modules) {


        List<Class<?>> moduleList = new ArrayList<>();
        if (modules != null) {
            Collections.addAll(moduleList, modules);
        }


        LOGGER.info("API-STUDIO STARTED...");


        final long startTime = System.currentTimeMillis();

        final Weld weld = new Weld(System.nanoTime() + "");
        weld.addPackage(true, aClass);
        weld.addPackage(true, CassandraDataSource.class);
        weld.addPackage(true, ApiStudio.class);

        // Register Modules in CDI
        for (Class<?> moduleClass : moduleList) {
            weld.addPackage(true, moduleClass);
        }

        weld.initialize();


        HttpServer httpServer = HttpServer.createSimpleServer(".", host, port);

        TyrusServerContainer tcontainer = null;
        try {
            tcontainer = getTyrusContainer(httpServer, aClass);
        } catch (DeploymentException e) {
            e.printStackTrace();
        }


        ResourceConfig resourceConfig = JaxRsAnnotationProcessor.genResourceConfig(aClass.getPackage().getName());
        resourceConfig.register(CORSResponseFilter.class);
        resourceConfig.register(JacksonFeature.class);
        resourceConfig.register(ApiStudioObjectMapperProvider.class);

        resourceConfig.property(ServerProperties.MONITORING_STATISTICS_ENABLED, true);
        // HTML5 Template Engine
        resourceConfig.register(ThymeleafViewProcessor.class);
        resourceConfig.register(MvcFeature.class);

//        StaticHttpHandler staticHttpHandler = new StaticHttpHandler(aClass.getResource("www").getPath() + ".");
        CLStaticHttpHandler staticHttpHandler = new CLStaticHttpHandler(aClass.getClassLoader(), "www/");
        LOGGER.info(staticHttpHandler.getName());
        httpServer.getServerConfiguration().addHttpHandler(staticHttpHandler, "/static");


        //Swagger
        resourceConfig.register(io.swagger.jaxrs.listing.ApiListingResource.class);
        resourceConfig.register(io.swagger.jaxrs.listing.SwaggerSerializers.class);
        //Authentication
        resourceConfig.register(RolesAllowedDynamicFeature.class);
        resourceConfig.register(AuthFilter.class);

        // Register Modules in Resource Config
        for (Class<?> moduleClass : moduleList) {
            resourceConfig.packages(true, moduleClass.getPackage().getName());
        }

        //
        resourceConfig.packages(true, aClass.getPackage().getName());


        ServletContainer servletContainer = new ServletContainer(resourceConfig);
        WebappContext webappContext = new WebappContext("API-STUDIO", root);


        webappContext.setAttribute("root", root);
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
            LOGGER.info("starting on http://" + host + ":" + port);
            tcontainer.start(root, port);
//            httpServer.start();
            LOGGER.info("Press CTRL^C to exit..");
            Thread.currentThread().join();
        } catch (Exception e) {
            LOGGER.error(
                    "There was an error while starting Grizzly HTTP server.", e);
        }
    }

    /**
     * Creates and configures a Tyrus server container, based on an existing grizzly HTTP server
     *
     * @return Tyrus server container.
     */
    public static TyrusServerContainer getTyrusContainer(HttpServer server, Class<?> classes) throws DeploymentException {
        TyrusServerContainer container = (TyrusServerContainer) new ApiWebSocketServerContainer(server).createContainer(null);

        Reflections reflections = new Reflections(classes.getPackage().getName());

        Set<Class<?>> typesAnnotatedWith = reflections.getTypesAnnotatedWith(ServerEndpoint.class);
        for (Class<?> aClass : typesAnnotatedWith) {
            container.addEndpoint(aClass);
        }


        return container;
    }


    public static void boot(Class<?> aClass, String host, int port, String root) {

        boot(aClass, host, port, root, null);

    }
}
