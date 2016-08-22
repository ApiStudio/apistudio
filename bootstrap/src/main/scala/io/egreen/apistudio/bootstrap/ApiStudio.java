package io.egreen.apistudio.bootstrap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.tyrus.server.TyrusServerContainer;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

import javax.enterprise.inject.Instance;
import javax.enterprise.inject.spi.CDI;

/**
 * Created by dewmal on 7/17/16.
 */
public class ApiStudio {

    private final static Logger LOGGER = LogManager.getLogger(ApiStudio.class);

    private final static ApiStudioContext CONTEXT = ApiStudioContext.getINSTANCE();
    private static WeldContainer weldContainer;
    private static TyrusServerContainer tcontainer;

    private static Weld weld = null;
    private static WeldContainer container = null;
    public static Class<?> applicationClass;
    public static String host;
    public static int port;
    public static String root;
    public static Class<?>[] modules;

    public static final long startTime = System.currentTimeMillis();


    public static WeldContainer getWeldContainer(Class<?> applicationClass, Class<?>... classes) {
        if (container == null) {
            weld = new Weld();
            weld.addPackage(true, ApiStudio.class);
            weld.addPackage(true, applicationClass);
            weld.addPackages(true, classes);
            container = weld.initialize();
        }
        return container;
    }


    public static void boot(Class<?> aClass, String host, int port, String root, Class<?>... modules) {
        ApiStudio.applicationClass = aClass;
        ApiStudio.host = host;
        ApiStudio.port = port;
        ApiStudio.root = root;
        ApiStudio.modules = modules;


        LOGGER.info("API-STUDIO STARTED...");
        final long startTime = System.currentTimeMillis();
        getWeldContainer(aClass, modules);

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                weldContainer.shutdown();
                LOGGER.info("Stopping server..");
            }
        }, "shutdownHook"));

//        httpServer.getServerConfiguration().getMonitoringConfig().
        // run
        try {
            Instance<ApiStudioApplication> select = CDI.current().select(ApiStudioApplication.class);
            ApiStudioApplication apiStudioApplication = select.get();
            apiStudioApplication.initialize();

            Thread.currentThread().join();
        } catch (Exception e) {
            LOGGER.error(
                    "There was an error while starting Grizzly HTTP server.", e);
        }
    }


    public static void boot(Class<?> aClass, String host, int port, String root) {

        boot(aClass, host, port, root, null);

    }
}
