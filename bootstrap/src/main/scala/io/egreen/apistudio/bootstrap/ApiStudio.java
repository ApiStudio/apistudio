package io.egreen.apistudio.bootstrap;

import io.egreen.apistudio.bootstrap.config.MSApp;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.jersey.server.TracingConfig;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

import javax.enterprise.inject.Instance;
import javax.enterprise.inject.spi.CDI;
import java.util.logging.Level;

/**
 * Created by dewmal on 7/17/16.
 */
public class ApiStudio {

    private final static Logger LOGGER = LogManager.getLogger(ApiStudio.class);

    private static WeldContainer weldContainer;

    private static Weld weld = null;
    private static WeldContainer container = null;
    public static Class<?> applicationClass;
    public static String host;
    public static int port;
    public static String root;
    public static Class<?>[] modules;

    public static final long startTime = System.currentTimeMillis();
    private static String applicationName;


    /**
     * Injectors Buildup
     *
     * @param applicationClass
     * @param classes
     * @return
     */
    public static WeldContainer getWeldContainer(Class<?> applicationClass, Class<?>... classes) {
        if (container == null) {
            weld = new Weld();
            weld.addPackage(true, ApiStudio.class);
            weld.addPackage(true, applicationClass);
            if (classes != null) {
                weld.addPackages(true, classes);
            }
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
        initApiContext();

        if (aClass.isAnnotationPresent(MSApp.class)) {
            MSApp annotation = aClass.getAnnotation(MSApp.class);
            ApiStudio.SETTINGS.TRACING = annotation.trace();

            switch (annotation.logLevel()) {

                case Integer.MIN_VALUE: {
                    ApiStudio.SETTINGS.LOG_LEVEL = Level.ALL;
                }
                break;
                case 500: {
                    ApiStudio.SETTINGS.LOG_LEVEL = Level.FINE;
                }
                break;
                case 800: {
                    ApiStudio.SETTINGS.LOG_LEVEL = Level.INFO;
                }

                break;
                case 900: {
                    ApiStudio.SETTINGS.LOG_LEVEL = Level.WARNING;
                }
                break;
                default: {
                    ApiStudio.SETTINGS.LOG_LEVEL = Level.OFF;
                }
            }


            applicationName = annotation.name();
        } else {
            throw new ExceptionInInitializerError("Application not initialized with MSApp");
        }





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

    private static void initApiContext() {

        ApiStudioContext.getINSTANCE().put(ApiStudioContext.CONTENT.HOST, host);
        ApiStudioContext.getINSTANCE().put(ApiStudioContext.CONTENT.NAME, applicationName);
        ApiStudioContext.getINSTANCE().put(ApiStudioContext.CONTENT.PORT, port);
        ApiStudioContext.getINSTANCE().put(ApiStudioContext.CONTENT.ROOT, root);

    }


    public static void boot(Class<?> aClass, String host, int port, String root) {

        boot(aClass, host, port, root, null);

    }

    public static Class getApplicationClass() {
        return applicationClass;
    }

    public static String getApplicationName() {
        return applicationName;
    }

    public static class SETTINGS {
        public static TracingConfig TRACING;
        public static Level LOG_LEVEL;
    }
}
