package io.egreen.apistudio.bootstrap;

import io.egreen.apistudio.bootstrap.config.processor.AnnotationFilterService;
import io.egreen.apistudio.bootstrap.module.ModuleIntergrator;
import io.egreen.apistudio.bootstrap.rest.RestComponentInitializer;
import io.egreen.apistudio.bootstrap.websocket.ApiWebSocketServerContainer;
import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.grizzly.comet.CometAddOn;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.NetworkListener;
import org.glassfish.grizzly.servlet.ServletRegistration;
import org.glassfish.grizzly.servlet.WebappContext;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.glassfish.tyrus.core.TyrusServerEndpointConfig;
import org.glassfish.tyrus.server.TyrusServerContainer;
import org.jboss.weld.environment.se.WeldContainer;
import org.reflections.Reflections;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.spi.CDI;
import javax.inject.Inject;
import javax.naming.NamingException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.websocket.Decoder;
import javax.websocket.DeploymentException;
import javax.websocket.Encoder;
import javax.websocket.Extension;
import javax.websocket.server.ServerApplicationConfig;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.server.ServerEndpointConfig;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;

/**
 * Created by dewmal on 8/20/16.
 */
@ApplicationScoped
public class ApiStudioServer {

    private static final Logger LOGGER = LogManager.getLogger(ApiStudioServer.class);

    @Inject
    private RestComponentInitializer restComponentInitializer;

    @Inject
    private ModuleIntergrator moduleIntergrator;


    @Inject
    private AnnotationFilterService annotationFilterService;


    private ResourceConfig resourceConfig;
    private TyrusServerContainer tyrusContainer;

    @PostConstruct
    void init() {
        LOGGER.info("Working server..");
        HttpServer httpServer = HttpServer.createSimpleServer(".", ApiStudio.host, ApiStudio.port);

        resourceConfig = restComponentInitializer.getResourceManager();

        //Swagger
        resourceConfig.register(io.swagger.jaxrs.listing.ApiListingResource.class);
        resourceConfig.register(io.swagger.jaxrs.listing.SwaggerSerializers.class);


        ServletContainer servletContainer = new ServletContainer(resourceConfig);


        WebappContext webappContext = new WebappContext("API-STUDIO", ApiStudio.root);
////
////
        webappContext.setAttribute("root", ApiStudio.root);
////
//
//
//        servletRegistration.setInitParameter("jersey.config.server.provider.packages", applicationClass.getPackage().getName());

        ServletRegistration servletRegistration = webappContext.addServlet("api-handler", servletContainer);
        servletRegistration.addMapping("/*");
        servletRegistration.setLoadOnStartup(1);

        moduleIntergrator.init(webappContext);

//        webappContext.ad

//        annotationFilterService.process();


        webappContext.addListener(new ServletContextListener() {
            @Override
            public void contextInitialized(ServletContextEvent sce) {
                NumberFormat formatter = new DecimalFormat("#0.00000");
                LOGGER.info("Execution time is " + formatter.format((System.currentTimeMillis() - ApiStudio.startTime) / 1000d) + " seconds");
            }

            @Override
            public void contextDestroyed(ServletContextEvent sce) {
                NumberFormat formatter = new DecimalFormat("#0.00000");
                LOGGER.info("Execution time is " + formatter.format((System.currentTimeMillis() - ApiStudio.startTime) / 1000d) + " seconds");
            }
        });

        webappContext.deploy(httpServer);


        try {

            final Collection<NetworkListener> listeners = httpServer.getListeners();
            for (NetworkListener listener : listeners) {
                listener.registerAddOn(new CometAddOn());
            }

            List<Class<?>> wsClassList = new ArrayList<>();
            if (ApiStudio.modules != null) {
                wsClassList = new ArrayList(Arrays.asList(ApiStudio.modules));
            }
            wsClassList.add(ApiStudio.applicationClass);
            tyrusContainer = getTyrusContainer(httpServer, wsClassList);


        } catch (DeploymentException e) {
            e.printStackTrace();
        } catch (NamingException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                LOGGER.info("Stopping server..");
//                httpServer.shutdown();
                tyrusContainer.stop();
            }
        }, "shutdownHook"));

//        httpServer.getServerConfiguration().getMonitoringConfig().
        // run
        try {
            LOGGER.info("starting on http://" + ApiStudio.host + ":" + ApiStudio.port + "/" + ApiStudio.root);

            tyrusContainer.start(ApiStudio.root, ApiStudio.port);
//            httpServer.getServerConfiguration().setJmxEnabled(true);
//            httpServer.start();
            LOGGER.info("Press CTRL^C to exit..");
            Thread.currentThread().join();
        } catch (Exception e) {
            LOGGER.error(
                    "There was an error while starting Grizzly HTTP server.", e);
        }

    }


    /**
     * @param server
     * @param classes
     * @return
     * @throws DeploymentException
     * @throws NamingException
     * @throws ClassNotFoundException
     */
    public TyrusServerContainer getTyrusContainer(HttpServer server, List<Class<?>> classes) throws DeploymentException, NamingException, ClassNotFoundException {

        LOGGER.info(classes);
        TyrusServerContainer container = (TyrusServerContainer) new ApiWebSocketServerContainer(server).createContainer(null);

        if (classes != null & classes.size() > 0) {



            for (Class<?> searchPackageClass : classes) {

                List<String> namesOfClassesWithAnnotation = new FastClasspathScanner(searchPackageClass.getPackage().getName()).scan().getNamesOfClassesWithAnnotation(ServerEndpoint.class);


//            Reflections reflections = new Reflections(searchPackageClass.getPackage().getName());

//            Set<Class<?>> typesAnnotatedWith = reflections.getTypesAnnotatedWith(ServerEndpoint.class);
                //    LOGGER.info(typesAnnotatedWith);
                for (String className : namesOfClassesWithAnnotation) {
                    LOGGER.info(Class.forName(className));

                    container.addEndpoint(Class.forName(className));
                }
            }
        }

//

        return container;
    }


}
