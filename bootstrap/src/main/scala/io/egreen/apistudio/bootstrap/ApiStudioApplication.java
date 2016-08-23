package io.egreen.apistudio.bootstrap;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
 * Created by dewmal on 8/20/16.
 */
@ApplicationScoped
public class ApiStudioApplication {

    private static final Logger LOGGER = LogManager.getLogger(ApiStudioApplication.class);


    @Inject
    private ApiStudioServer apiStudioServer;


    @PostConstruct
    public void initialize() {
        LOGGER.info("Start all components...");
        initializeComponents();
    }

    private void initializeComponents() {


        apiStudioServer.init();

    }

}
