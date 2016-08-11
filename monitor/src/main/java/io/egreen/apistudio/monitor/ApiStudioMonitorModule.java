package io.egreen.apistudio.monitor;

import io.egreen.apistudio.bootstrap.module.Module;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jboss.weld.environment.se.events.ContainerInitialized;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;

/**
 * Created by dewmal on 8/10/16.
 */
@ApplicationScoped
@Module
public class ApiStudioMonitorModule {

    private static final Logger LOGGER = LogManager.getLogger(ApiStudioMonitorModule.class);


    public void start(@Observes ContainerInitialized event) throws Exception {
        LOGGER.info("Welcome to Main API Monitor");
        LOGGER.info(event.getContainerId());
    }
}
